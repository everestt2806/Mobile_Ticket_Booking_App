package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

import java.util.List;

public class Movie implements Parcelable {
    private String movieId;
    private String name;
    private List<String> genre;
    private String description;
    private int releaseYear;
    private String poster;
    private double imdbRating;
    private String runtime;
    private double price;
    private List<Showtime> showtimes;
    private List<Cast> casts;

    public Movie() {}

    @PropertyName("movie_id")
    public String getMovieId() {
        return movieId;
    }

    @PropertyName("movie_id")
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("genre")
    public List<String> getGenre() {
        return genre;
    }

    @PropertyName("genre")
    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    @PropertyName("description")
    public String getDescription() {
        return description;
    }

    @PropertyName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("release_year")
    public int getReleaseYear() {
        return releaseYear;
    }

    @PropertyName("release_year")
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @PropertyName("poster")
    public String getPoster() {
        return poster;
    }

    @PropertyName("poster")
    public void setPoster(String poster) {
        this.poster = poster;
    }

    @PropertyName("imdb_rating")
    public double getImdbRating() {
        return imdbRating;
    }

    @PropertyName("imdb_rating")
    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    @PropertyName("runtime")
    public String getRuntime() {
        return runtime;
    }

    @PropertyName("runtime")
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    @PropertyName("price")
    public double getPrice() {
        return price;
    }

    @PropertyName("price")
    public void setPrice(double price) {
        this.price = price;
    }

    @PropertyName("showtimes")
    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    @PropertyName("showtimes")
    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    @PropertyName("casts")
    public List<Cast> getCasts() {
        return casts;
    }

    @PropertyName("casts")
    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }


    protected Movie(Parcel in) {
        movieId = in.readString();
        name = in.readString();
        description = in.readString();
        poster = in.readString();
        runtime = in.readString();
        releaseYear = in.readInt();
        imdbRating = in.readDouble();
        price = in.readDouble();
        genre = in.createStringArrayList();
        showtimes = in.createTypedArrayList(Showtime.CREATOR);
        casts = in.createTypedArrayList(Cast.CREATOR);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(poster);
        dest.writeString(runtime);
        dest.writeInt(releaseYear);
        dest.writeDouble(imdbRating);
        dest.writeDouble(price);
        dest.writeStringList(genre);
        dest.writeTypedList(showtimes);
        dest.writeTypedList(casts);
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
