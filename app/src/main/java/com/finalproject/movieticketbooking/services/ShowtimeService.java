package com.finalproject.movieticketbooking.services;

import com.finalproject.movieticketbooking.models.Showtime;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import java.util.Map;

public class ShowtimeService extends DatabaseService {
    private static final String REF = "showtimes";

    public ShowtimeService() {
        super(REF);
    }

    public Query getShowtimesByMovie(String movieId) {
        return db.child(REF_NAME)
                .orderByChild("movieId")
                .equalTo(movieId);
    }

    public Query getShowtimesByCinema(String cinemaId) {
        return db.child(REF_NAME)
                .orderByChild("cinemaId")
                .equalTo(cinemaId);
    }

    public Query getShowtimesByDate(String date) {
        return db.child(REF_NAME)
                .orderByChild("date")
                .equalTo(date);
    }

    public Task<Void> addShowtime(String id, Showtime showtime) {
        return add(id, showtime);
    }

    public Task<Void> updateShowtime(String id, Map<String, Object> updates) {
        return update(id, updates);
    }

    public DatabaseReference getShowtimeRef(String id) {
        return getReference(id);
    }
}
