package com.paranoid.paranoidtwitter.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.paranoid.paranoidtwitter.helpers.PreferenceHelper;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class State {

    private Queue<Tweet> homeTweets;
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
        homeTweets = new LinkedList<>();
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public List<Tweet> getHomeTweets() {
        return new ArrayList<>(homeTweets);
    }

    public void refreshHomeTweets(List<Tweet> newTweets) {
        int postCount = PreferenceHelper.getPostCount();
        Log.e("TAG", "count tweets = " + newTweets.size());
        for (Tweet tweet: newTweets) {
            ++postCount;
            if (postCount < newTweets.size()) {homeTweets.poll(); }
            homeTweets.add(tweet);
        }
    }

    public String getCurrentFragmentTag() {
        return currentFragmentTag;
    }

    public void setCurrentFragmentTag(String currentFragmentTag) {
        this.currentFragmentTag = currentFragmentTag;
    }
}
