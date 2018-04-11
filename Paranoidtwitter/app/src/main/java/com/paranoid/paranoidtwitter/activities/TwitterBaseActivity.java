package com.paranoid.paranoidtwitter.activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.fragments.AbstractFragment;
import com.paranoid.paranoidtwitter.fragments.AuthorizationFragment;
import com.paranoid.paranoidtwitter.fragments.PostFragment;
import com.paranoid.paranoidtwitter.fragments.PrefFragment;
import com.paranoid.paranoidtwitter.utils.NavigationHeaderUtils;
import com.twitter.sdk.android.core.TwitterCore;

//Base application activity with toolbar and drawer layout
public class TwitterBaseActivity extends AppCompatActivity implements AbstractFragment.FragmentLifeCircle {
    public static final String EXTRA_ACTION = "EXTRA_ACTION";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;

    public enum ACTION {SUCCESS_AUTH}

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ACTION action = (ACTION) intent.getSerializableExtra(EXTRA_ACTION);
            switch (action) {
                case SUCCESS_AUTH:
                    NavigationHeaderUtils.refreshHeader(mNavigationView);
                    showFragment(PostFragment.newInstance(), PostFragment.FRAGMENT_TAG, true);
                    break;
            }
        }
    };

    private void showFragment(
            android.app.Fragment frag,
            String tag,
            boolean clearBackStack) {
        FragmentManager fragmentManager = getFragmentManager();

        if (clearBackStack) {
            fragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
            );
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_frame, frag, tag);
        transaction.addToBackStack(tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupDrawerLayout();
        showFragment(PrefFragment.newInstance(), PrefFragment.FRAGMENT_TAG, false);

        if (App.getInstance().getState().isAuth()) {
            NavigationHeaderUtils.refreshHeader(mNavigationView);
            showFragment(PostFragment.newInstance(), AuthorizationFragment.FRAGMENT_TAG, false);
        } else {
            showFragment(AuthorizationFragment.newInstance(), AuthorizationFragment.FRAGMENT_TAG, false);
        }
    }

    private Toolbar setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        return toolbar;
    }

    private void setupDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                setupToolbar(),
                R.string.drawer_open,
                R.string.drawer_close
        );
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    switch (menuItem.getItemId()) {
                        case R.id.nav_settings:
                            showFragment(PrefFragment.newInstance(), PrefFragment.FRAGMENT_TAG, false);
                            break;
                        case R.id.nav_log_out:
                            Log.e("TAG", "clear session");
                            TwitterCore.getInstance().getSessionManager().clearActiveSession();
                        case R.id.nav_home:
                            if (App.getInstance().getState().isAuth()) {
                                showFragment(PostFragment.newInstance(), PostFragment.FRAGMENT_TAG, true);
                            } else {
                                showFragment(AuthorizationFragment.newInstance(), AuthorizationFragment.FRAGMENT_TAG, true);
                            }
                            break;
                        case R.id.nav_exit:
                            finish();
                            break;
                    }
                    mDrawerLayout.closeDrawers();
                    return true;
                });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getFragmentManager().findFragmentByTag(AuthorizationFragment.FRAGMENT_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(App.getInstance()).unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(App.getInstance()).registerReceiver(
                receiver,
                new IntentFilter(AuthorizationFragment.BROADCAST_ACTION)
        );
    }

    @Override
    public void onFragmentStart(String title) {
        getSupportActionBar().setTitle(title);
    }
}
