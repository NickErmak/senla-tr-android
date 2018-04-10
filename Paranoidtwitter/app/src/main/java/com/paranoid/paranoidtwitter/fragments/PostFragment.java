package com.paranoid.paranoidtwitter.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.activities.TimeLineActivity;
import com.paranoid.paranoidtwitter.adapters.TweetRecyclerAdapter;
import com.paranoid.paranoidtwitter.adapters.TwitterRecyclerAdapter;
import com.paranoid.paranoidtwitter.providers.ApiTwitterProvider;
import com.paranoid.paranoidtwitter.services.TwitterService;
import com.paranoid.paranoidtwitter.utils.NetworkUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class PostFragment extends AbstractFragment {

    public static final String FRAGMENT_TITLE = App.getInstance().getString(R.string.frag_post_title);
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG_POST";

    private FragmentLifeCircle mFragmentLifeCircle;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ApiTwitterProvider.API_ACTION action = (ApiTwitterProvider.API_ACTION) intent.getSerializableExtra(ApiTwitterProvider.EXTRA_API_ACTION);
            switch (action) {
                case GET_HOME_TIMELINE:
                    mAdapter.setItems(App.getInstance().getState().getHomeTweets());
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private TweetRecyclerAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public PostFragment() {}

    public static PostFragment newInstance() {
        return new PostFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentLifeCircle = (FragmentLifeCircle) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.post_swipe_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPosts();
            }
        });

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tweets_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mAdapter = new TweetRecyclerAdapter(App.getInstance().getState().getHomeTweets());
        recyclerView.setAdapter(mAdapter);
        refreshPosts();
    }

    private void refreshPosts() {
        mSwipeRefreshLayout.setRefreshing(true);
        ApiTwitterProvider.refreshHomeTimeLine();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFragmentLifeCircle != null) {
            mFragmentLifeCircle.onFragmentStart(FRAGMENT_TITLE);
        }
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(App.getInstance()).unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(App.getInstance()).registerReceiver(
                receiver,
                new IntentFilter(ApiTwitterProvider.BROADCAST_ACTION)
        );
    }
}
