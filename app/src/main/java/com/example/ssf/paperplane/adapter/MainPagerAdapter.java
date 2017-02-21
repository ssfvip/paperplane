package com.example.ssf.paperplane.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ssf.paperplane.R;
import com.example.ssf.paperplane.homepage.DouBanMomentFragment;
import com.example.ssf.paperplane.homepage.GuoKrFragment;
import com.example.ssf.paperplane.homepage.ZhihuDailyFragment;

/**
 * 首页tabLayout的适配器
 * Created by Administrator on 2017/2/21.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private String[] title;
    private  Context mContext;

    private GuoKrFragment guokrFragment;
    private ZhihuDailyFragment zhihuFragment;
    private DouBanMomentFragment doubanFragment;



    public GuoKrFragment getGuokrFragment() {
        return guokrFragment;
    }

    public ZhihuDailyFragment getZhihuFragment() {
        return zhihuFragment;
    }

    public DouBanMomentFragment getDoubanFragment() {
        return doubanFragment;
    }

    public MainPagerAdapter(FragmentManager childFragmentManager,
                            Context context,
                            ZhihuDailyFragment zhihuDailyFragment,
                            GuoKrFragment guokrFragment,
                            DouBanMomentFragment doubanMomentFragment) {
        super(childFragmentManager);

        this.mContext = context;
        title = new String[]{
                mContext.getResources().getString(R.string.zhihu_daily),
                mContext.getResources().getString(R.string.guokr_handpick),
                mContext.getResources().getString(R.string.douban_moment)
        };

        this.guokrFragment = guokrFragment;
        this.zhihuFragment = zhihuDailyFragment;
        this.doubanFragment = doubanMomentFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return guokrFragment;
        }else if (position == 2){
            return doubanFragment;
        }
        return zhihuFragment;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    // 设置标题栏的点击事件
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
