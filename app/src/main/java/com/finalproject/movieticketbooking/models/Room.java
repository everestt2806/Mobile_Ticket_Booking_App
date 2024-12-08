package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class Room implements Parcelable {
    private String id;
    private String cinemaId;
    private String name;
    private int rows;
    private int columns;
    private Map<String, SeatInfo> seatMap;

    // Constructor
    public Room() {
        this.seatMap = new HashMap<>();
    }

    public Room(String id, String cinemaId, String name, int rows, int columns) {
        this.id = id;
        this.cinemaId = cinemaId;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.seatMap = new HashMap<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Map<String, SeatInfo> getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(Map<String, SeatInfo> seatMap) {
        this.seatMap = seatMap;
    }

    // Parcelable implementation
    protected Room(Parcel in) {
        id = in.readString();
        cinemaId = in.readString();
        name = in.readString();
        rows = in.readInt();
        columns = in.readInt();
        int seatMapSize = in.readInt();
        seatMap = new HashMap<>();
        for (int i = 0; i < seatMapSize; i++) {
            String key = in.readString();
            SeatInfo value = in.readParcelable(SeatInfo.class.getClassLoader());
            seatMap.put(key, value);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cinemaId);
        dest.writeString(name);
        dest.writeInt(rows);
        dest.writeInt(columns);
        dest.writeInt(seatMap.size());
        for (Map.Entry<String, SeatInfo> entry : seatMap.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    @Override
    public int describeContents() {
        return 0;
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

    // Inner class SeatInfo
    public static class SeatInfo implements Parcelable {
        private String type; // standard, vip, couple

        public SeatInfo() {
        }

        public SeatInfo(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        // Parcelable implementation for SeatInfo
        protected SeatInfo(Parcel in) {
            type = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(type);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SeatInfo> CREATOR = new Creator<SeatInfo>() {
            @Override
            public SeatInfo createFromParcel(Parcel in) {
                return new SeatInfo(in);
            }

            @Override
            public SeatInfo[] newArray(int size) {
                return new SeatInfo[size];
            }
        };
    }

    // Optional: ToString method for debugging
    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", cinemaId='" + cinemaId + '\'' +
                ", name='" + name + '\'' +
                ", rows=" + rows +
                ", columns=" + columns +
                ", seatMap=" + seatMap +
                '}';
    }
}

