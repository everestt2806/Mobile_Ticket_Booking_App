package com.finalproject.movieticketbooking.services;

import com.finalproject.movieticketbooking.models.Room;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import java.util.Map;

public class RoomService extends DatabaseService {
    private static final String REF = "rooms";

    public RoomService() {
        super(REF);
    }

    public Query getRoomsByCinema(String cinemaId) {
        return db.child(REF_NAME)
                .orderByChild("cinemaId")
                .equalTo(cinemaId);
    }

    public Task<Void> addRoom(String id, Room room) {
        return add(id, room);
    }

    public Task<Void> updateRoom(String id, Map<String, Object> updates) {
        return update(id, updates);
    }

    public DatabaseReference getRoomRef(String id) {
        return getReference(id);
    }
}
