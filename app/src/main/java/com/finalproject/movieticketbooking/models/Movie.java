package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movie implements Parcelable {
    private String Title;
    private String Description;
    private String Time;
    private String Poster;
    private String Trailer;
    private int Year;
    private double Imdb;
    private double price;
    private List<String> Genre;
    private List<Cast> Casts;

    // Empty constructor for Firebase
    public Movie() {}

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public double getImdb() {
        return Imdb;
    }

    public void setImdb(double imdb) {
        Imdb = imdb;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getGenre() {
        return Genre;
    }

    public void setGenre(List<String> genre) {
        Genre = genre;
    }

    public List<Cast> getCasts() {
        return Casts;
    }

    public void setCasts(List<Cast> casts) {
        Casts = casts;
    }

    // Parcelable implementation
    protected Movie(Parcel in) {
        Title = in.readString();
        Description = in.readString();
        Time = in.readString();
        Poster = in.readString();
        Trailer = in.readString();
        Year = in.readInt();
        Imdb = in.readDouble();
        price = in.readDouble();
        Genre = in.createStringArrayList();
        Casts = in.createTypedArrayList(Cast.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Description);
        dest.writeString(Time);
        dest.writeString(Poster);
        dest.writeString(Trailer);
        dest.writeInt(Year);
        dest.writeDouble(Imdb);
        dest.writeDouble(price);
        dest.writeStringList(Genre);
        dest.writeTypedList(Casts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
