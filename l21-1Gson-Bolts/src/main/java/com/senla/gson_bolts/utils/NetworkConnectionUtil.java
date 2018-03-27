package com.senla.gson_bolts.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.senla.gson_bolts.App;
import com.senla.gson_bolts.R;

import java.io.IOException;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnectionUtil {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String url = App.self.getString(R.string.format_url);
    public static final String BROADCAST_ACTION_GET_RESPONSE = "local:SendRequestTask.BROADCAST_ACTION_GET_RESPONSE";
    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    public static <REQ, RESP> Response sendRequest(String method, REQ reqModel, final Class<RESP> respClass) throws IOException {

        String jsonRequest = gson.toJson(reqModel);
        Log.e("TAG", jsonRequest);
        RequestBody body = RequestBody.create(JSON, jsonRequest);
        Request request = new Request.Builder()
                .url(String.format(url, method))
                .post(body)
                .build();

        return execute(request).onSuccess(new Continuation<Response, Response>() {
            @Override
            public Response then(Task<Response> task) throws Exception {
                Log.e("TAG", "onSuccess");//сперва возвращает null, потом уже onSuccess. может так и должно быть?)
                return task.getResult();
            }
        }).getResult();
    }

    private static Task<Response> execute(final Request request) {
        return Task.callInBackground(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                Response response = client.newCall(request).execute();
                Log.e("TAG", response.body().string());//сервер отдает токен, все ок. но null response уже был получен
                return client.newCall(request).execute();
            }
        });
    }
}



