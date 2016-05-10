package com.example.rapunzel.trakter.data;

import com.example.rapunzel.trakter.data.models.FollowedUser;
import com.example.rapunzel.trakter.data.models.User;
import com.example.rapunzel.trakter.data.viewmodels.FollowedUserRowVM;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class ApiController {

    private TraktApi mApi;

    @Inject
    public ApiController(TraktApi api) {
        mApi = api;
    }

    //this is an unused method in production, but this is just to demonstrate abstracted business logic that can be test modularly.
    public Observable<List<String>> getFollowing(String username) {
        return mApi.getFollowing(username)
                .flatMapIterable(followedUsers -> followedUsers)
                .map(FollowedUser::getUser)
                .map(User::getUsername)
                .toList();
    }

    public Observable<List<FollowedUserRowVM>> getFollowingViewModels(String username) {
        return mApi.getFollowing(username)
                .flatMapIterable(followedUsers -> followedUsers)
                .map(followedUser -> new FollowedUserRowVM(followedUser))
                .toList();
    }
}
