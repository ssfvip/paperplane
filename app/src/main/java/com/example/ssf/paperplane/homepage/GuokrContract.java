package com.example.ssf.paperplane.homepage;

import com.example.ssf.paperplane.BasePresenter;
import com.example.ssf.paperplane.BaseView;
import com.example.ssf.paperplane.bean.GuokrHandpickNews;

import java.util.ArrayList;

/**
 * 果壳的接口
 * Created by Administrator on 2017/2/23.
 */
public interface GuokrContract {

    // view 接口是给fragment/或者activity中使用的，方便实现view中的方法
    interface View extends BaseView<Presenter>{ //继承BaseView 传递presenter参数，方便在fragment中进行实例化
        void showError();

        void showResults(ArrayList<GuokrHandpickNews.result> list); //传入需要解析数据的数组

        void showLoading();

        void stopLoading();
    }
//presenter接口是给presenter用的，就是MVP中的P用来处理view中的方法的实现逻辑
    interface Presenter extends BasePresenter{//继承Presenter中的开始方法
        void loadPosts();

        void refresh();

        void startReading(int position);

        void feelLucky();
    }
}
