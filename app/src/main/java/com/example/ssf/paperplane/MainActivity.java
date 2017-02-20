package com.example.ssf.paperplane;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/**
 * 主页的设计是，根据侧滑菜单的切换两种模式，来控制首页和收藏页面的
 * 既MainFragment，BookmarksFragment
 */
public class MainActivity extends AppCompatActivity {


    //侧滑布局
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private static final String ACTION_BOOKMARKS = ""; // 用于判断当前页面位置的标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}

//    about 关于页面
//    |   |   |   |   |   ├── adapter RecyclerView与ViewPager等控件的Adapter
//    |   |   |   |   |   ├── app Application
//    |   |   |   |   |   ├── bean 存放实体类
//    |   |   |   |   |   ├── bookmarks 收藏页面
//    |   |   |   |   |   ├── customtabs Chrome Custom Tabs相关
//    |   |   |   |   |   ├── db 数据库相关
//    |   |   |   |   |   ├── detail 详细内容页面
//    |   |   |   |   |   ├── homepage 首页页面
//    |   |   |   |   |   ├── innerbrowser 内置浏览器页面
//    |   |   |   |   |   ├── interfaze 接口集合
//    |   |   |   |   |   ├── license 开源许可证页面
//    |   |   |   |   |   ├── search 搜索页面
//    |   |   |   |   |   ├── service Service集合
//    |   |   |   |   |   ├── settings 设置页面
//    |   |   |   |   |   ├── util 工具类集合
//    |   |   |   |   |   ├── BasePresenter.java Presenter基类
//    |   |   |   |   |   ├── BaseView.java View基类
