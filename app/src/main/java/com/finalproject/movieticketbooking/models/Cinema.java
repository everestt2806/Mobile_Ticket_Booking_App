package com.finalproject.movieticketbooking.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Cinema implements Parcelable {
    private String cinemaId;
    private String name;
    private String address;
    private int totalScreens;
    private String contactNumber;
    private Location location;
    private List<String> amenities;

    // Constructors
    public Cinema() {}

    protected Cinema(Parcel in) {
        cinemaId = in.readString();
        name = in.readString();
        address = in.readString();
        totalScreens = in.readInt();
        contactNumber = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        amenities = in.createStringArrayList();
    }

    // Parcelable Creator
    public static final Creator<Cinema> CREATOR = new Creator<Cinema>() {
        @Override
        public Cinema createFromParcel(Parcel in) {
            return new Cinema(in);
        }

        @Override
        public Cinema[] newArray(int size) {
            return new Cinema[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cinemaId);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeInt(totalScreens);
        dest.writeString(contactNumber);
        dest.writeParcelable(location, flags);
        dest.writeStringList(amenities);
    }

    // Getter and Setter Methods
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalScreens() {
        return totalScreens;
    }

    public void setTotalScreens(int totalScreens) {
        this.totalScreens = totalScreens;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    // Inner Location class
    public static class Location implements Parcelable {
        private double latitude;
        private double longitude;

        // Constructor
        public Location() {}

        protected Location(Parcel in) {
            latitude = in.readDouble();
            longitude = in.readDouble();
        }

        public static final Creator<Location> CREATOR = new Creator<Location>() {
            @Override
            public Location createFromParcel(Parcel in) {
                return new Location(in);
            }

            @Override
            public Location[] newArray(int size) {
                return new Location[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(latitude);
            dest.writeDouble(longitude);
        }

        // Getter and Setter Methods
        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
