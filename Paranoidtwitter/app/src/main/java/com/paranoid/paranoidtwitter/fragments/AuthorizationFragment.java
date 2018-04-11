package com.paranoid.paranoidtwitter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.activities.TwitterBaseActivity;
import com.paranoid.paranoidtwitter.utils.NetworkUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class AuthorizationFragment extends AbstractFragment {

    public static final String FRAGMENT_TITLE = App.getInstance().getString(R.string.frag_authorization_title);
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG_AUTHORIZATION";
    public static final String BROADCAST_ACTION = "local:AuthorizationFragment.BROADCAST_ACTION";

    private FragmentLifeCircle mFragmentLifeCircle;
    private TwitterLoginButton mLoginBtn;

    public static AuthorizationFragment newInstance() {
        return new AuthorizationFragment();
    }

    public AuthorizationFragment() {
    }

    private void requestEmailAddress(final Context context, TwitterSession session) {
        new TwitterAuthClient().requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                App.getInstance().getState().setEmail(result.data);
                LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcast(
                        new Intent(BROADCAST_ACTION)
                                .putExtra(TwitterBaseActivity.EXTRA_ACTION, TwitterBaseActivity.ACTION.SUCCESS_AUTH)
                );
            }

            @Override
            public void failure(TwitterException exception) {
                exception.printStackTrace();
                Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentLifeCircle = (FragmentLifeCircle) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoginBtn = view.findViewById(R.id.login_button);
        mLoginBtn.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.e("TAG", "success authorization");
                NetworkUtils.initializeSession(result.data);
                requestEmailAddress(getActivity(), result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("TAG", "fail authorization");
                Toast.makeText(getActivity(), exception.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFragmentLifeCircle != null) {
            mFragmentLifeCircle.onFragmentStart(FRAGMENT_TITLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginBtn.onActivityResult(requestCode, resultCode, data);
    }
}
