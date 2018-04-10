package com.paranoid.paranoidtwitter.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.adapters.DrawerRecyclerAdapter;
import com.paranoid.paranoidtwitter.fragments.AbstractFragment;
import com.paranoid.paranoidtwitter.models.DrawerMenuItem;

import java.util.ArrayList;

//Base application activity with toolbar and drawer layout
public class TwitterBaseActivity extends AppCompatActivity implements AbstractFragment.FragmentLifeCircle{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        setupToolbar();
        setupDrawerLayout();
        RecyclerView rvDrawerMenu = (RecyclerView) findViewById(R.id.drawer_recycler);

        ArrayList<DrawerMenuItem> menu = new ArrayList<>();
        menu.add(new DrawerMenuItem(R.drawable.tw__composer_close, R.string.drawer_setting_text));

        rvDrawerMenu.setAdapter(new DrawerRecyclerAdapter(menu));
        rvDrawerMenu.setLayoutManager(new LinearLayoutManager(this));
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
    public void onFragmentStart(String title) {
        getSupportActionBar().setTitle(title);
    }
}
