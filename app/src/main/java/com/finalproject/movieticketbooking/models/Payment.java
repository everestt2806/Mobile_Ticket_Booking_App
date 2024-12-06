package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class Payment implements Parcelable {
    private String paymentId;
    private String bookingId;
    private String userId;
    private double amount;
    private String paymentMethod;
    private Date transactionDate;
    private String paymentStatus;
    private String transactionId;

    // Constructors
    public Payment() {}

    protected Payment(Parcel in) {
        paymentId = in.readString();
        bookingId = in.readString();
        userId = in.readString();
        amount = in.readDouble();
        paymentMethod = in.readString();
        transactionDate = new Date(in.readLong()); // Reading Date from long (milliseconds)
        paymentStatus = in.readString();
        transactionId = in.readString();
    }

    // Parcelable Creator
    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(paymentId);
        dest.writeString(bookingId);
        dest.writeString(userId);
        dest.writeDouble(amount);
        dest.writeString(paymentMethod);
        dest.writeLong(transactionDate != null ? transactionDate.getTime() : -1); // Writing Date as long
        dest.writeString(paymentStatus);
        dest.writeString(transactionId);
    }

    // Getter and Setter Methods
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
