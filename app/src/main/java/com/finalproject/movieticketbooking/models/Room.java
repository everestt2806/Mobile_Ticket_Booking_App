package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Room implements Parcelable {
    private String id;
    private String cinemaId;
    private String name;
    private int rows;
    private int columns;
    private HashMap<String, Seat> seatMap;

    public Room(String id, String cinemaId, String name, int rows, int columns, HashMap<String, Seat> seatMap) {
        this.id = id;
        this.cinemaId = cinemaId;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.seatMap = seatMap;
    }

    protected Room(Parcel in) {
        id = in.readString();
        cinemaId = in.readString();
        name = in.readString();
        rows = in.readInt();
        columns = in.readInt();
        seatMap = (HashMap<String, Seat>) in.readSerializable();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cinemaId);
        dest.writeString(name);
        dest.writeInt(rows);
        dest.writeInt(columns);
        dest.writeSerializable(seatMap);
    }

    // Getters
    public String getId() { return id; }
    public String getCinemaId() { return cinemaId; }
    public String getName() { return name; }
    public int getRows() { return rows; }
    public int getColumns() { return columns; }
    public Map<String, Seat> getSeatMap() { return seatMap; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setCinemaId(String cinemaId) { this.cinemaId = cinemaId; }
    public void setName(String name) { this.name = name; }
    public void setRows(int rows) { this.rows = rows; }
    public void setColumns(int columns) { this.columns = columns; }
    public void setSeatMap(HashMap<String, Seat> seatMap) { this.seatMap = seatMap; }

}


