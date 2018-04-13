package com.paranoid.paranoidtwitter.fragments;

import android.support.v4.app.Fragment;

import com.paranoid.paranoidtwitter.App;

public abstract class AbstractFragment extends Fragment {

    public interface FragmentLifeCircle {
        void onFragmentStart(String title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getInstance().getRefWatcher().watch(this);
    }
}
