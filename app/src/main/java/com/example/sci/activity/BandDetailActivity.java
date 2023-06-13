package com.example.sci.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.sci.R;
import com.example.sci.api.BandResponse;
import com.example.sci.api.CommentResponse;
import com.example.sci.model.BandModel;
import com.example.sci.model.CommentModel;
import com.example.sci.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

public class BandDetailActivity extends BasicActivity {
    int i=0;
    private ScrollView mScro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_detail);
        Button like = (Button) this.findViewById(R.id.like);
        Button remark = (Button) this.findViewById(R.id.remark);
        mScro = (ScrollView)findViewById(R.id.mScro);

        SharedPreferences preferences = getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = preferences.getString("username","");
        Intent intent = getIntent();
        String bandName = intent.getStringExtra("bandName");
        getBandDetail(bandName,username);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((i%2)==0){
                    followBand(username,bandName);
                    like.setText("取关");
                    i++;
                }
                else{
                    unfollowBand(username,bandName);
                    like.setText("关注");
                    i++;
                }
            }
        });
        remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView commentWords =  findViewById(R.id.commentWords);
                String comment =  ((TextView)commentWords).getText().toString();
                CommentModel commentModel = new CommentModel();
                commentModel.commentBand(bandName, comment, new CommentModel.OnListener() {
                    @Override
                    public void onSuccess(String response) {
                        if(CommentResponse.getStatus()==0){
                            getBandDetail(bandName,username);
                            showToastSync("评论成功");
                        }else {
                            showToastSync(CommentResponse.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        showToastSync(errorMessage);
                    }
                });
            }
        });
    }
    public void getBandDetail(String bandName,String userName){
        Handler handler = new Handler(Looper.getMainLooper());
        BandModel bandModel = new BandModel();
        TextView bandNameTv = findViewById(R.id.bandName);
        TextView bandCreateTimeTv = findViewById(R.id.bandCreateTime);
        TextView bandNumTv = findViewById(R.id.bandNum);
        TextView albumTv = findViewById(R.id.album);
        TextView memberTv = findViewById(R.id.member);
        bandModel.getBandInfo(bandName,userName, new BandModel.OnListener() {
            @Override
            public void onSuccess(String response) {
                if (BandResponse.getStatus() == 0){
                        BandResponse bandResponse = JsonUtils.fromJson(response,BandResponse.class);
                        BandResponse.Band Data = bandResponse.getData();
                        String bandName = Data.getBandName();
                        String bandCreTime = Data.getBandCreTime();
                        String bandAlbum  = Data.getBandAlbum();
                        String bandMember = Data.getBandMember();
                        int isLove = Data.getIsLove();
                        Button Love =  findViewById(R.id.like);
                        ListView commentLayout = findViewById(R.id.comment);
                        int bandNum = Data.getNum();
                        handler.post(()-> {
                           bandNameTv.setText(bandName);
                           bandCreateTimeTv.setText(bandCreTime);
                           bandNumTv.setText(bandNum+"");
                           albumTv.setText(bandAlbum);
                           memberTv.setText(bandMember.toString());
                            String isLoved = isLove ==0 ? "关注" : "取关";
                            Love.setText(isLoved);
                            List<String> commentList = Arrays.asList(Data.getBandComment());
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,commentList);
                            commentLayout.setAdapter(adapter);
                        });
                }else{
                        showToastSync(BandResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                showToastSync(errorMessage);
            }
        });
    }
    public void followBand(String username,String bandName){
        BandModel bandModel = new BandModel();
        bandModel.followBand(username,bandName,new BandModel.OnListener() {
            @Override
            public void onSuccess(String response) {
                if(CommentResponse.getStatus()==0){
                    showToastSync("关注成功");
                }else {
                    showToastSync(CommentResponse.getMessage());
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                showToastSync(errorMessage);
            }
        });
    }
    public void unfollowBand(String username,String bandName){
        BandModel bandModel = new BandModel();
        bandModel.unfollowBand(username,bandName,new BandModel.OnListener() {
            @Override
            public void onSuccess(String response) {
                if(CommentResponse.getStatus()==0){
                    showToastSync("取关成功");
                }else {
                    showToastSync(CommentResponse.getMessage());
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                showToastSync(errorMessage);
            }
        });
    }
}