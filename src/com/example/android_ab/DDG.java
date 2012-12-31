package com.example.android_ab;

import java.util.HashMap;

public class DDG {
	static String getBucket(String experiment_key){
		HashMap<String, String> experiments = new HashMap<String, String>();
		experiments.put("experiment_1", "fancy");
		return experiments.get(experiment_key);
	}
}
