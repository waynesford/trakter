package com.example.rapunzel.trakter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Mocking the response for our Api. For this demo, I've only implemented a success and it's static return value.
 * There is no flow logic to determine the endpoint and query values here, but obviously should be done for a fully fleshed out system.
 */
public class MockInterceptor implements Interceptor {
    // FAKE RESPONSES
    public static final String RESPONSE
            = "[{\"followed_at\":\"2016-03-11T23:58:57.000Z\",\"user\":{\"username\":\"testUserName\",\"private\":false,\"name\":null,\"vip\":false,\"vip_ep\":false}}]";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        if(BuildConfig.DEBUG) {
            response = new Response.Builder()
                    .code(200)
                    .message(RESPONSE)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), RESPONSE.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        }
        else {
            response = chain.proceed(chain.request());
        }

        return response;
    }
}