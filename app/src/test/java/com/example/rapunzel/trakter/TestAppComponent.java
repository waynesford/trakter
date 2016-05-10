package com.example.rapunzel.trakter;

import com.example.rapunzel.trakter.data.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
interface TestAppComponent extends AppComponent {
    void inject(MainActivityTest activity);
}
