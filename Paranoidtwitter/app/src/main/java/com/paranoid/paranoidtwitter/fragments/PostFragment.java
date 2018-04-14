package com.paranoid.paranoidtwitter.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.adapters.TweetRecyclerAdapter;
import com.paranoid.paranoidtwitter.models.State;
import com.paranoid.paranoidtwitter.providers.ApiTwitterProvider;
import com.paranoid.paranoidtwitter.utils.BroadcastUtils;

import static com.paranoid.paranoidtwitter.utils.BroadcastUtils.*;

public class PostFragment extends AbstractFragment {

    public static final String FRAGMENT_TITLE = App.getInstance().getString(R.string.frag_post_title);
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG_POST";
    private FragmentLifeCircle mFragmentLifeCircle;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ACTION action = (ACTION) intent.getSerializableExtra(EXTRA_ACTION);
            switch (action) {
                case SUCCESS_POST_UPDATE:
                    mAdapter.setItems(App.getInstance().getState().getHomeTweets());
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case ERROR_POST_UPDATE:
                    Toast.makeText(context, R.string.toast_msg_connection_error, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private TweetRecyclerAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private State state = App.getInstance().getState();

    public PostFragment() {
    }

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = view.findViewById(R.id.post_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPosts();
            }
        });

        final RecyclerView recyclerView = view.findViewById(R.id.tweets_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mAdapter = new TweetRecyclerAdapter(getActivity(), App.getInstance().getState().getHomeTweets());
        recyclerView.setAdapter(mAdapter);
        refreshPosts();
    }

    private void refreshPosts() {
        App.getInstance().getState();
        mSwipeRefreshLayout.setRefreshing(true);
        Long id = null;
        if (!state.getHomeTweets().isEmpty()) {
            id = state.getHomeTweets().get(0).getId();
        }
        ApiTwitterProvider.refreshHomeTimeLine(id);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFragmentLifeCircle != null) {
            mFragmentLifeCircle.onFragmentStart(FRAGMENT_TITLE);
        }
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
                new IntentFilter(BROADCAST_ACTION)
        );
    }
}
