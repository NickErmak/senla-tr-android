package com.paranoid.paranoidtwitter.utils;

import android.util.Log;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.ExtendedTwitterApiClient;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class NetworkUtils {

    public static final OkHttpClient okHttpClient;
    public static ExtendedTwitterApiClient twitterApiClient;

    //adding interceptor for okHttp client
    static {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();
    }

    public static boolean initializeSession(final TwitterSession session) {
        if (session != null) {
            Log.e("TAG", "active session");
            App.getInstance().getState().setAuth(true);
            twitterApiClient = new ExtendedTwitterApiClient(session, okHttpClient);
            TwitterCore.getInstance().addApiClient(session, twitterApiClient);
            return true;
        } else {
            Log.e("TAG", "guest session");
            App.getInstance().getState().setAuth(false);
            twitterApiClient = new ExtendedTwitterApiClient(okHttpClient);
            TwitterCore.getInstance().addGuestApiClient(twitterApiClient);
            return false;
        }
    }

}
