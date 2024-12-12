package com.finalproject.movieticketbooking.services;

import com.finalproject.movieticketbooking.models.Cinema;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import java.util.Map;

public class CinemaService extends DatabaseService {
    private static final String REF = "cinemas";

    public CinemaService() {
        super(REF);
    }

    public Query getCinemasByCity(String city) {
        return db.child(REF_NAME)
                .orderByChild("city")
                .equalTo(city);
    }

    public Task<Void> addCinema(String id, Cinema cinema) {
        return add(id, cinema);
    }

    public Task<Void> updateCinema(String id, Map<String, Object> updates) {
        return update(id, updates);
    }

    public DatabaseReference getCinemaRef(String id) {
        return getReference(id);
    }
}