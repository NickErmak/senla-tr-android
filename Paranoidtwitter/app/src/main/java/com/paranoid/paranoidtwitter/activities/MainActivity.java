package com.paranoid.paranoidtwitter.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.fragments.AbstractFragment;
import com.paranoid.paranoidtwitter.fragments.AuthorizationFragment;
import com.paranoid.paranoidtwitter.fragments.PostFragment;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;

public class MainActivity extends TwitterBaseActivity {

    public static final String EXTRA_ACTION = "EXTRA_ACTION";

    public enum ACTION {POSTS}

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ACTION action = (ACTION) intent.getSerializableExtra(EXTRA_ACTION);
            switch (action) {
                case POSTS:
                    showFragment(PostFragment.newInstance(), false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (App.getInstance().getState().isAuth()) {
            Log.e("TAG", "start auth frag");
            showFragment(AuthorizationFragment.newInstance(), false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(AuthorizationFragment.FRAGMENT_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showFragment(
            AbstractFragment frag,
            boolean clearBackStack) {
        String tag = frag.getFragmentTag();
        Log.e("TAG", "fragment tag = " + tag);
        FragmentManager fragmentManager = getSupportFragmentManager();

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
}
















       /* final LinearLayout myLayout
                = (LinearLayout) findViewById(R.id.my_tweet_layout);

        final long tweetId = 510908133917487104L;
        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                myLayout.addView(new TweetView(getApplicationContext(), result.data));
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("TAG", "fail show tweet");
            }
        });


       startActivity(new Intent(getApplicationContext(), TimeLineActivity.class));


    public final AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

        @Override
        protected Void doInBackground(Void... voids) {


            Response res = null;

            TwitterAuthConfig authConfig = new TwitterAuthConfig(
                    getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                    getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));


            TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

            TwitterAuthToken authToken = session.getAuthToken();
            String token = authToken.token;
            String secret = authToken.secret;

            Log.e("TAG", "token = " + token);
            Log.e("TAG", "secret = " + secret);

            OAuthSigning signing = new OAuthSigning(authConfig, authToken);


            String header = signing.getAuthorizationHeader("GET", "https://api.twitter.com/", null);
            // OAuth1aService oAuth1aService = new OAuth1aService().
            //   OAuthActivity activity = new OAuthActivity();


            // OAuth2Token tokens = new OAuth2Token(OAuth2Token.TOKEN_TYPE_BEARER, "4833285184-Pbx5BmlcMuJpeOLy9ceMC14qZlyLPfnyxIQ6Pkh");
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();


            final Service service;

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(gsonConverterFactory)
                    .baseUrl("https://api.twitter.com/")
                    .client(NetworkUtilsAcceptSelfSignedSslCert.trustAllHosts())

                    .build();
            service = retrofit.create(Service.class);
            return null;
        }
    };*/
//--------------------------------------------------------
/*
            try {
                Call<List<Tweet>> request = service.myHomeTimeline(header, 5, null, null, null, null, null, null);
                Log.e("TAG", "header = " + request.request().headers().toString());
                Log.e("TAG", "request = " + request.request().toString());
                Response<List<Tweet>> response = request.execute();
                if (response.isSuccessful()) {
                    Log.e("TAG", "succuess");
                    //App.tweetList = response.body();
                } else {
                    Log.e("TAG", "error = " + response.errorBody().string());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            final LinearLayout myLayout
                    = (LinearLayout) findViewById(R.id.my_tweet_layout);

            Iterator<Tweet> it = App.tweetList.iterator();
            while (it.hasNext()) {
                Tweet tweet = it.next();

                TweetUtils.loadTweet(tweet.id, new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        myLayout.addView(new TweetView(getApplicationContext(), result.data));
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.e("TAG", "fail show tweet");
                    }
                });
            }
        }
    };*/



