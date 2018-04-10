package com.paranoid.paranoidtwitter.fragments;

import android.support.v4.app.Fragment;

public abstract class AbstractFragment extends Fragment {

    public interface FragmentLifeCircle {
        void onFragmentStart (String title);
    }

    public abstract String getFragmentTag();
}
