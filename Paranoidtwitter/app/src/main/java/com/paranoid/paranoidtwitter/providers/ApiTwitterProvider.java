package com.paranoid.paranoidtwitter.providers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.annotations.JsonAdapter;
import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.activities.TwitterBaseActivity;
import com.paranoid.paranoidtwitter.helpers.PreferenceHelper;
import com.paranoid.paranoidtwitter.services.TwitterService;
import com.paranoid.paranoidtwitter.utils.BroadcastUtils;
import com.paranoid.paranoidtwitter.utils.NetworkUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import retrofit2.Call;

public class ApiTwitterProvider {

    public static void requestEmailAddress(final Context context) {
        new TwitterAuthClient().requestEmail(
                NetworkUtils.session,
                new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        App.getInstance().getState().setEmail(result.data);
                        BroadcastUtils.sendBroadcast(BroadcastUtils.ACTION.SUCCESS_EMAIL);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        exception.printStackTrace();
                        Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public static void refreshHomeTimeLine(int count) {
        App app = App.getInstance();
        TwitterService service = NetworkUtils.twitterApiClient.getTwitterService();
        Call<List<Tweet>> call = service.myHomeTimeline(
                count,
                null,
                null,
                null,
                null,
                null,
                null
        );

        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                app.getState().setHomeTweets(result.data);
                BroadcastUtils.sendBroadcast(BroadcastUtils.ACTION.SUCCESS_POST_UPDATE);
            }

            @Override
            public void failure(TwitterException exception) {
                BroadcastUtils.sendBroadcast(BroadcastUtils.ACTION.ERROR_POST_UPDATE);
                Log.e("TAG", "exception get home status = " + exception.getMessage());
            }
        });
    }
}
