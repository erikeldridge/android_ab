package com.example.android_ab;

import android.content.Context;

class ABUtil {
    private final DBHelper mDBHelper;

    ABUtil(Context context){
        this.mDBHelper = new DBHelper(context);
    }

    void log(Experiment experiment){}

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
