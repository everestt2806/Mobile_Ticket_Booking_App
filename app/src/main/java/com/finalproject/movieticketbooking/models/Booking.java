package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import java.util.List;

public class Booking implements Parcelable {
    private String bookingId;
    private String userId;
    private String showtimeId;
    private Date bookingDate;
    private int totalTickets;
    private double totalPrice;
    private String bookingStatus;
    private String paymentStatus;
    private List<Ticket> tickets;

    // Constructors
    public Booking() {}

    protected Booking(Parcel in) {
        bookingId = in.readString();
        userId = in.readString();
        showtimeId = in.readString();
        bookingDate = new Date(in.readLong()); // Converting timestamp to Date
        totalTickets = in.readInt();
        totalPrice = in.readDouble();
        bookingStatus = in.readString();
        paymentStatus = in.readString();
        tickets = in.createTypedArrayList(Ticket.CREATOR); // Assuming Ticket implements Parcelable
    }

    // Parcelable Creator
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
        dest.writeString(bookingId);
        dest.writeString(userId);
        dest.writeString(showtimeId);
        dest.writeLong(bookingDate.getTime()); // Storing Date as timestamp
        dest.writeInt(totalTickets);
        dest.writeDouble(totalPrice);
        dest.writeString(bookingStatus);
        dest.writeString(paymentStatus);
        dest.writeTypedList(tickets); // Assuming Ticket implements Parcelable
    }

    // Getter and Setter Methods
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
