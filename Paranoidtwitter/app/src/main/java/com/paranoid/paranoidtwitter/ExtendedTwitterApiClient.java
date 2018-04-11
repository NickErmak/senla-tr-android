package com.paranoid.paranoidtwitter;

import com.paranoid.paranoidtwitter.services.TwitterService;
import com.paranoid.paranoidtwitter.utils.NetworkUtils;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import okhttp3.OkHttpClient;

public class ExtendedTwitterApiClient extends TwitterApiClient {

    public ExtendedTwitterApiClient(OkHttpClient client) {
        super(client);
    }

    public ExtendedTwitterApiClient(TwitterSession session, OkHttpClient client) {
        super(session, client);
    }

    public TwitterService getTwitterService() {
        return getService(TwitterService.class);
    }


}
