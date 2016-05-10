package com.example.rapunzel.trakter.data.viewmodels;

import com.example.rapunzel.trakter.data.models.FollowedUser;

/**
 * A ViewModel, which provides for easier testing of business logic, and also keeps business logic out of our models.
 */
public class FollowedUserRowVM {

    private FollowedUser mFollowedUser;
    private String mName;

    public FollowedUserRowVM(FollowedUser user) {
        mFollowedUser = user;
    }

    public FollowedUserRowVM(String name) {
        mName = name;
    }

    public String getDisplayText() {
        if (mFollowedUser != null) {
            return mFollowedUser.getUser().getUsername();
        }
        return mName;
    }
}
