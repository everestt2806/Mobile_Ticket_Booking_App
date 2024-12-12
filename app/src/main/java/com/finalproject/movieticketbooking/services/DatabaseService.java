package com.finalproject.movieticketbooking.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class DatabaseService {
    protected final DatabaseReference db;
    protected final String REF_NAME;

    public DatabaseService(String refName) {
        this.db = FirebaseDatabase.getInstance().getReference();
        this.REF_NAME = refName;
    }

    // Generic methods
    protected Task<Void> add(String id, Object object) {
        return db.child(REF_NAME).child(id).setValue(object);
    }

    protected Task<Void> update(String id, Map<String, Object> updates) {
        return db.child(REF_NAME).child(id).updateChildren(updates);
    }

    protected Task<Void> delete(String id) {
        return db.child(REF_NAME).child(id).removeValue();
    }

    protected DatabaseReference getReference(String id) {
        return db.child(REF_NAME).child(id);
    }
}
