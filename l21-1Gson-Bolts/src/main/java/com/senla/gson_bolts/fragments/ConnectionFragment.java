package com.senla.gson_bolts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.senla.gson_bolts.models.requests.RequestLogin;

import com.senla.gson_bolts.models.responses.ResponseLogin;
import com.senla.gson_bolts.utils.NetworkConnectionUtil;

import java.io.IOException;

import okhttp3.Response;

public class ConnectionFragment extends Fragment {

    public static final String TAG = "TAG_CONNECTION_FRAGMENT";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void login(String login, String password) {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail("john@domain.tld");
        requestLogin.setPassword("123123");

        try {
            Response r = NetworkConnectionUtil.sendRequest("login", requestLogin, ResponseLogin.class);//возвращает null
            Log.e("TAG", ""+ (r==null));
        } catch (IOException e) {
            e.printStackTrace();
        }

      //  Log.e("TAG", r.getStatus());
    }

}
