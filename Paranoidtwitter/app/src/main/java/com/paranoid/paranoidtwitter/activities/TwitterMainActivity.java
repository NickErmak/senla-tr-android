package com.paranoid.paranoidtwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.adapters.TweetRecyclerAdapter;
import com.paranoid.paranoidtwitter.fragments.AuthorizationFragment;
import com.paranoid.paranoidtwitter.fragments.PostFragment;
import com.paranoid.paranoidtwitter.fragments.SaveImageFragment;
import com.paranoid.paranoidtwitter.providers.ApiTwitterProvider;
import com.paranoid.paranoidtwitter.utils.NetworkUtils;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterSession;

public class TwitterMainActivity extends TwitterBaseActivity
        implements AuthorizationFragment.IAuthSuccessEvent, TweetRecyclerAdapter.IOpenImageEvent {

    @Override
    public void forwardHome(TwitterSession session) {
        ApiTwitterProvider.requestEmailAddress(getApplicationContext());
        showFragment(PostFragment.newInstance(), PostFragment.FRAGMENT_TAG, true);
    }

    @Override
    public void openImage(String imageUrl) {
        showFragment(
                SaveImageFragment.newInstance(imageUrl),
                SaveImageFragment.FRAGMENT_TAG,
                false
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String currentFragmentTag = App.getInstance().getState().getCurrentFragmentTag();
        if (currentFragmentTag != null) {
            showFragment(getSupportFragmentManager().findFragmentByTag(currentFragmentTag), currentFragmentTag, false);
        } else if (App.getInstance().getState().isAuth()) {
            ApiTwitterProvider.requestEmailAddress(this);
            showFragment(PostFragment.newInstance(), AuthorizationFragment.FRAGMENT_TAG, false);
        } else {
            showFragment(AuthorizationFragment.newInstance(), AuthorizationFragment.FRAGMENT_TAG, false);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(App.getInstance().getState().getCurrentFragmentTag());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(App.getInstance().getState().getCurrentFragmentTag());
        if (fragment != null) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
