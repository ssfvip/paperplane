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
public class GuoKrFragment extends Fragment {

    public GuoKrFragment (){}
    public static GuoKrFragment newInstance(){
        return new GuoKrFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }
}
