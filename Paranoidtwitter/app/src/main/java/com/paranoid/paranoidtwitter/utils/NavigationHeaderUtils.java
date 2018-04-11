package com.paranoid.paranoidtwitter.utils;

import android.support.design.widget.NavigationView;
import android.widget.TextView;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;

import org.w3c.dom.Text;

public class NavigationHeaderUtils {

    public static void refreshHeader (NavigationView navView) {
        TextView navHeader = (TextView) navView.getHeaderView(0).findViewById(R.id.nav_header_email);
        String email = App.getInstance().getState().getEmail();
        if (email == null) {
            email = "no email";
        }
        navHeader.setText(email);
    }
}
