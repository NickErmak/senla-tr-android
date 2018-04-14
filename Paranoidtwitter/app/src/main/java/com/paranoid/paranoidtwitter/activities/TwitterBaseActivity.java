package com.paranoid.paranoidtwitter.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.fragments.AbstractFragment;
import com.paranoid.paranoidtwitter.fragments.AuthorizationFragment;
import com.paranoid.paranoidtwitter.fragments.PostFragment;
import com.paranoid.paranoidtwitter.fragments.PrefFragment;
import com.paranoid.paranoidtwitter.helpers.NavigationHelper;
import com.paranoid.paranoidtwitter.utils.BroadcastUtils;

public class TwitterBaseActivity extends AppCompatActivity implements AbstractFragment.FragmentLifeCircle {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            BroadcastUtils.ACTION action = (BroadcastUtils.ACTION) intent.getSerializableExtra(BroadcastUtils.EXTRA_ACTION);
            switch (action) {
                case SUCCESS_EMAIL:
                    NavigationHelper.refreshHeader(mNavigationView);
                    break;
            }
        }
    };

    public void showFragment(
            Fragment frag,
            String tag,
            boolean clearBackStack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (clearBackStack) {
            fragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
            );
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_frame, frag, tag);
        if (!tag.equals(App.getInstance().getState().getCurrentFragmentTag())) {
            transaction.addToBackStack(tag);
            App.getInstance().getState().setCurrentFragmentTag(tag);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void setupDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
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
                            NavigationHelper.logOut();
                            NavigationHelper.refreshHeader(mNavigationView);
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
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
        setupDrawerLayout();
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
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause Activity ");
        LocalBroadcastManager.getInstance(App.getInstance()).unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume Activity" + ". Thread = " + Thread.currentThread().getName());
        LocalBroadcastManager.getInstance(App.getInstance()).registerReceiver(
                receiver,
                new IntentFilter(BroadcastUtils.BROADCAST_ACTION)
        );
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onFragmentStart(String title) {
        if (title != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
