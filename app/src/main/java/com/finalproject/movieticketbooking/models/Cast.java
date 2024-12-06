package com.finalproject.movieticketbooking.models;

import android.os.Parcelable;


import android.os.Parcel;
import android.os.Parcelable;

public class Cast implements Parcelable {
    private String Actor;

    // Empty constructor
    public Cast() {}

    // Constructor with parameters
    public Cast(String actor) {
        Actor = actor;
    }

    // Getter and Setter methods
    public String getActor() {
        return Actor;
    }

    public void setActor(String actor) {
        Actor = actor;
    }


    // Implement Parcelable methods
    protected Cast(Parcel in) {
        Actor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Actor);
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

