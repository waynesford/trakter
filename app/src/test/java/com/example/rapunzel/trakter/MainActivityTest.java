package com.example.rapunzel.trakter;

import com.example.rapunzel.trakter.data.ApiController;
import com.example.rapunzel.trakter.data.ApiModule;
import com.example.rapunzel.trakter.data.TraktApi;
import com.example.rapunzel.trakter.data.models.FollowedUser;
import com.example.rapunzel.trakter.ui.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.os.Build;
import android.widget.Button;

import java.util.List;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class MainActivityTest {

    @Inject
    TraktApi mApi;

    @Inject
    ApiController mApiController;

    @Before
    public void setTestComponent() {
        TrakterApp app = ((TrakterApp) RuntimeEnvironment.application);
        TestAppComponent appComponent = DaggerTestAppComponent.builder()
                .appModule(new AppModule(app))
                .apiModule(new ApiModule() {
                    @Override
                    public OkHttpClient provideOkHttpClient(Interceptor headersInterceptor) {
                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                        return new OkHttpClient.Builder()
                                .addInterceptor(headersInterceptor)
                                .addInterceptor(interceptor)
                                .addInterceptor(new MockInterceptor())
                                .build();
                    }
                })
                .build();
        appComponent.inject(this);
    }

    @Test
    public void shouldShowCorrectText() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Button runButton = (Button) activity.findViewById(R.id.runbutton);
        assertThat(runButton.getText().toString()).isEqualTo("Get Followers");
    }

    @Test
    public void getFollowersShouldSuccessfullyReturn() {
        TestSubscriber<List<FollowedUser>> testSubscriber = new TestSubscriber<>();
        mApi.getFollowing("turtlewayne").subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        String actualName = testSubscriber.getOnNextEvents().get(0).get(0).getUser().getUsername();
        assertThat(actualName).isEqualTo("testUserName");
    }

    @Test
    public void getFollowersShouldBeSantized() {
        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
        mApiController.getFollowing("turtlewayne").subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        String actualName = testSubscriber.getOnNextEvents().get(0).get(0);
        assertThat(actualName).isEqualTo("testUserName");
    }
}
