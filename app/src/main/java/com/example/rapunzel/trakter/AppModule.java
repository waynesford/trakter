package com.example.rapunzel.trakter;

import com.f2prateek.rx.preferences.RxSharedPreferences;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class AppModule {

    private final TrakterApp mApp;

    public AppModule(TrakterApp app) {
        this.mApp = app;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application app) {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides @Singleton
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences prefs) {
        return RxSharedPreferences.create(prefs);
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApp;
    }
}
