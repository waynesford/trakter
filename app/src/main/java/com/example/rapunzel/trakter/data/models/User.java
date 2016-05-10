package com.example.rapunzel.trakter.data.models;

import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("username")
    private String username;

    @SerializedName("private")
    private boolean _private;

    @SerializedName("name")
    private String name;

    @SerializedName("vip")
    private boolean vip;

    @SerializedName("vip_ep")
    private boolean vip_ep;

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", _private=" + _private +
                ", name='" + name + '\'' +
                ", vip=" + vip +
                ", vip_ep=" + vip_ep +
                '}';
    }
}
