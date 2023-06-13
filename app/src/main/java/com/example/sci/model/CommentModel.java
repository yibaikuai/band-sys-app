package com.example.sci.model;

import static com.example.sci.utils.AppConfig.BASE_URL;

import com.example.sci.utils.OkHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommentModel {
    private String username;
    private String comment;

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public interface OnListener {
        void onSuccess(String response);

        void onFailure(String errorMessage);
    }

    public void commentBand(String bandName, String comment, OnListener listener) {
        String url = BASE_URL+"/commentBand";
        Map<String, String> params = new HashMap<>();
        params.put("bandName", bandName);
        params.put("comment", comment);

        OkHttpUtils.post(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    listener.onSuccess(responseData);
                } else {
                    listener.onFailure(response.message());
                }
            }
        });
    }
    public void commentMember(String memberName, String comment, OnListener listener) {
        String url = BASE_URL+"/commentMember";
        Map<String, String> params = new HashMap<>();
        params.put("memberName", memberName);
        params.put("comment", comment);

        OkHttpUtils.post(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    listener.onSuccess(responseData);
                } else {
                    listener.onFailure(response.message());
                }
            }
        });
    }
    public void commentPerform(String performName, String comment, OnListener listener) {
        String url = BASE_URL+"/commentPerform";
        Map<String, String> params = new HashMap<>();
        params.put("performName", performName);
        params.put("comment", comment);

        OkHttpUtils.post(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    listener.onSuccess(responseData);
                } else {
                    listener.onFailure(response.message());
                }
            }
        });
    }
}