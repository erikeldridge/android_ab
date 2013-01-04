package com.example.android_ab;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExperimentConfigService extends IntentService {
    private static final String TAG = "ExperimentConfigService";
    private static final String SERVICE_URL = "http://damp-taiga-5766.herokuapp.com";

    public ExperimentConfigService() {
        super("ExperimentConfigService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(TAG, "onHandleIntent");
        String json = fetchExperimentConfig();
        JSONArray experiments = parseJson(json);
        saveExperiments(experiments);
    }

    private String fetchExperimentConfig(){
        String json = "[]";
        try {
            URL url = new URL(SERVICE_URL);
            if(BuildConfig.DEBUG){
                url = new URL(SERVICE_URL+":5000");
            }
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            json = readStream(con.getInputStream());
        } catch(ConnectException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return json;
    }

    private static String readStream(InputStream in){
        Log.v(TAG, "readStream");
        BufferedReader reader = null;
        String json = "";
        try {
            String line;
            reader = new BufferedReader(new InputStreamReader(in));
            while((line = reader.readLine()) != null){
                json += line;
                // http://stackoverflow.com/a/5190876
                if (!reader.ready()) {
                    break;
                }
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

    private static JSONArray parseJson(String json){
        Log.v(TAG, "parseJson");
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

    private void saveExperiments(JSONArray experiments){
        DBHelper helper = new DBHelper(this);
        helper.removeAllExperiments();
        helper.addExperiments(experiments);
        helper.close();
    }
}
