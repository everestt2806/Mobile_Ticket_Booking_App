package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Seat implements Parcelable {
    private String type; // standard, vip, couple

    public Seat() {
        // Required empty constructor for Firebase
    }

    public Seat(String type) {
        this.type = type;
    }

    protected Seat(Parcel in) {
        type = in.readString();
    }

    public static final Creator<Seat> CREATOR = new Creator<Seat>() {
        @Override
        public Seat createFromParcel(Parcel in) {
            return new Seat(in);
        }

        @Override
        public Seat[] newArray(int size) {
            return new Seat[size];
        }
    };

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
    }
}



