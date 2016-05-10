package com.example.rapunzel.trakter.data;

import com.example.rapunzel.trakter.BuildConfig;
import com.example.rapunzel.trakter.data.models.OauthClientParams;

import android.net.Uri;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String CLIENT_ID = BuildConfig.TRAKT_API_KEY;

    private static Uri buildOauthUri() {
        final String url = "https://trakt.tv/oauth/authorize?";
        return Uri.parse(url)
                .buildUpon()
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("client_id", CLIENT_ID)
                .appendQueryParameter("redirect_uri", BuildConfig.TRAKT_REDIRECT_URI)
                .build();
    }

    public static final String OAUTH_SIGNIN_URL = buildOauthUri().toString();


    @Provides
    @Singleton
    OauthClientParams provideClientParams() {
        return new OauthClientParams(
                CLIENT_ID,
                BuildConfig.TRAKT_CLIENT_SECRET,
                BuildConfig.TRAKT_REDIRECT_URI,
                BuildConfig.TRAKT_GRANT_TYPE
                );
    }

    @Provides
    Interceptor provideHeadersInterceptor(AppPreferences appPreferences) {
        String accessToken = appPreferences.accessToken.get();
        return chain -> {
            Request.Builder builder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-key", BuildConfig.TRAKT_API_KEY)
                    .addHeader("trakt-api-version", "2");

            if (accessToken != null) {
                builder.addHeader("Authorization", "Bearer " + accessToken);
            }
            Request request = builder.build();
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Interceptor headersInterceptor) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(headersInterceptor)
                .addInterceptor(interceptor)
                .build();
    }


    @Provides
    @Singleton
    TraktApi provideTraktApi(OkHttpClient client){
        Retrofit retroFit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://api-v2launch.trakt.tv/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retroFit.create(TraktApi.class);
    }
}
