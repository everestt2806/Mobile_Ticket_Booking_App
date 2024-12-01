package com.finalproject.movieticketbooking.models;

public class Cast {
    private String Actor;
    private String PicUrl;

    // Empty constructor
    public Cast() {}

    public String getActor() {
        return Actor;
    }

    public void setActor(String actor) {
        Actor = actor;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
