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
import com.example.sci.activity.ConcertDetailActivity;
import com.example.sci.api.PerformListResponse;
import com.example.sci.model.CommentModel;
import com.example.sci.model.PerformModel;
import com.example.sci.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcertFragment extends BasicFragment implements AdapterView.OnItemClickListener{

    public ConcertFragment() {
        // Required empty public constructor
    }
    public static ConcertFragment newInstance(String param1, String param2) {
        ConcertFragment fragment = new ConcertFragment();
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
        View view=inflater.inflate(R.layout.fragment_concert, container, false);
        ListView concertView = (ListView) view.findViewById(R.id.concertlist);
        getConcertList(concertView);
        concertView.setOnItemClickListener(this);

        concertView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConcertDetailActivity.class);
                String performName = ((TextView)view).getText().toString();
                intent.putExtra("performName",performName);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ConcertDetailActivity.class);
        startActivity(intent);
    }
    public void getConcertList(ListView listView){
        Handler handler = new Handler(Looper.getMainLooper());
        PerformModel performModel = new PerformModel();
        performModel.getAllPerform(new PerformModel.OnListener() {
            @Override
            public void onSuccess(String response) {
                PerformListResponse performListResponse = JsonUtils.fromJson(response,PerformListResponse.class);
                if (performListResponse.getStatus() == 0){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            List<String> performList = Arrays.asList(performListResponse.getData());
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,performList);
                            listView.setAdapter(adapter);
                        }
                    });
                }else{
                    showToastSync(PerformListResponse.getMessage());
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                    showToastSync(errorMessage);
            }
        });
    }
}