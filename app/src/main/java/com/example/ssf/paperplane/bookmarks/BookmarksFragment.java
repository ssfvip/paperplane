package com.example.ssf.paperplane.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ssf.paperplane.R;

/**
 * 收藏页面，利用MVP模式，此页面相当于V
 * Created by Administrator on 2017/2/21.
 */
public class BookmarksFragment extends Fragment {

    public BookmarksFragment (){}
    public static BookmarksFragment newInstance(){
        return new BookmarksFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }
}
