package com.paranoid.paranoidtwitter;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.paranoid.paranoidtwitter.models.State;
import com.paranoid.paranoidtwitter.services.TwitterService;
import com.paranoid.paranoidtwitter.utils.ImageUtils;
import com.paranoid.paranoidtwitter.utils.NetworkUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;

public class App extends Application {

    public static App instance;
    private State state;
    private RefWatcher refWatcher;

    public static App getInstance() {
        return App.instance;
    }

    public State getState() {
        return state;
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);

        instance = this;
        state = new State();

        Twitter.initialize(this);
        /*check for existence active session.
        create auth api client for authenticated user or guest api client
         */
        NetworkUtils.initializeSession(TwitterCore.getInstance()
                                .getSessionManager().getActiveSession());

    }
}
