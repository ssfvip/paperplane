package com.example.ssf.paperplane.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ssf.paperplane.R;
import com.example.ssf.paperplane.adapter.MainPagerAdapter;
import com.example.ssf.paperplane.adapter.ZhihuDailyNewsAdapter;
import com.example.ssf.paperplane.bean.ZhihuDailyNews;
import com.example.ssf.paperplane.interfaces.OnRecyclerViewOnClickListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

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

        // 设置recyclerView的滚动事件监听,既实现上拉加载更多的设置
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    // 获取最后一个完全显示的item position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast){
                        // 获取时间的实例
                        Calendar c = Calendar.getInstance();
                        // 设置时间，加载前一天的信息
                        c.set(mYear, mMonth, --mDay);

                        //实现加载更多的操作，传入时间参数
                        presenter.loadMore(c.getTimeInMillis());


                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            /**
             * 设置滑动监听事件
             * @param recyclerView
             * @param dx
             * @param dy
             */
            private boolean a;
            private int b = 2;

            public Boolean start(){
                a = b >0 ;
                return a;
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy >0; //是一个判断，判断如果滑动，将isSlidingToLast=true
                //隐藏或者显示fab
                if (dy > 0){
                    fab.hide();
//                    isSlidingToLast = true;
                }else {
                    fab.show();
                }
            }
        });

// 直接将豆瓣精选的fab点击事件放在知乎的部分
        // 因为fab是属于activity的view
        // 按通常的做法，在每个fragment中去设置监听时间会导致先前设置的listener失效
        // 尝试将监听放置到main pager adapter中，这样做会引起fragment中recycler view和fab的监听冲突
        // fab并不能获取到点击事件
        // 根据tab layout的位置选择显示不同的dialog
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    Calendar now = Calendar.getInstance();
                    now.set(mYear, mMonth, mDay);
                    DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            Calendar temp = Calendar.getInstance();
                            temp.clear();
                            temp.set(year, monthOfYear, dayOfMonth);
                            presenter.loadPosts(temp.getTimeInMillis(), true);
                        }
                    }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

                    dialog.setMaxDate(Calendar.getInstance());
                    Calendar minDate = Calendar.getInstance();
                    // 2013.5.20是知乎日报api首次上线
                    minDate.set(2013, 5, 20);
                    dialog.setMinDate(minDate);
                    dialog.vibrate(false);

                    dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    ViewPager p = (ViewPager) getActivity().findViewById(R.id.view_pager);
                    MainPagerAdapter ad = (MainPagerAdapter) p.getAdapter();
//                    ad.getDoubanFragment().showPickDialog();
                }
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
            // 传递数据到adapter
            adapter = new ZhihuDailyNewsAdapter(getContext(), list);
            // item点击事件
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    // 将点击事件传递到presenter中进行实现
                    presenter.startReading(position);
                }
            });
            // 绑定适配器
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }


}
