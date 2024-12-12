package com.finalproject.movieticketbooking.services;

import com.finalproject.movieticketbooking.models.Booking;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import java.util.HashMap;
import java.util.Map;

public class BookingService extends DatabaseService {
    private static final String REF = "bookings";

    public BookingService() {
        super(REF);
    }

    public Query getBookingsByUser(String userId) {
        return db.child(REF_NAME)
                .orderByChild("userId")
                .equalTo(userId);
    }

    public Query getBookingsByShowtime(String showtimeId) {
        return db.child(REF_NAME)
                .orderByChild("showtimeId")
                .equalTo(showtimeId);
    }

    public Task<Void> createBooking(String id, Booking booking) {
        return add(id, booking);
    }

    public Task<Void> updateBookingStatus(String id, String status) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", status);
        return update(id, updates);
    }

    public DatabaseReference getBookingRef(String id) {
        return getReference(id);
    }
}
