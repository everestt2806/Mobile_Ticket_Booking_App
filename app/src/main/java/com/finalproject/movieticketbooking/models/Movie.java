package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
public class Movie implements Parcelable {
    private String id;
    private String title;
    private String description;
    private int duration;
    private String releaseDate;
    private String poster;
    private String trailer;
    private String status;
    private String language;
    private String subtitle;
    private String ageRating;
    private List<String> actors;
    private List<String> genres;

    public Movie() {
        // Required empty constructor for Firebase
    }

    public Movie(String id, String title, String description, int duration,
                 String releaseDate, String poster, String trailer, String status,
                 String language, String subtitle, String ageRating,
                 List<String> actors, List<String> genres) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.trailer = trailer;
        this.status = status;
        this.language = language;
        this.subtitle = subtitle;
        this.ageRating = ageRating;
        this.actors = actors;
        this.genres = genres;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        duration = in.readInt();
        releaseDate = in.readString();
        poster = in.readString();
        trailer = in.readString();
        status = in.readString();
        language = in.readString();
        subtitle = in.readString();
        ageRating = in.readString();
        actors = in.createStringArrayList();
        genres = in.createStringArrayList();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getDuration() { return duration; }
    public String getReleaseDate() { return releaseDate; }
    public String getPoster() { return poster; }
    public String getTrailer() { return trailer; }
    public String getStatus() { return status; }
    public String getLanguage() { return language; }
    public String getSubtitle() { return subtitle; }
    public String getAgeRating() { return ageRating; }
    public List<String> getActors() { return actors; }
    public List<String> getGenres() { return genres; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setPoster(String poster) { this.poster = poster; }
    public void setTrailer(String trailer) { this.trailer = trailer; }
    public void setStatus(String status) { this.status = status; }
    public void setLanguage(String language) { this.language = language; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public void setAgeRating(String ageRating) { this.ageRating = ageRating; }
    public void setActors(List<String> actors) { this.actors = actors; }
    public void setGenres(List<String> genres) { this.genres = genres; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(duration);
        dest.writeString(releaseDate);
        dest.writeString(poster);
        dest.writeString(trailer);
        dest.writeString(status);
        dest.writeString(language);
        dest.writeString(subtitle);
        dest.writeString(ageRating);
        dest.writeStringList(actors);
        dest.writeStringList(genres);
    }
}
