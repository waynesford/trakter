package com.example.rapunzel.trakter.data.models;

import com.google.gson.annotations.SerializedName;

public class OauthTokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private long expiresIn;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "OauthTokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
