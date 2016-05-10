package com.example.rapunzel.trakter.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class FollowedUser {

    @SerializedName("followed_at")
    private Date followedAt;

    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "FollowedUser{" +
                "followed_at='" + followedAt + '\'' +
                ", user=" + user +
                '}';
    }
}
