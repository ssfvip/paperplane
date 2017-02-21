package com.example.ssf.paperplane.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ssf.paperplane.R;

/**
 * Created by Administrator on 2017/2/21.
 */
public class DouBanMomentFragment extends Fragment {

    public DouBanMomentFragment(){

    }
    public static DouBanMomentFragment newInstance(){
        return new DouBanMomentFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }
}
