package com.example.sci.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sci.R;
import com.example.sci.activity.BandDetailActivity;
import com.example.sci.api.BandListResponse;
import com.example.sci.api.BandResponse;
import com.example.sci.model.BandModel;
import com.example.sci.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

public class MineFragment extends BasicFragment implements AdapterView.OnItemClickListener{
    public MineFragment() {

    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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

        View view=inflater.inflate(R.layout.fragment_mine, container, false);
        ListView loveBandList = view.findViewById(R.id.loveBandList);
        SharedPreferences preferences = getActivity().getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = preferences.getString("username","");
        TextView user = view.findViewById(R.id.name);
        user.setText("欢迎您，"+username);

        getLoveBandList(loveBandList,username);
        loveBandList.setOnItemClickListener(this);

        loveBandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BandDetailActivity.class);
                String bandName = ((TextView)view).getText().toString();
                intent.putExtra("bandName",bandName);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), BandDetailActivity.class);
        startActivity(intent);
    }
    public void getLoveBandList(ListView listView,String username){
        Handler handler = new Handler(Looper.getMainLooper());
        BandModel bandModel = new BandModel();
        bandModel.getLoveBand(username,new BandModel.OnListener() {
            @Override
            public void onSuccess(String response) {
                BandListResponse bandListResponse = JsonUtils.fromJson(response, BandListResponse.class);
                if (bandListResponse.getStatus() == 0){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            List<String> bandList = Arrays.asList(bandListResponse.getData());
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,bandList);
                            listView.setAdapter(adapter);
                        }
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
}