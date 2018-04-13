package com.paranoid.paranoidtwitter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.utils.NetworkUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class AuthorizationFragment extends AbstractFragment {

    public static final String FRAGMENT_TITLE = App.getInstance().getString(R.string.frag_authorization_title);
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG_AUTHORIZATION";

    public interface IAuthSuccessEvent {
        void forwardHome(TwitterSession session);
    }

    private FragmentLifeCircle mFragmentLifeCircle;
    private TwitterLoginButton mLoginBtn;

    public static AuthorizationFragment newInstance() {
        return new AuthorizationFragment();
    }

    public AuthorizationFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentLifeCircle = (FragmentLifeCircle) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginBtn = view.findViewById(R.id.login_button);
        mLoginBtn.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.e("TAG", "success authorization");
                NetworkUtils.initializeSession(result.data);
                if (getActivity() != null) {
                    ((IAuthSuccessEvent) getActivity()).forwardHome(result.data);
                }
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
