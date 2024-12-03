package com.finalproject.movieticketbooking.models;

import android.os.Parcelable;


import android.os.Parcel;
import android.os.Parcelable;

public class Cast implements Parcelable {
    private String Actor;
    private String PicUrl;

    // Empty constructor
    public Cast() {}

    // Constructor with parameters
    public Cast(String actor, String picUrl) {
        Actor = actor;
        PicUrl = picUrl;
    }

    // Getter and Setter methods
    public String getActor() {
        return Actor;
    }

    public void setActor(String actor) {
        Actor = actor;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    // Implement Parcelable methods
    protected Cast(Parcel in) {
        Actor = in.readString();
        PicUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Actor);
        dest.writeString(PicUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Cast> CREATOR = new Parcelable.Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };
}

