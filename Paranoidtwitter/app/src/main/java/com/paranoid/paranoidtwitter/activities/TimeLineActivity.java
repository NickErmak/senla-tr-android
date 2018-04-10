package com.paranoid.paranoidtwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.adapters.TwitterRecyclerAdapter;

import com.twitter.sdk.android.tweetui.UserTimeline;

public class TimeLineActivity extends TwitterBaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post);

        //send tweet
        /*final Intent intent = new ComposerActivity.Builder(this)
                .session(App.session)
                .text("can I send tweet right now?")
                .hashtags("#twitter")
                .createIntent();
        startActivity(intent);*/

        UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("atory29")
                .build();


    }





}
