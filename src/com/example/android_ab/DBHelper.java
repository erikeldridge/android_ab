package com.example.android_ab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

final class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DATABASE_FILE_NAME = "android_ab.db";
    private static final int DATABASE_VERSION = 1;

    DBHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EXPERIMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, String.format("Upgrading from version %d to %d",
                oldVersion, newVersion) );
        db.execSQL(SQL_DROP_EXPERIMENTS_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, String.format("Downgrading from version %d to %d",
                oldVersion, newVersion) );
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_EXPERIMENTS_TABLE = String.format(
            "CREATE TABLE %s ("
                    +"%s INT PRIMARY KEY AUTOINCREMENT, "
                    +"%s TEXT UNIQUE NOT NULL, "
                    +"%s TEXT, "
                    +"%s INT, "
                    +"%s TEXT);",
            ExperimentsTable.TABLE_NAME, ExperimentsTable._ID,
            ExperimentsTable.EXPERIMENT_KEY, ExperimentsTable.BUCKET_NAME,
            ExperimentsTable.VERSION, ExperimentsTable.EXPIRATION_DATE);
    private static final String SQL_DROP_EXPERIMENTS_TABLE = String.format(
            "DROP TABLE IF EXISTS %s;", ExperimentsTable.TABLE_NAME);
    private static final String SQL_DELETE_EXPERIMENTS = String.format(
            "DELETE FROM %s;", ExperimentsTable.TABLE_NAME);

    private static final class ExperimentsQuery {
        public static final String[] PROJECTION = new String[] {
                ExperimentsTable.EXPERIMENT_KEY,
                ExperimentsTable.BUCKET_NAME,
                ExperimentsTable.VERSION,
                ExperimentsTable.EXPIRATION_DATE
        };
        public static final String SELECTION = String.format(
                "%s =? AND %s >= date('now');",
                ExperimentsTable.EXPERIMENT_KEY,
                ExperimentsTable.EXPIRATION_DATE);
    }

    public synchronized void removeAllExperiments() {
        final SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_EXPERIMENTS);
    }

    public synchronized void addExperiments(ArrayList<Experiment> experiments) {
        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();
        for(Experiment experiment : experiments){
            values.put(ExperimentsTable.EXPERIMENT_KEY, experiment.mExperimentKey);
            values.put(ExperimentsTable.BUCKET_NAME, experiment.mBucketName);
            values.put(ExperimentsTable.VERSION, experiment.mVersion);
            values.put(ExperimentsTable.EXPIRATION_DATE, experiment.mExpirationDate);
            db.insert(ExperimentsTable.TABLE_NAME, null, values);
        }
    }

    public synchronized Experiment getExperiment(String experimentKey) {
        final SQLiteDatabase db = getWritableDatabase();
        final Cursor cursor = db.query(ExperimentsTable.TABLE_NAME, ExperimentsQuery.PROJECTION,
                ExperimentsQuery.SELECTION, new String[]{experimentKey}, null, null, null);
        Experiment experiment = null;
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            experiment = new Experiment();
            experiment.mExperimentKey = cursor.getString(cursor.getColumnIndex(ExperimentsTable.EXPERIMENT_KEY));
            experiment.mBucketName = cursor.getString(cursor.getColumnIndex(ExperimentsTable.BUCKET_NAME));
            experiment.mVersion = cursor.getInt(cursor.getColumnIndex(ExperimentsTable.VERSION));
            experiment.mExpirationDate = cursor.getString(cursor.getColumnIndex(ExperimentsTable.EXPIRATION_DATE));
        }
        return experiment;
    }
}
