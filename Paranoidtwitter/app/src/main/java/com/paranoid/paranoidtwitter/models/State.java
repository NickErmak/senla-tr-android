package com.paranoid.paranoidtwitter.models;

import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.ArrayList;
import java.util.List;

public class State {

    private List<Tweet> homeTweets;
    private String email;
    private String currentFragmentTag;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private boolean isAuth;

    public State() {
        homeTweets = new ArrayList<>();
    }


    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public List<Tweet> getHomeTweets() {
        return homeTweets;
    }

    public void setHomeTweets(List<Tweet> homeTweets) {
        this.homeTweets = homeTweets;
    }

    public String getCurrentFragmentTag() {
        return currentFragmentTag;
    }

    public void setCurrentFragmentTag(String currentFragmentTag) {
        this.currentFragmentTag = currentFragmentTag;
    }
}
