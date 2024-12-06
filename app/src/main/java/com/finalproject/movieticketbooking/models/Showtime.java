package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class Showtime implements Parcelable {
    private String showtimeId;
    private String movieId;
    private String cinemaId;
    private int screenNumber;
    private Date startTime;
    private Date endTime;
    private double ticketPrice;
    private int availableSeats;
    private int totalSeats;
    private String format;
    private String language;
    private String subtitles;

    // Constructor
    public Showtime() {
    }


    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }


    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }


    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }


    public int getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }


    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }


    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }


    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(showtimeId);
        dest.writeString(movieId);
        dest.writeString(cinemaId);
        dest.writeInt(screenNumber);
        dest.writeLong(startTime != null ? startTime.getTime() : -1);
        dest.writeLong(endTime != null ? endTime.getTime() : -1); //
        dest.writeDouble(ticketPrice);
        dest.writeInt(availableSeats);
        dest.writeInt(totalSeats);
        dest.writeString(format);
        dest.writeString(language);
        dest.writeString(subtitles);
    }


    protected Showtime(Parcel in) {
        showtimeId = in.readString();
        movieId = in.readString();
        cinemaId = in.readString();
        screenNumber = in.readInt();
        long startTimeLong = in.readLong();
        startTime = startTimeLong != -1 ? new Date(startTimeLong) : null;
        long endTimeLong = in.readLong();
        endTime = endTimeLong != -1 ? new Date(endTimeLong) : null;
        ticketPrice = in.readDouble();
        availableSeats = in.readInt();
        totalSeats = in.readInt();
        format = in.readString();
        language = in.readString();
        subtitles = in.readString();
    }


    public static final Creator<Showtime> CREATOR = new Creator<Showtime>() {
        @Override
        public Showtime createFromParcel(Parcel in) {
            return new Showtime(in);
        }

        @Override
        public Showtime[] newArray(int size) {
            return new Showtime[size];
        }
    };
}
