package com.example.sci.model;

import static com.example.sci.utils.AppConfig.BASE_URL;

import com.example.sci.utils.OkHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BandModel {
    private String bandName;
    private String userName;

    public String getBandName() {
        return bandName;
    }


    public interface OnListener {
        void onSuccess(String response);

        void onFailure(String errorMessage);
    }

    public void getAllBand(BandModel.OnListener listener) {
        String url = BASE_URL + "/getAllBand";
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

    public void getBandInfo(String bandName,String userName,BandModel.OnListener listener) {
        String url = BASE_URL + "/getBandInfo";
        Map<String, String> params = new HashMap<>();
        params.put("bandName", bandName);
        params.put("username",userName);
        OkHttpUtils.post(url,params,new Callback() {
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


    public void followBand(String userName, String bandName, BandModel.OnListener listener) {
        String url = BASE_URL + "/followBand";
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("bandName", bandName);

        OkHttpUtils.post(url, params,new Callback() {
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

    public void unfollowBand(String userName, String bandName, BandModel.OnListener listener) {
        String url = BASE_URL + "/unfollowBand";
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("bandName", bandName);

        OkHttpUtils.post(url, params,new Callback() {
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

    public void getLoveBand(String userName,BandModel.OnListener listener) {
        String url = BASE_URL + "/getLoveBand";
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);

        OkHttpUtils.post(url, params,new Callback() {
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
