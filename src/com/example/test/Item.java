package com.example.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Item implements BaseColumns {

	public static final String TABLE_NAME = "items";
	public static final String COLUMN_NAME = "name";
	public static final String SQL_CREATE_TABLE = String.format(
			"CREATE TABLE %s ("
					+"%s integer primary key autoincrement,"
					+"%s text)",
					TABLE_NAME, _ID, COLUMN_NAME);
	public static final String SQL_DROP_TABLE = String.format(
			"DROP TABLE IF EXISTS %s", TABLE_NAME);

	public static long insert(SQLiteDatabase db, String id, String name) throws SQLException {
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		return db.insertOrThrow(TABLE_NAME, null, values);
	}

	public static void insert(SQLiteDatabase db, JSONArray items){
		JSONObject item;
		for(int i = 0; i < items.length(); i++){
			try {
				item = items.getJSONObject(i);
				Item.insert(db, item.getString("id"), item.getString("name"));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} 
	
	public static Cursor getAllItems(SQLiteDatabase db){
		String[] projection = {_ID, COLUMN_NAME};
		return db.query(TABLE_NAME, projection, null, null, null, null, null);
	}
}
