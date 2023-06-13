package com.example.sci.fragment;

import android.app.LauncherActivity;
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
import android.widget.Toast;

import com.example.sci.R;
import com.example.sci.activity.BandDetailActivity;
import com.example.sci.activity.MainPageActivity;
import com.example.sci.api.BandListResponse;
import com.example.sci.api.LoginResponse;
import com.example.sci.model.BandModel;
import com.example.sci.model.CommentModel;
import com.example.sci.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BandFragment extends BasicFragment implements AdapterView.OnItemClickListener {

    public BandFragment() {

    }
    public static BandFragment newInstance(String param1, String param2) {
        BandFragment fragment = new BandFragment();
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
        View view=inflater.inflate(R.layout.fragment_band, container, false);
        ListView bandView = view.findViewById(R.id.bandlist);
        getBandList(bandView);
        bandView.setOnItemClickListener(this);

        bandView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public void getBandList(ListView listView){
        Handler handler = new Handler(Looper.getMainLooper());
        BandModel bandModel = new BandModel();
        bandModel.getAllBand(new BandModel.OnListener() {
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
                        showToastSync(BandListResponse.getMessage());
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                showToastSync(errorMessage);
            }
        });
    }
}