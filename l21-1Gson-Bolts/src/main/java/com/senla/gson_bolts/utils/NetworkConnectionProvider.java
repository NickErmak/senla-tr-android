package com.senla.gson_bolts.utils;

import java.util.concurrent.Callable;

import bolts.Task;

public class NetworkConnectionProvider {

    public static Task<Void> executeTask() {


        return Task.callInBackground(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return null;
            }
        });
    }


}
