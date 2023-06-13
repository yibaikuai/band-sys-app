package com.example.sci.model;

import static com.example.sci.utils.AppConfig.BASE_URL;

import com.example.sci.utils.OkHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginModel {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public interface OnListener {
        void onSuccess(String response);
        void onFailure(String errorMessage);
    }

    public void login(String username, String password, OnListener listener) {
        String url = BASE_URL+"/login.do";
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);


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
