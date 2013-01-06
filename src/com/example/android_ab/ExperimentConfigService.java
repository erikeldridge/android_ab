package com.example.android_ab;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ExperimentConfigService extends IntentService {
    private static final String DEV_SERVICE_URL = "http://damp-taiga-5766.herokuapp.com:5000";
    private static final String PROD_SERVICE_URL = "http://damp-taiga-5766.herokuapp.com";
    private static final String EXTRA_EXPERIMENTS = "experiments";

    final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            final Intent intent = (Intent) msg.obj;
            final ArrayList<Experiment> experiments = intent.getParcelableArrayListExtra(EXTRA_EXPERIMENTS);
            saveExperiments(experiments);
        }
    };

    public ExperimentConfigService() {
        super("ExperimentConfigService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        try {
            InputStream stream = fetchExperimentConfig();
            ArrayList<Experiment> experiments = Parser.parseExperimentArray(stream);
            intent.putParcelableArrayListExtra(EXTRA_EXPERIMENTS, experiments);
            mHandler.sendMessage(mHandler.obtainMessage(1, intent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream fetchExperimentConfig() throws IOException {
        URL url;
        if(BuildConfig.DEBUG) {
            url = new URL(DEV_SERVICE_URL);
        } else {
            url = new URL(PROD_SERVICE_URL);
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
