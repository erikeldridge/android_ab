package com.example.android_ab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;

import android.util.Log;

public class Util {
	
	public static String readStream(InputStream in){
		Log.v("Util", "readStream");
		BufferedReader reader = null;
		String json = "";
		try {
			String line = "";
			reader = new BufferedReader(new InputStreamReader(in));
			while((line = reader.readLine()) != null){
				json += line; 
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return json;
	}

	public static JSONArray parseJson(String json){
		Log.v("Util", "parseJson");
		JSONArray data = null;

		try{
			data = new JSONArray(json);
		}catch(Exception e){
			e.printStackTrace();
		}

		if(null == data){
			try{
				data = new JSONArray("[]");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return data;
	}
}
