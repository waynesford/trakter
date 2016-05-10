package com.example.rapunzel.trakter.ui;

import com.example.rapunzel.trakter.data.ApiModule;
import com.example.rapunzel.trakter.data.AppPreferences;
import com.example.rapunzel.trakter.R;
import com.example.rapunzel.trakter.data.TraktApi;
import com.example.rapunzel.trakter.TrakterApp;
import com.example.rapunzel.trakter.data.models.OauthClientParams;
import com.example.rapunzel.trakter.data.models.OauthTokenResponse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class AuthActivity extends AppCompatActivity {

    public static final String TAG = AuthActivity.class.getSimpleName();

    @Inject
    TraktApi mService;

    @Inject
    AppPreferences mPreferences;

    @Inject
    OauthClientParams mClientParams;

    @BindView(R.id.webview)
    WebView mWebView;

    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TrakterApp) getApplication()).getComponent().inject(this);
        setContentView(R.layout.auth_activity);
        ButterKnife.bind(this);
        Log.d(TAG, "oncreate: ");

        mWebView.clearCache(true);
        mWebView.clearHistory();
        // need to get access token with OAuth2.0
        // set up webview for OAuth2 login
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished:" + url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "loading: " + url);

                //extract code first, which we will exchange for an access token
                String codeMappingKey = "code=";
                if (url.contains(codeMappingKey)) {
                    int position = url.indexOf(codeMappingKey) + codeMappingKey.length();
                    String accessCode = url.substring(position);
                    mClientParams.setCode(accessCode);

                    mCompositeSubscription.add(mService.exchangeCodeForAccessToken(mClientParams)
                            .subscribeOn(Schedulers.io())
                            .doOnNext(oauthTokenResponse -> Timber.d("doOnNext: %s", oauthTokenResponse.toString()))
                            .map(OauthTokenResponse::getAccessToken)
                            .doOnNext(accessToken -> mPreferences.accessToken.set(accessToken))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(accessToken -> Timber.d("onNext: " + accessToken),
                                    throwable -> {
                                        Timber.e(throwable, "error code for token exchange:");
                                        setResult(RESULT_CANCELED);
                                        finish();

                                    },
                                    () -> {
                                        Timber.d("completed auth, got access token");
                                        setResult(RESULT_OK);
                                        finish();
                                    }));
                    return true;
                }


                // load the webpage from url: login and grant access
                return super.shouldOverrideUrlLoading(view, url); // return false;
            }
        });
        mWebView.loadUrl(ApiModule.OAUTH_SIGNIN_URL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }
}
