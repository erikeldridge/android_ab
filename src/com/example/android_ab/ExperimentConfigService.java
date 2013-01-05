package com.example.android_ab;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.fasterxml.jackson.core.JsonFactory;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class ExperimentConfigService extends IntentService {
    private static final String TAG = "ExperimentConfigService";
    private static final String SERVICE_URL = "http://damp-taiga-5766.herokuapp.com";

    public ExperimentConfigService() {
        super("ExperimentConfigService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(TAG, "onHandleIntent");
        try {
            InputStream stream = fetchExperimentConfig();
            ArrayList<Experiment> experiments = Parser.parseExperimentArray(stream);
            saveExperiments(experiments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream fetchExperimentConfig() throws IOException {
        URL url;
        if(BuildConfig.DEBUG){
            url = new URL(SERVICE_URL+":5000");
        }else{
            url = new URL(SERVICE_URL);
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return con.getInputStream();
    }

    private void saveExperiments(ArrayList<Experiment> experiments){
        DBHelper helper = new DBHelper(this);
        helper.removeAllExperiments();
        helper.addExperiments(experiments);
        helper.close();
    }
}
