package com.example.rapunzel.trakter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.example.rapunzel.trakter.data.models.FollowedUser;
import com.example.rapunzel.trakter.data.viewmodels.FollowedUserRowVM;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Type;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class ViewModelTest {
    @Test
    public void shouldShowUserName() {
        final String DATA
                = "[{\"followed_at\":\"2016-03-11T23:58:57.000Z\",\"user\":{\"username\":\"testUserName\",\"private\":false,\"name\":null,\"vip\":false,\"vip_ep\":false}}]";
        Gson gson = new Gson();
        Type listType = new TypeToken<List<FollowedUser>>() {
        }.getType();
        List<FollowedUser> users = gson.fromJson(DATA, listType);
        FollowedUserRowVM viewModel = new FollowedUserRowVM(users.get(0));
        assertThat(viewModel.getDisplayText()).isEqualTo("testUserName");
    }
}
