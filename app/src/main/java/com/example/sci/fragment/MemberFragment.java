package com.example.sci.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.sci.R;
import com.example.sci.activity.BandDetailActivity;
import com.example.sci.activity.MemberDetailActivity;
import com.example.sci.api.MemberListResponse;
import com.example.sci.model.CommentModel;
import com.example.sci.model.MemberModel;
import com.example.sci.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemberFragment extends BasicFragment implements AdapterView.OnItemClickListener{

    public MemberFragment() {
        // Required empty public constructor
    }

    public static MemberFragment newInstance(String param1, String param2) {
        MemberFragment fragment = new MemberFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_member, container, false);
        ListView memberView = view.findViewById(R.id.memberlist);
        getMemberList(memberView);
        memberView.setOnItemClickListener(this);

        memberView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
                String memberName = ((TextView)view).getText().toString();
                intent.putExtra("memberName",memberName);
                startActivity(intent);
            }
        });
        return view;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
        startActivity(intent);
    }
    public void getMemberList(ListView listView){
        Handler handler = new Handler(Looper.getMainLooper());
        MemberModel memberModel = new MemberModel();
        memberModel.getAllMember(new MemberModel.OnListener() {
            @Override
            public void onSuccess(String response) {
                MemberListResponse memberListResponse = JsonUtils.fromJson(response,MemberListResponse.class);
                if (memberListResponse.getStatus() == 0){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            List<String> memberList = Arrays.asList(memberListResponse.getData());
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,memberList);
                            listView.setAdapter(adapter);
                        }
                    });
                }else{
                        showToastSync(MemberListResponse.getMessage());
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                    showToastSync(errorMessage);
            }
        });
    }
}