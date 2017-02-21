package com.example.ssf.paperplane.homepage;

import android.webkit.WebView;

import com.example.ssf.paperplane.BasePresenter;
import com.example.ssf.paperplane.BaseView;
import com.example.ssf.paperplane.bean.ZhihuDailyNews;

import java.util.ArrayList;

/**
 * 实现交互的接口，返回一系列需要用到的方法
 * Created by Administrator on 2017/2/21.
 */
public interface ZhihuDailyContract {

    interface View extends BaseView<Presenter>{

        void showError();

        void showLoading();

        void stopLoading();

        void showResults(ArrayList<ZhihuDailyNews.Question> list);
    }
    interface Presenter extends BasePresenter{
        void loadPosts(long date, boolean clearing);

        void refresh();

        void loadMore(long date);

        void startReading(int position);

        void feelLucky(); // 随机选择的消息
    }


}
