package com.senla.gson_bolts;

import android.app.Application;

import com.senla.gson_bolts.models.State;

public class App extends Application {

    public static App self;
    public static State state = new State();

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
    }
}
