package com.paranoid.paranoidtwitter.fragments;

import android.app.Fragment;

public abstract class AbstractFragment extends Fragment {

    public interface FragmentLifeCircle {
        void onFragmentStart (String title);
    }

}
