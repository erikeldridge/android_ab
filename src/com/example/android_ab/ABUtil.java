package com.example.android_ab;

import android.content.Context;

class ABUtil {
    private final DBHelper mDBHelper;

    ABUtil(Context context){
        this.mDBHelper = new DBHelper(context);
    }

    private void log(Experiment experiment){} // placeholder for remote logger

	String getBucket(String experiment_key){
		Experiment experiment = this.mDBHelper.getExperiment(experiment_key);
        String bucketName = null;
        if(experiment != null){
            log(experiment);
            bucketName = experiment.mBucketName;
        }
        return bucketName;
	}
}
