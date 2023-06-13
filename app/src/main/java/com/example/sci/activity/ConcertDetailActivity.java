package com.example.sci.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.sci.R;
import com.example.sci.api.CommentResponse;
import com.example.sci.api.PerformResponse;
import com.example.sci.model.CommentModel;
import com.example.sci.model.PerformModel;
import com.example.sci.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

public class ConcertDetailActivity extends BasicActivity {
    int i=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert_detail);
        Button remark = (Button) this.findViewById(R.id.remark);
        Intent intent = getIntent();
        String performName = intent.getStringExtra("performName");
        getPerformDetail(performName);
        remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView commentWords =  findViewById(R.id.commentWords);
                String comment =  ((TextView)commentWords).getText().toString();
                CommentModel commentModel = new CommentModel();
                commentModel.commentPerform(performName, comment, new CommentModel.OnListener() {
                    @Override
                    public void onSuccess(String response) {
                        if(CommentResponse.getStatus()==0){
                            getPerformDetail(performName);
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
    public void getPerformDetail(String performName){
        Handler handler = new Handler(Looper.getMainLooper());
        PerformModel performModel = new PerformModel();
        TextView performBandTv = findViewById(R.id.performBand);
        TextView performNameTv = findViewById(R.id.performName);
        TextView performTimeTv = findViewById(R.id.performTime);
        TextView performSpotTv = findViewById(R.id.performSpot);
        performModel.getPerformInfo(performName, new PerformModel.OnListener() {
            @Override
            public void onSuccess(String response) {
                if (PerformResponse.getStatus() == 0){
                    PerformResponse performResponse = JsonUtils.fromJson(response,PerformResponse.class);
                    PerformResponse.Perform Data = performResponse.getData();
                    String perfromName = Data.getPerformName();
                    String performTime = Data.getPerformTime();
                    String performBand = Data.getPerformBand();
                    String performSpot = Data.getPerformSpot();
                    ListView commentLayout = findViewById(R.id.comment);
                    handler.post(()-> {
                        performNameTv.setText(perfromName);
                        performBandTv.setText(performBand);
                        performTimeTv.setText(performTime);
                        performSpotTv.setText(performSpot);
                        List<String> commentList = Arrays.asList(Data.getPerformComment());
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,commentList);
                        commentLayout.setAdapter(adapter);
                    });
                }else{
                    showToastSync(PerformResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                showToastSync(errorMessage);
            }
        });
    }
}