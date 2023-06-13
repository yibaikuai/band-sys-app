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
import androidx.core.widget.NestedScrollView;

import com.example.sci.R;
import com.example.sci.api.CommentResponse;
import com.example.sci.api.MemberResponse;
import com.example.sci.model.CommentModel;
import com.example.sci.model.MemberModel;
import com.example.sci.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

public class MemberDetailActivity extends BasicActivity {
    int i=0;
    private NestedScrollView nestedScrollView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        Button remark = (Button) this.findViewById(R.id.remark);

        Intent intent = getIntent();
        String memberName = intent.getStringExtra("memberName");
        getMemberDetail(memberName);
        remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView commentWords =  findViewById(R.id.commentWords);
                String comment =  ((TextView)commentWords).getText().toString();
                CommentModel commentModel = new CommentModel();
                commentModel.commentMember(memberName, comment, new CommentModel.OnListener() {
                    @Override
                    public void onSuccess(String response) {
                        if(CommentResponse.getStatus()==0){
                            getMemberDetail(memberName);
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

    public void getMemberDetail(String memberName){
        Handler handler = new Handler(Looper.getMainLooper());
        MemberModel memberModel = new MemberModel();
        TextView memberNameTv = findViewById(R.id.memberName);
        TextView memberAgeTv = findViewById(R.id.memberAge);
        TextView memberSexTv = findViewById(R.id.memberSex);
        TextView memberAffiBandTv = findViewById(R.id.memberAffiBand);
        TextView memberJoinTimeTv = findViewById(R.id.memberJoinTime);
        memberModel.getMemberInfo(memberName, new MemberModel.OnListener() {
            @Override
            public void onSuccess(String response) {
                if (MemberResponse.getStatus() == 0){
                    MemberResponse memberResponse = JsonUtils.fromJson(response,MemberResponse.class);
                    MemberResponse.Member Data = memberResponse.getData();
                    String memberName = Data.getMemberName();
                    String memberAge  = Data.getMemberAge();
                    String memberSex  = Data.getMemberSex();
                    String memberAffiBand = Data.getMemberAffiBand();
                    String memberJoinTime = Data.getMemberJoinTime();
                    ListView commentLayout = findViewById(R.id.comment);
                    handler.post(()-> {
                        memberNameTv.setText(memberName);
                        memberAgeTv.setText(memberAge);
                        memberSexTv.setText(memberSex);
                        memberAffiBandTv.setText(memberAffiBand);
                        memberJoinTimeTv.setText(memberJoinTime);
                        List<String> commentList = Arrays.asList(Data.getMemberComment());
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,commentList);
                        commentLayout.setAdapter(adapter);
                    });
                }else{
                    showToastSync(MemberResponse.getMessage());
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                    showToastSync(errorMessage);
            }
        });
    }
}