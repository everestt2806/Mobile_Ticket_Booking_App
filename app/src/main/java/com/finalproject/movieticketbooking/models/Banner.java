package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Banner implements Parcelable {
    private String image;

    public  Banner(){}
    // Constructor đầy đủ
    public Banner(String image) {
        this.image = image;
    }

    // Constructor cho Parcelable
    protected Banner(Parcel in) {
        image = in.readString();
    }

    // Getter và Setter
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Phương thức của Parcelable
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Creator cho Parcelable
    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}
