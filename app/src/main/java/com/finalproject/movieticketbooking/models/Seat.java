package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Seat implements Parcelable {
    private String id;
    private String row;
    private int number;
    private String type;
    private String status;

    // Empty constructor for Firebase
    public Seat() {}

    // Full constructor
    public Seat(String id, String row, int number, String type, String status) {
        this.id = id;
        this.row = row;
        this.number = number;
        this.type = type;
        this.status = status;
    }

    protected Seat(Parcel in) {
        id = in.readString();
        row = in.readString();
        number = in.readInt();
        type = in.readString();
        status = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(row);
        dest.writeInt(number);
        dest.writeString(type);
        dest.writeString(status);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return number == seat.number &&
                Objects.equals(id, seat.id) &&
                Objects.equals(row, seat.row) &&
                Objects.equals(type, seat.type) &&
                Objects.equals(status, seat.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, number, type, status);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id='" + id + '\'' +
                ", row='" + row + '\'' +
                ", number=" + number +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

