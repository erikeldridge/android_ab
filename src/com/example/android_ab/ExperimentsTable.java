package com.example.android_ab;

import android.provider.BaseColumns;

class ExperimentsTable implements BaseColumns {
    public static final String TABLE_NAME = "experiments";
    public static final String EXPERIMENT_KEY = "experiment_key";
    public static final String BUCKET_NAME = "bucket_name";
    public static final String VERSION = "version";
    public static final String EXPIRATION_DATE = "expiration_date";
}
