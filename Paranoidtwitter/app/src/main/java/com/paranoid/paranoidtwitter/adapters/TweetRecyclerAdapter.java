package com.paranoid.paranoidtwitter.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.activities.TwitterBaseActivity;
import com.paranoid.paranoidtwitter.fragments.SaveImageFragment;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetMediaClickListener;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.List;

public class TweetRecyclerAdapter extends RecyclerView.Adapter<TweetRecyclerAdapter.ViewHolder> {

    public interface IOpenImageEvent {
        void openImage(String imageUrl);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout tweetContainer;

        ViewHolder(View itemView) {
            super(itemView);
            tweetContainer = (FrameLayout) itemView;
        }
    }

    private List<Tweet> mTweets;
    private IOpenImageEvent mOpenImageEvent;

    public TweetRecyclerAdapter(Activity activity, List<Tweet> tweets) {
        mOpenImageEvent = (IOpenImageEvent) activity;
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
        View tweetLayout = inflater.inflate(R.layout.list_tweet, parent, false);
        return new TweetRecyclerAdapter.ViewHolder(tweetLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        TweetView tweetView = new TweetView(App.getInstance(), tweet);
        tweetView.setTweetMediaClickListener(new TweetMediaClickListener() {
            @Override
            public void onMediaEntityClick(Tweet tweet, MediaEntity entity) {
                mOpenImageEvent.openImage(entity.mediaUrl);
            }
        });
        holder.tweetContainer.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.tweetContainer.addView(tweetView);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
