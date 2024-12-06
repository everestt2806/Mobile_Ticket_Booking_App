package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ticket implements Parcelable {
    private String ticketId;
    private String bookingId;
    private String showtimeId;
    private String seatId;
    private String userId;
    private double price;
    private String status;
    private String qrCode;

    // Constructors
    public Ticket() {}

    protected Ticket(Parcel in) {
        ticketId = in.readString();
        bookingId = in.readString();
        showtimeId = in.readString();
        seatId = in.readString();
        userId = in.readString();
        price = in.readDouble();
        status = in.readString();
        qrCode = in.readString();
    }

    // Parcelable Creator
    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ticketId);
        dest.writeString(bookingId);
        dest.writeString(showtimeId);
        dest.writeString(seatId);
        dest.writeString(userId);
        dest.writeDouble(price);
        dest.writeString(status);
        dest.writeString(qrCode);
    }

    // Getter and Setter Methods
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
