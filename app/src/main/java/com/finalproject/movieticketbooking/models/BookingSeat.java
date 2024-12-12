package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class BookingSeat implements Parcelable {
    private String seatId;
    private String type;
    private double price;

    public BookingSeat() {
        // Required empty constructor for Firebase
    }

    public BookingSeat(String seatId, String type, double price) {
        this.seatId = seatId;
        this.type = type;
        this.price = price;
    }

    protected BookingSeat(Parcel in) {
        seatId = in.readString();
        type = in.readString();
        price = in.readDouble();
    }

    public static final Creator<BookingSeat> CREATOR = new Creator<BookingSeat>() {
        @Override
        public BookingSeat createFromParcel(Parcel in) {
            return new BookingSeat(in);
        }

        @Override
        public BookingSeat[] newArray(int size) {
            return new BookingSeat[size];
        }
    };

    // Getters
    public String getSeatId() { return seatId; }
    public String getType() { return type; }
    public double getPrice() { return price; }

    // Setters
    public void setSeatId(String seatId) { this.seatId = seatId; }
    public void setType(String type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(seatId);
        dest.writeString(type);
        dest.writeDouble(price);
    }
}