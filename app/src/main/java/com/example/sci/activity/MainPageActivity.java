package com.example.sci.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.sci.fragment.BandFragment;
import com.example.sci.fragment.ConcertFragment;
import com.example.sci.fragment.MemberFragment;
import com.example.sci.fragment.MineFragment;
import com.example.sci.R;
import com.google.android.material.tabs.TabLayout;

public class MainPageActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();
        Toolbar toolbar=findViewById(R.id.toolbar);
        TabLayout tabLayout=findViewById(R.id.tablayout);
        swithFragment(new BandFragment());
        toolbar.setTitle("乐队");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        toolbar.setTitle(tab.getText());
                        swithFragment(new BandFragment());

                        break;

                    case 1:
                        toolbar.setTitle(tab.getText());
                        swithFragment(new MemberFragment());

                        break;

                    case 2:
                        toolbar.setTitle(tab.getText());
                        swithFragment(new ConcertFragment());
                        break;

                    case 3:
                        toolbar.setTitle(tab.getText());
                        swithFragment(new MineFragment());
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public static void swithFragment(Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
    }
}