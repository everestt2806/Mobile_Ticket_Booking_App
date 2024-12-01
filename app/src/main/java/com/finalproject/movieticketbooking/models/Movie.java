package com.finalproject.movieticketbooking.models;

import java.util.List;

public class Movie {
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
}
