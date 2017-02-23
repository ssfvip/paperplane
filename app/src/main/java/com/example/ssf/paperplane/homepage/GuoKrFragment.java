package com.example.ssf.paperplane.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ssf.paperplane.R;
import com.example.ssf.paperplane.adapter.GuokrNewsAdapter;
import com.example.ssf.paperplane.bean.GuokrHandpickNews;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/21.
 */
public class GuoKrFragment extends Fragment implements GuokrContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private GuokrNewsAdapter adapter;
    private GuokrContract.Presenter presenter; // 初始化自身对应的presenter

    // 单例模式
    public GuoKrFragment() {
    }

    public static GuoKrFragment newInstance() {
        return new GuoKrFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initViews(view);
        presenter.start(); // 保证项目运行起来以后开始执行一次加载数据

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        return view;
    }

    @Override
    public void showError() {
        //利用design包的特性，参数一：任意该布局上的view，参数二：显示错误提示内容，参数三：显示时长
        Snackbar.make(refreshLayout, R.string.loaded_failed, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() { // 重试
                    @Override
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                });
    }

    /**
     * 加载数据成功绑定数据到adapter中，然后再和recyclerview进行绑定
     *
     * @param list
     */
    @Override
    public void showResults(ArrayList<GuokrHandpickNews.result> list) {

        if (adapter == null){
            adapter = new GuokrNewsAdapter(getActivity(), list);
            // 缺少item的点击事件


            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    /**
     * 继承的baseview 中的方法
     *
     * @param presenter
     */
    @Override
    public void setPresenter(GuokrContract.Presenter presenter) {

        if (presenter != null){
            this.presenter = presenter;
        }
    }

    /**
     * 继承的baseview 中的方法
     *
     * @param view
     */
    @Override
    public void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }
}
