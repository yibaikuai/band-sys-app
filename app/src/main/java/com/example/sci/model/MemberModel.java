package com.example.sci.model;

import static com.example.sci.utils.AppConfig.BASE_URL;

import com.example.sci.utils.OkHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MemberModel {
    private String memberName;

    public String getMemberName() {return memberName;}

    public interface OnListener {
        void onSuccess(String response);

        void onFailure(String errorMessage);
    }

    public void getAllMember(MemberModel.OnListener listener) {
        String url = BASE_URL + "/getAllMember";

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

    public void getMemberInfo(String memberName,MemberModel.OnListener listener) {
        String url = BASE_URL + "/getMemberInfo";
        Map<String, String> params = new HashMap<>();
        params.put("memberName", memberName);

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
