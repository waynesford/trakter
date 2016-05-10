package com.example.rapunzel.trakter.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ApprovedUser {

    @SerializedName("approved_at")
    private Date approvedAt;

    @SerializedName("user")
    private User user;

    @Override
    public String toString() {
        return "ApprovedUser{" +
                "approvedAt='" + approvedAt + '\'' +
                ", user=" + user +
                '}';
    }
}
