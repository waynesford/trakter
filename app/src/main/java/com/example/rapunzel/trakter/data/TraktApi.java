package com.example.rapunzel.trakter.data;

import com.example.rapunzel.trakter.data.models.ApprovedUser;
import com.example.rapunzel.trakter.data.models.FollowedUser;
import com.example.rapunzel.trakter.data.models.OauthClientParams;
import com.example.rapunzel.trakter.data.models.OauthTokenResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface TraktApi {
    @GET("users/{username}/following")
    Observable<List<FollowedUser>> getFollowing(@Path("username") String username);

    @POST("users/{username}/follow")
    Observable<ApprovedUser> followThisUser(@Path("username") String username);

    @DELETE("users/{username}/follow")
    Observable<Void> unfollowThisUser(@Path("username") String username);

    @GET("users/{username}/lists")
    Observable<List<FollowedUser>> getLists(@Path("username") String username);

    @POST("oauth/token")
    Observable<OauthTokenResponse> exchangeCodeForAccessToken(@Body OauthClientParams params);
}
