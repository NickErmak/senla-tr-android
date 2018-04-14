package com.paranoid.paranoidtwitter.utils;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.activities.TwitterBaseActivity;

public class BroadcastUtils {

    public static final String BROADCAST_ACTION = "local:BroadcastUtils.BROADCAST_ACTION";
    public static final String EXTRA_ACTION = "EXTRA_ACTION";
    public enum ACTION {SUCCESS_EMAIL, SUCCESS_POST_UPDATE, ERROR_POST_UPDATE}

    public static void sendBroadcast(ACTION action) {
        LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcast(
                new Intent(BROADCAST_ACTION)
                        .putExtra(BroadcastUtils.EXTRA_ACTION, action)
        );
    }
}
