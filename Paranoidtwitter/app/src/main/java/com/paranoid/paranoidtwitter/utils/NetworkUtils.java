package com.paranoid.paranoidtwitter.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.ExtendedTwitterApiClient;
import com.paranoid.paranoidtwitter.activities.TwitterBaseActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class NetworkUtils {

    public static final OkHttpClient okHttpClient;
    public static ExtendedTwitterApiClient twitterApiClient;
    public static TwitterSession session;

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
            NetworkUtils.session = session;
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
