package com.example.android_ab;

import android.content.Context;

import java.util.HashMap;

public class DDG {
    DBHelper mDBHelper;

    DDG(Context context){
        this.mDBHelper = new DBHelper(context);
    }

	String getBucket(String experiment_key){
		Experiment experiment = this.mDBHelper.getExperiment(experiment_key);
        String bucketName = null;
        if(experiment != null){
            // fire impression
            bucketName = experiment.mBucketName;
        }
        return bucketName;
	}
}
