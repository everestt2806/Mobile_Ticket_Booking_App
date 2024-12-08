package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class Showtime implements Parcelable {
    private String id;
    private String movieId;
    private String cinemaId;
    private String roomId;
    private String startTime;
    private Map<String, Integer> price;
    // Empty constructor for Firebase
    public Showtime() {}

    // Full constructor
    public Showtime(String id, String movieId, String cinemaId, String roomId,
                    String startTime, Map<String, Integer> price) {
        this.id = id;
        this.movieId = movieId;
        this.cinemaId = cinemaId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.price = price;
    }

    // Parcelable implementation
    protected Showtime(Parcel in) {
        id = in.readString();
        movieId = in.readString();
        cinemaId = in.readString();
        roomId = in.readString();
        startTime = in.readString();
        price = new HashMap<>();
        int priceSize = in.readInt();
        for (int i = 0; i < priceSize; i++) {
            String key = in.readString();
            int value = in.readInt();
            price.put(key, value);
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(movieId);
        dest.writeString(cinemaId);
        dest.writeString(roomId);
        dest.writeString(startTime);
        dest.writeInt(price.size());
        for (Map.Entry<String, Integer> entry : price.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeInt(entry.getValue());
        }
    }



    // Getters and Setters for Showtime
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Map<String, Integer> getPrice() {
        return price;
    }

    public void setPrice(Map<String, Integer> price) {
        this.price = price;
    }


}
