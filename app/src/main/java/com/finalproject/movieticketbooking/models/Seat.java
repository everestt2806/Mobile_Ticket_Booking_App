package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Seat implements Parcelable {
    private String seatId;
    private String showtimeId;
    private String row;
    private int number;
    private String type;
    private String status;
    private double priceModifier;

    // Constructors
    public Seat() {}

    protected Seat(Parcel in) {
        seatId = in.readString();
        showtimeId = in.readString();
        row = in.readString();
        number = in.readInt();
        type = in.readString();
        status = in.readString();
        priceModifier = in.readDouble();
    }

    // Parcelable Creator
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(seatId);
        dest.writeString(showtimeId);
        dest.writeString(row);
        dest.writeInt(number);
        dest.writeString(type);
        dest.writeString(status);
        dest.writeDouble(priceModifier);
    }

    // Getter and Setter Methods
    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPriceModifier() {
        return priceModifier;
    }

    public void setPriceModifier(double priceModifier) {
        this.priceModifier = priceModifier;
    }
}
