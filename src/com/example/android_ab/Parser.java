package com.example.android_ab;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class Parser {
    private static final String KEY_EXPERIMENT_KEY = "experiment_key";
    private static final String KEY_BUCKET_NAME = "bucket_name";
    private static final String KEY_VERSION = "version";
    private static final String KEY_EXPIRATION_DATE = "expiration_date";

    public static ArrayList<Experiment> parseExperimentArray(InputStream stream) throws IOException {
        final JsonFactory factory = new JsonFactory();
        final JsonParser parser = factory.createJsonParser(stream);
        final ArrayList<Experiment> experiments = new ArrayList<Experiment>();
        parser.nextToken();
        JsonToken token = parser.getCurrentToken();
        while (token != null && token != JsonToken.END_ARRAY) {
            if (token == JsonToken.START_OBJECT) {
                final Experiment experiment = parseExperiment(parser);
                if (experiment != null) {
                    experiments.add(experiment);
                }
            }
            token = parser.nextToken();
        }
        return experiments;
    }

    private static Experiment parseExperiment(JsonParser parser) throws IOException {
        JsonToken token = parser.nextToken();
        String name;
        Experiment experiment = new Experiment();
        while (token != null && token != JsonToken.END_OBJECT) {
            switch (token){
                case VALUE_STRING:{
                    name = parser.getCurrentName();
                    if(KEY_EXPERIMENT_KEY.equals(name)){
                        experiment.mExperimentKey = parser.getText();
                    } else if(KEY_BUCKET_NAME.equals(name)){
                        experiment.mBucketName = parser.getText();
                    } else if(KEY_EXPIRATION_DATE.equals(name)){
                        experiment.mExpirationDate = parser.getText();
                    }
                }
                case VALUE_NUMBER_INT:{
                    name = parser.getCurrentName();
                    if(KEY_VERSION.equals(name)){
                        experiment.mVersion = parser.getIntValue();
                    }
                }
            }
            token = parser.nextToken();
        }
        return experiment;
    }
}