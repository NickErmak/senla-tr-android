package com.paranoid.paranoidtwitter.adapters;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetLinkClickListener;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

public class TwitterRecyclerAdapter extends TweetTimelineRecyclerViewAdapter {

    public TwitterRecyclerAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Tweet tweet = new TweetBuilder().build();
        final CompactTweetView compactTweetView = new CompactTweetView(context, tweet, styleResId);
        compactTweetView.setOnActionCallback(actionCallback);
        compactTweetView.setTweetLinkClickListener(new TweetLinkClickListener() {
            @Override
            public void onLinkClick(Tweet tweet, String url) {
                Log.e("TweetLinkClicked", "tweet = " + tweet.text + "url = " + url);
            }
        });
        return new TweetViewHolder(compactTweetView);
    }
}
