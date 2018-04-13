package com.paranoid.paranoidtwitter.utils;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.activities.TwitterBaseActivity;

public class BroadcastUtils {

    public static final String BROADCAST_ACTION = "local:BroadcastUtils.BROADCAST_ACTION";

    public static void sendBroadcast(TwitterBaseActivity.ACTION action) {
        LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcast(
                new Intent(BROADCAST_ACTION)
                        .putExtra(TwitterBaseActivity.EXTRA_ACTION, action)
        );
    }
}
