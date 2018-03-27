package com.senla.gson_bolts.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.senla.gson_bolts.R;
import com.senla.gson_bolts.fragments.ConnectionFragment;

public class MainActivity extends AppCompatActivity {

    private ConnectionFragment mFrag;
    private TextView mTvError;
    private EditText mEtLogin, mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtLogin = findViewById(R.id.main_et_login);
        mEtPassword = findViewById(R.id.main_et_password);
        mTvError = findViewById(R.id.main_tv_error);

        FragmentManager fm = getSupportFragmentManager();
        mFrag = (ConnectionFragment) fm.findFragmentByTag(ConnectionFragment.TAG);
        if (mFrag == null) {
            mFrag = new ConnectionFragment();
            fm.beginTransaction()
                    .add(mFrag, ConnectionFragment.TAG)
                    .commit();
        }


    }

    public void onClickLogin(View view) {
        mFrag.login("","");

        /*
        String login = mEtLogin.getText().toString();
        String password =   mEtPassword.getText().toString();

        boolean hasError = false;

        if (login.isEmpty()) {
            mEtLogin.setError(getString(R.string.error_empty));
        } else if (!checkEmail(login)) {
            mEtLogin.setError(getString(R.string.error_incorrect_email));
            hasError = true;
        }


        if (password.isEmpty()) {
            mEtPassword.setError(getString(R.string.error_empty));
            hasError = true;
        }

        if (!hasError) {
            state.setLogin(login);
            state.setPassword(password);
            mFrag.login(login, password);
        }*/
    }
}
