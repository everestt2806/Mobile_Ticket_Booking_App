package com.finalproject.movieticketbooking.services;

import com.google.firebase.database.Query;

public class BannerService extends DatabaseService {
    private static final String REF = "banners";

    public BannerService() {
        super(REF);
    }

    public Query getAllBanners() {
        return db.child(REF_NAME).orderByKey();
    }
}
