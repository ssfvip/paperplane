package com.example.ssf.paperplane.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ssf.paperplane.R;
import com.example.ssf.paperplane.adapter.MainPagerAdapter;

/**
 * 首页，利用tabLayout实现三个viewPager的切换
 * Created by Administrator on 2017/2/21.
 */
public class MainFragment extends Fragment {

    private Context context;
    private TabLayout tabLayout;
    private MainPagerAdapter pagerAdapter;
    //
    private ZhihuDailyFragment zhihuDailyFragment;
    private GuoKrFragment guokrFragment;
    private DouBanMomentFragment doubanMomentFragment;

    //
     private ZhihuDailyPresenter zhihuDailyPresenter;

//    単例模式
    public MainFragment(){}
    public static MainFragment newInstance(){
        return new MainFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();

        /**
         * 显示viewPager
         * 对缓存进行判空处理
         * 当为空的时候new实例出来使用
         */
        if (savedInstanceState != null){
            FragmentManager manger = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) manger.getFragment(savedInstanceState, "zhihu");
            guokrFragment = (GuoKrFragment) manger.getFragment(savedInstanceState, "guokr");
            doubanMomentFragment = (DouBanMomentFragment) manger.getFragment(savedInstanceState, "douban");
        }else {
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            guokrFragment = GuoKrFragment.newInstance();
            doubanMomentFragment = DouBanMomentFragment.newInstance();
        }
        // 初始化各个presenter
        zhihuDailyPresenter = new ZhihuDailyPresenter(context, zhihuDailyFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container, false);

        initViews(view);
        // 想让Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
        // 当tab layout位置为果壳精选时，隐藏fab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                if (tab.getPosition() == 1) {
                    fab.hide();
                } else {
                    fab.show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        return view;
    }

    private void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);

        pagerAdapter = new MainPagerAdapter(getChildFragmentManager(),
                context,
                zhihuDailyFragment,
                guokrFragment,
                doubanMomentFragment);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    public  MainPagerAdapter getAdapter(){
        return pagerAdapter;
    }
}
