package com.paranoid.paranoidtwitter.helpers;

import android.support.design.widget.NavigationView;
import android.util.Log;
import android.widget.TextView;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.models.State;
import com.paranoid.paranoidtwitter.providers.ApiTwitterProvider;
import com.twitter.sdk.android.core.TwitterCore;

public class NavigationHelper {

    private static State state = App.getInstance().getState();

    public static void logOut() {
        Log.e("TAG", "clear session");
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
        state.setEmail(null);
        state.setAuth(false);
    }

    public static void refreshHeader(NavigationView navView) {
        TextView navHeader = navView.getHeaderView(0).findViewById(R.id.nav_header_email);
        String email = state.getEmail();
        if (email == null) {
            email = App.getInstance().getString(R.string.nav_header_default_title);
        }
        navHeader.setText(email);
    }
}
