package com.example.ssf.paperplane.homepage;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.ssf.paperplane.bean.GuokrHandpickNews;
import com.example.ssf.paperplane.bean.StringModelImpl;
import com.example.ssf.paperplane.interfaces.OnStringListener;
import com.example.ssf.paperplane.util.Api;
import com.example.ssf.paperplane.util.NetworkState;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/23.
 */
public class GuokrPresenter implements GuokrContract.Presenter {

    private GuokrContract.View view;
    private Context context;
    private StringModelImpl model;

    private ArrayList<GuokrHandpickNews.result> list = new ArrayList<GuokrHandpickNews.result>();
    private Gson gson = new Gson();


    public GuokrPresenter(Context context, GuokrContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        model = new StringModelImpl(context);
    }

    /**
     * 解析接口数据
     */
    @Override
    public void loadPosts() {

        view.showLoading(); // 进度条显示
        if (NetworkState.networkConnected(context)) {
            model.load(Api.GUOKR_ARTICLES, new OnStringListener() {
                @Override
                public void onSuccess(String result) { //成功的回调接口
                    // 由于果壳并没有按照日期加载的api
                    // 所以不存在加载指定日期内容的操作，当要请求数据时一定是在进行刷新
                    list.clear();
                    try {
                        GuokrHandpickNews question = gson.fromJson(result, GuokrHandpickNews.class);
                        for (GuokrHandpickNews.result re :
                                question.getResult()) { // 遍历result中的所有数据
                            list.add(re);
                            // 添加数据库操作


                        }
                        view.showResults(list); // 将解析的数据集合传入到view中去
                    } catch (JsonSyntaxException e) {
                        view.showError();
                    }
                    view.stopLoading();// 关闭进度
                }


                @Override
                public void onError(VolleyError error) { // 失败的回调接口
                    view.stopLoading();
                    view.showError();
                }
            });
        } else { // 没网的时候加载数据库中的数据
             //当第一次安装应用，并且没有打开网络时
            //此时既无法网络加载，也无法本地加载
            if (list.isEmpty()) {
                view.showError();
            }
        }
    }

    @Override
    public void refresh() {
        loadPosts();
    }

    /**
     * 进入具体信息页面
     *
     * @param position
     */
    @Override
    public void startReading(int position) {

    }

    @Override
    public void feelLucky() {

    }

    @Override
    public void start() {
        loadPosts();
    }
}
