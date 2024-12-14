package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Booking implements Parcelable {
    private String id;
    private String userId;
    private String showtimeId;
    private List<String> seatIds;
    private double totalPrice;
    private String createdAt;

    public Booking(String id, String userId, String showtimeId, List<String> seatIds, double totalPrice, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatIds = seatIds;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    protected Booking(Parcel in) {
        id = in.readString();
        userId = in.readString();
        showtimeId = in.readString();
        seatIds = in.createStringArrayList();
        totalPrice = in.readDouble();
        createdAt = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(showtimeId);
        dest.writeStringList(seatIds);
        dest.writeDouble(totalPrice);
        dest.writeString(createdAt);
    }

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getShowtimeId() { return showtimeId; }
    public List<String> getSeats() { return seatIds; }
    public double getTotalPrice() { return totalPrice; }
    public String getBookingTime() { return createdAt; }


    // Setters
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = showtimeId; }
    public void setSeats(List<BookingSeat> seats) { this.seatIds = seatIds; }
    public void setTotalAmount(double totalAmount) { this.totalPrice = totalPrice; }
    public void setBookingTime(String bookingTime) { this.createdAt = createdAt; }

}
