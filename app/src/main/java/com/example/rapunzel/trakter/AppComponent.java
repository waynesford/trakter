package com.example.rapunzel.trakter;

import com.example.rapunzel.trakter.data.ApiModule;
import com.example.rapunzel.trakter.ui.AuthActivity;
import com.example.rapunzel.trakter.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(AuthActivity activity);
}

