package com.example.ssf.paperplane.homepage;

import com.example.ssf.paperplane.BasePresenter;
import com.example.ssf.paperplane.BaseView;
import com.example.ssf.paperplane.bean.DoubanMomentNews;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/23.
 */
public interface DoubanMomentContract {
    interface View extends BaseView<Presenter> {

        void startLoading();

        void stopLoading();

        void showLoadingError();

        void showResults(ArrayList<DoubanMomentNews.posts> list);

    }
    interface Presenter extends BasePresenter {

        void startReading(int position);

        void loadPosts(long date, boolean clearing);

        void refresh();

        void loadMore(long date);

        void feelLucky();

    }
}
