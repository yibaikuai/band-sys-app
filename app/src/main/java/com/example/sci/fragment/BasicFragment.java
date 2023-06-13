package com.example.sci.fragment;


import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BasicFragment extends Fragment {
    public void showToastSync(String message){
        if (getActivity()!= null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
