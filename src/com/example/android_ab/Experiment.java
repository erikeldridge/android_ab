package com.example.android_ab;

import android.os.Parcel;
import android.os.Parcelable;

public class Experiment implements Parcelable{
    public String mExperimentKey, mBucketName, mExpirationDate;
    public int mVersion;

    public Experiment() {
    }

    public Experiment(Parcel in) {
        mExperimentKey = in.readString();
        mBucketName = in.readString();
        mVersion = in.readInt();
        mExpirationDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel in, int i) {
        in.writeString(mExperimentKey);
        in.writeString(mBucketName);
        in.writeInt(mVersion);
        in.writeString(mExpirationDate);
    }

    public static final Parcelable.Creator<Experiment> CREATOR = new Parcelable.Creator<Experiment>() {
        public Experiment createFromParcel(Parcel in) {
            return new Experiment(in);
        }

        public Experiment[] newArray(int size) {
            return new Experiment[size];
        }
    };
}
