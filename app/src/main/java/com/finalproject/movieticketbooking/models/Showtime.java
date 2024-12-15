package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Showtime implements Parcelable {
    private String id;
    private String movieId;
    private String cinemaId;
    private String roomId;
    private String startTime;
    private HashMap<String, Integer> price;

    public Showtime() {} // Required empty constructor for Firebase
    public Showtime(String id, String movieId, String cinemaId, String roomId, String startTime, HashMap<String, Integer> price) {
        this.id = id;
        this.movieId = movieId;
        this.cinemaId = cinemaId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.price = price;
    }

    protected Showtime(Parcel in) {
        id = in.readString();
        movieId = in.readString();
        cinemaId = in.readString();
        roomId = in.readString();
        startTime = in.readString();
        price = (HashMap<String, Integer>) in.readSerializable();
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
        dest.writeSerializable(price);
    }

    // Getters
    public String getId() { return id; }
    public String getMovieId() { return movieId; }
    public String getCinemaId() { return cinemaId; }
    public String getRoomId() { return roomId; }
    public String getStartTime() { return startTime; }
    public Map<String, Integer> getPrice() { return price; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public void setCinemaId(String cinemaId) { this.cinemaId = cinemaId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setPrice(HashMap<String, Integer> price) { this.price = price; }

    @Override
    public String toString() {
        return "Showtime{" +
                "id='" + id + '\'' +
                ", cinemaId='" + cinemaId + '\'' +
                ", startTime='" + startTime + '\'' +
                '}';
    }

}
