package com.example.rapunzel.trakter.ui;

import com.example.rapunzel.trakter.R;
import com.example.rapunzel.trakter.TrakterApp;
import com.example.rapunzel.trakter.data.ApiController;
import com.example.rapunzel.trakter.data.AppPreferences;
import com.example.rapunzel.trakter.data.TraktApi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int ACCESS_TOKEN_REQ_CODE = 100;

    @Inject
    TraktApi mApi;

    @Inject
    ApiController mApiController;

    @Inject
    AppPreferences mPreferences;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private MyAdapter mAdapter = new MyAdapter();

    //region Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TrakterApp) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onactivityResult: request %d, result %d", requestCode, resultCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }
    //endregion

    @OnClick(R.id.runbutton)
    void auth() {
        // check whether access token already saved
        String accessToken = mPreferences.accessToken.get();
        if (accessToken == null) {
            Timber.d("access token is null");
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivityForResult(intent, ACCESS_TOKEN_REQ_CODE);
            return;
        }

        mCompositeSubscription.add(mApiController.getFollowingViewModels("turtlewayne")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(followedUsers -> Timber.d(followedUsers.toString()))
                .subscribe(followedUsers -> {
                            mAdapter.setData(followedUsers);
                            mAdapter.notifyDataSetChanged();
                        },
                        throwable -> Timber.d(throwable, "error happened:"),
                        () -> Timber.d("completed get following")));
    }

    @OnClick(R.id.clearbutton)
    void clearData() {
        mPreferences.signOut();
    }

    @OnClick(R.id.addSean)
    void addSean() {
        mCompositeSubscription.add(mApi.followThisUser("sean")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(approvedUser -> Timber.d(approvedUser.toString()))
                .subscribe(approvedUser -> {
                        },
                        throwable -> Timber.d(throwable, "error happened:"),
                        () -> Timber.d("completed add sean")));
    }

    @OnClick(R.id.deleteSean)
    void deleteSean() {
        mCompositeSubscription.add(mApi.unfollowThisUser("sean")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(empty -> Timber.d("do on next!"))
                .subscribe(empty -> {
                        },
                        throwable -> Timber.d(throwable, "error happened:"),
                        () -> Timber.d("completed delete sean")));
    }
}
