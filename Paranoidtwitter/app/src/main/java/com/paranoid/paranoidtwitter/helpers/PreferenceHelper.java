package com.paranoid.paranoidtwitter.helpers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;

public class PreferenceHelper {

    public static int getPostCount() {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
        String countKey = App.getInstance().getString(R.string.pref_post_count_key);
        return Integer.valueOf(sPref.getString(countKey, "0"));
    }
}
