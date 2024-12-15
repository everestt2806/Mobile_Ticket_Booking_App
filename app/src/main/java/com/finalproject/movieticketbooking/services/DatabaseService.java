package com.finalproject.movieticketbooking.services;

import androidx.annotation.NonNull;

import com.finalproject.movieticketbooking.models.Cinema;
import com.finalproject.movieticketbooking.models.Movie;
import com.finalproject.movieticketbooking.models.Showtime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseService {
    protected final DatabaseReference db;

    public DatabaseService() {
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    // ===== MOVIE METHODS =====
    public Query getNowShowingMovies() {
        return db.child("movies")
                .orderByChild("status")
                .equalTo("NOW_SHOWING");
    }

    public Query getComingSoonMovies() {
        return db.child("movies")
                .orderByChild("status")
                .equalTo("COMING_SOON");
    }

    public DatabaseReference getMovieRef(String movieId) {
        return db.child("movies").child(movieId);
    }

    public void getMovieById(String movieId, OnMovieCallback callback) {
        db.child("movies").child(movieId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Movie movie = snapshot.getValue(Movie.class);
                        if (movie != null) {
                            callback.onSuccess(movie);
                        } else {
                            callback.onFailure("Movie not found");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onFailure(error.getMessage());
                    }
                });
    }

    // ===== CINEMA METHODS =====

    public void getAllCinemas(final OnCinemasCallback callback) {
        db.child("cinemas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Cinema> cinemas = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Cinema cinema = childSnapshot.getValue(Cinema.class);
                    if (cinema != null) {
                        cinemas.add(cinema);
                    }
                }
                callback.onSuccess(cinemas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }


    public Query getCinemasByCity(String city) {
        return db.child("cinemas")
                .orderByChild("city")
                .equalTo(city);
    }

    public DatabaseReference getCinemaRef(String id) {
        return db.child("cinemas").child(id);
    }

    public void getCinemaById(String cinemaId, final OnCinemaCallback callback) {
        db.child("cinemas").child(cinemaId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Cinema cinema = snapshot.getValue(Cinema.class);
                            if (cinema != null) {
                                callback.onSuccess(cinema);
                            } else {
                                callback.onFailure("Cinema not found");
                            }
                        } else {
                            callback.onFailure("Cinema not found");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onFailure(error.getMessage());
                    }
                });
    }

    // ===== SHOWTIME METHODS =====
    public DatabaseReference getShowtimeRef(String id) {
        return db.child("showtimes").child(id);
    }

    public void getShowtime(String id, final OnShowtimeCallback callback) {
        db.child("showtimes").child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Showtime showtime = snapshot.getValue(Showtime.class);
                        if (showtime != null) {
                            callback.onSuccess(showtime);
                        } else {
                            callback.onFailure("Showtime not found");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onFailure(error.getMessage());
                    }
                });
    }

    public void getShowtimesByMovie(String movieId, final OnShowtimesCallback callback) {
        db.child("showtimes")
                .orderByChild("movieId")
                .equalTo(movieId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Showtime> showtimes = new ArrayList<>();
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            Showtime showtime = childSnapshot.getValue(Showtime.class);
                            if (showtime != null) {
                                showtimes.add(showtime);
                            }
                        }
                        callback.onSuccess(showtimes);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onFailure(error.getMessage());
                    }
                });
    }

    // ===== CALLBACK INTERFACES =====
    public interface OnMovieCallback {
        void onSuccess(Movie movie);
        void onFailure(String errorMessage);
    }

    public interface OnCinemaCallback {
        void onSuccess(Cinema cinema);
        void onFailure(String errorMessage);
    }

    public interface OnShowtimeCallback {
        void onSuccess(Showtime showtime);
        void onFailure(String errorMessage);
    }

    public interface OnCinemasCallback {
        void onSuccess(List<Cinema> cinemas);
        void onFailure(String errorMessage);
    }

    public interface OnShowtimesCallback {
        void onSuccess(List<Showtime> showtimes);
        void onFailure(String errorMessage);
    }
}
