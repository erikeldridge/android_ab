package com.example.test;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class FetchItemsAsyncTask extends AsyncTask<String, Void, String> {
	private static final String TAG = "FetchItemsAsyncTask";
	Context context;
	
    public FetchItemsAsyncTask(Context context) {
        this.context = context;
    }
	
	@Override
	protected String doInBackground(String... urls) {
		Log.v(TAG, "doInBackground");
		String json = "[]";
        try {
        	URL url = new URL(urls[0]);
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
        	json = Util.readStream(con.getInputStream());
        } catch(Exception e){
        	e.printStackTrace();
        }
		
		return json;
	}
	
	@Override
	protected void onPostExecute(String json){
		Log.v(TAG, "onPostExecute");
		
		// parse
		JSONArray items = Util.parseJson(json);

		// store
    	DBHelper DBHelper = new DBHelper(this.context);
    	SQLiteDatabase db = DBHelper.getReadableDatabase();
    	Item.insert(db, items);
    	
    	// debug
    	Cursor cursor = Item.getAllItems(db);
    	Log.v(TAG, "count: "+cursor.getCount());
	}
}
