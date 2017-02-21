package com.example.ssf.paperplane.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ssf.paperplane.R;
import com.example.ssf.paperplane.adapter.ZhihuDailyNewsAdapter;
import com.example.ssf.paperplane.bean.ZhihuDailyNews;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * V模式
 * Created by Administrator on 2017/2/21.
 */
public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;
    private FloatingActionButton fab;
    private TabLayout tabLayout;

    private ZhihuDailyNewsAdapter adapter;

    // 转换年月日的实例
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    //获取P模式的实例，在persenter中进行逻辑操作
    private ZhihuDailyContract.Presenter presenter;

    public ZhihuDailyFragment() {

    }

    public static ZhihuDailyFragment newInstance() {
        return new ZhihuDailyFragment();
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

        presenter.start(); // 启动presenter

        //下拉刷新设置
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 使方法传递到presenter中执行刷新操作
                presenter.refresh();
            }
        });


        return view;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {

        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refresh.setColorSchemeResources(R.color.colorPrimary);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
    }

    /**
     * 显示加载网络数据错误的方法
     */
    @Override
    public void showError() {
        // 使用design包下的新特性，来做一个toast的效果
        Snackbar.make(fab, R.string.loaded_failed, Snackbar.LENGTH_INDEFINITE).
                setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 传递刷新操作
                        presenter.refresh();
                    }
                }).show();
    }

    /**
     * 显示进度条
     */
    @Override
    public void showLoading() {
        refresh.post(new Runnable() {
            @Override
            public void run() {
        refresh.setRefreshing(true);
            }
        });
    }

    /**
     * 关闭进度条
     */
    @Override
    public void stopLoading() {
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        });
    }

    /**
     * 返回解析结果
     */
    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Question> list) {

        if (adapter == null){
            adapter = new ZhihuDailyNewsAdapter(getContext(), list);
        }
    }


}
