package com.paranoid.paranoidtwitter.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;

public class PrefFragment extends PreferenceFragmentCompat {

    public static final String FRAGMENT_TITLE = App.getInstance().getString(R.string.frag_settings_title);
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG_SETTINGS";
    private AbstractFragment.FragmentLifeCircle mFragmentLifeCircle;

    public PrefFragment() {
    }

    public static PreferenceFragmentCompat newInstance() {
        return new PrefFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentLifeCircle = (AbstractFragment.FragmentLifeCircle) context;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFragmentLifeCircle != null) {
            mFragmentLifeCircle.onFragmentStart(FRAGMENT_TITLE);
        }
    }
}
