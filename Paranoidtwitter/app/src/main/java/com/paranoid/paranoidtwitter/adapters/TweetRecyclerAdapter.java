package com.paranoid.paranoidtwitter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.List;

public class TweetRecyclerAdapter extends RecyclerView.Adapter<TweetRecyclerAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout tweetContainer;
        TweetView tweetView;

        ViewHolder(View itemView) {
            super(itemView);
            tweetContainer = (LinearLayout) itemView;
        }
    }

    private List<Tweet> mTweets;

    public TweetRecyclerAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    public void setItems(List<Tweet> tweets) {
        mTweets = tweets;
    }

    @NonNull
    @Override
    public TweetRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.list_tweet, parent, false);
        return new TweetRecyclerAdapter.ViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        holder.tweetView = new TweetView(App.getInstance(), tweet);

        holder.tweetContainer.addView(holder.tweetView);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
