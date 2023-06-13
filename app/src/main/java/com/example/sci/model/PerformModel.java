package com.example.sci.model;

import static com.example.sci.utils.AppConfig.BASE_URL;

import com.example.sci.utils.OkHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PerformModel {
    private String performName;

    public String getPerformName() {return performName;}

    public interface OnListener {
        void onSuccess(String response);

        void onFailure(String errorMessage);
    }

    public void getAllPerform(PerformModel.OnListener listener) {
        String url = BASE_URL + "/getAllPerform";

        OkHttpUtils.get(url,new Callback() {
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

    public void getPerformInfo(String performName,PerformModel.OnListener listener) {
        String url = BASE_URL + "/getPerformInfo";
        Map<String, String> params = new HashMap<>();
        params.put("performName", performName);
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
