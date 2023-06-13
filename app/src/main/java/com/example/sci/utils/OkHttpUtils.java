package com.example.sci.utils;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpUtils {
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static void get(String url,Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        CLIENT.newCall(request).enqueue(callback);
    }

    public static void post(String url, @NonNull Map<String, String> params, Callback callback) {
        // JSON格式
        JsonObject jsonObject = new JsonObject();
        for (String key : params.keySet()) {
            jsonObject.addProperty(key, params.get(key));
        }
        String json = jsonObject.toString();
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        CLIENT.newCall(request).enqueue(callback);
    }
}
