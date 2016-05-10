package com.example.rapunzel.trakter;

import android.app.Application;

import timber.log.Timber;

public class TrakterApp extends Application {
    AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }

    //this is for testing. Unfortunately, limitations of Dagger 2.0 don't allow subclassing of modules
    //https://stackoverflow.com/questions/26939340/how-do-you-override-a-module-dependency-in-a-unit-test-with-dagger-2-0/29996385#29996385
    public void setComponent(AppComponent appComponent) {
        mComponent = appComponent;
    }
}

