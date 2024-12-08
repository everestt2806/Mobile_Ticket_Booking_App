package com.finalproject.movieticketbooking.models;
import android.os.Parcel;
import android.os.Parcelable;


public class Cinema implements Parcelable {
    private String id;
    private String name;
    private String address;
    private String city;
    private String image;

    // Empty constructor for Firebase
    public Cinema() {}

    // Full constructor
    public Cinema(String id, String name, String address, String city, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.image = image;
    }

    protected Cinema(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        city = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cinema> CREATOR = new Creator<Cinema>() {
        @Override
        public Cinema createFromParcel(Parcel in) {
            return new Cinema(in);
        }

        @Override
        public Cinema[] newArray(int size) {
            return new Cinema[size];
        }
    };

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

