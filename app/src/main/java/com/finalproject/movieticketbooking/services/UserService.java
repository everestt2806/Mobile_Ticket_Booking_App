package com.finalproject.movieticketbooking.services;

import com.finalproject.movieticketbooking.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import java.util.HashMap;
import java.util.Map;

public class UserService extends DatabaseService {
    private static final String REF = "users";

    public UserService() {
        super(REF);
    }

    public Query getUserByEmail(String email) {
        return db.child(REF_NAME)
                .orderByChild("email")
                .equalTo(email);
    }

    public Task<Void> createUser(String id, User user) {
        return add(id, user);
    }

    public Task<Void> updateUser(String id, Map<String, Object> updates) {
        return update(id, updates);
    }

    public Task<Void> updateUserProfile(String id, String fullName, String phoneNumber) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("fullName", fullName);
        updates.put("phoneNumber", phoneNumber);
        return update(id, updates);
    }

    public Task<Void> updateUserAvatar(String id, String avatarUrl) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("avatar", avatarUrl);
        return update(id, updates);
    }

    public Task<Void> updatePassword(String id, String newPassword) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("password", newPassword);
        return update(id, updates);
    }

    public Task<Void> addToTicketHistory(String userId, String bookingId) {
        DatabaseReference userRef = getReference(userId);
        return userRef.child("ticketHistory")
                .child(bookingId)
                .setValue(true);
    }

    public DatabaseReference getUserRef(String id) {
        return getReference(id);
    }
}