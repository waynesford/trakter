package com.example.rapunzel.trakter.data;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferences {

    public final Preference<String> accessToken;

    @Inject
    public AppPreferences(RxSharedPreferences rxPreferences) {
        accessToken = rxPreferences.getString("access_token");
    }

    public void signOut() {
        accessToken.delete();
    }
}
