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
    private List<BookingSeat> seats;
    private double totalAmount;
    private String bookingTime;
    private String status;
    private String paymentMethod;

    public Booking() {
        // Required empty constructor for Firebase
    }

    public Booking(String id, String userId, String showtimeId, List<BookingSeat> seats,
                   double totalAmount, String bookingTime, String status, String paymentMethod) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seats = seats;
        this.totalAmount = totalAmount;
        this.bookingTime = bookingTime;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    protected Booking(Parcel in) {
        id = in.readString();
        userId = in.readString();
        showtimeId = in.readString();
        seats = new ArrayList<>();
        in.readTypedList(seats, BookingSeat.CREATOR);
        totalAmount = in.readDouble();
        bookingTime = in.readString();
        status = in.readString();
        paymentMethod = in.readString();
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

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getShowtimeId() { return showtimeId; }
    public List<BookingSeat> getSeats() { return seats; }
    public double getTotalAmount() { return totalAmount; }
    public String getBookingTime() { return bookingTime; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = showtimeId; }
    public void setSeats(List<BookingSeat> seats) { this.seats = seats; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setBookingTime(String bookingTime) { this.bookingTime = bookingTime; }
    public void setStatus(String status) { this.status = status; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(showtimeId);
        dest.writeTypedList(seats);
        dest.writeDouble(totalAmount);
        dest.writeString(bookingTime);
        dest.writeString(status);
        dest.writeString(paymentMethod);
    }
}
