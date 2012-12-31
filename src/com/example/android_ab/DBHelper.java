package com.example.android_ab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public final class DBHelper extends SQLiteOpenHelper {
	public static final String TAG = "DbAdapter";
	public static final String DATABASE_FILE_NAME = "test.db";
	public static final int DATABASE_VERSION = 1;

	DBHelper(Context context) {
		super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Item.SQL_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, String.format("Upgrading database from version %d to %d", 
				oldVersion, newVersion) );
		db.execSQL(Item.SQL_DROP_TABLE);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, String.format("Upgrading database from version %d to %d", 
				oldVersion, newVersion) );
		onUpgrade(db, oldVersion, newVersion);
	}

}
