package com.paranoid.paranoidtwitter.providers;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.ExtendedTwitterApiClient;
import com.paranoid.paranoidtwitter.services.TwitterService;
import com.paranoid.paranoidtwitter.utils.NetworkUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;

import java.util.List;

import retrofit2.Call;

public class ApiTwitterProvider {

    public static final String BROADCAST_ACTION = "local:ApiTwitterProvider.BROADCAST_ACTION";
    public static final String EXTRA_API_ACTION = "EXTRA_API_ACTION";

    public enum API_ACTION {
        GET_HOME_TIMELINE
    }

    public static void refreshHomeTimeLine() {
        TwitterService service = NetworkUtils.twitterApiClient.getTwitterService();
        Call<List<Tweet>> call = service.myHomeTimeline(
                20,
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
                App.getInstance().getState().setHomeTweets(result.data);
                LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcast(
                        new Intent(BROADCAST_ACTION)
                                .putExtra(EXTRA_API_ACTION, API_ACTION.GET_HOME_TIMELINE)
                );
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("TAG", "exception get hom status = " + exception.getMessage());
            }
        });
    }
}
