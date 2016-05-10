package com.example.rapunzel.trakter.data.models;

import com.google.gson.annotations.SerializedName;

public class OauthClientParams {

    @SerializedName("code")
    private String code;

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("redirect_uri")
    private String redirectUri;

    @SerializedName("grant_type")
    private String grantType;

    public OauthClientParams(String clientId, String clientSecret, String redirectUri,
            String grantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.grantType = grantType;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "OauthClientParams{" +
                "code='" + code + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret=" + clientSecret +
                ", redirectUri='" + redirectUri + '\'' +
                ", grantType='" + grantType + '\'' +
                '}';
    }
}
