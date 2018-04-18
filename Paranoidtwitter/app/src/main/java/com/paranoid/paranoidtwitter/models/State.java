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
        homeTweets = new LinkedList<>();
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
