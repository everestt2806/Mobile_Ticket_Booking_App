package com.finalproject.movieticketbooking.services;

import com.finalproject.movieticketbooking.models.Movie;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import java.util.Map;

public class MovieService extends DatabaseService {
    private static final String REF = "movies";

    public MovieService() {
        super(REF);
    }

    public Query getNowPlayingMovies() {
        return db.child(REF_NAME)
                .orderByChild("status")
                .equalTo("NOW_SHOWING");
    }

    public Query getUpcomingMovies() {
        return db.child(REF_NAME)
                .orderByChild("status")
                .equalTo("COMING_SOON");
    }

    public Task<Void> addMovie(String id, Movie movie) {
        return add(id, movie);
    }

    public Task<Void> updateMovie(String id, Map<String, Object> updates) {
        return update(id, updates);
    }

    public DatabaseReference getMovieRef(String id) {
        return getReference(id);
    }
}
