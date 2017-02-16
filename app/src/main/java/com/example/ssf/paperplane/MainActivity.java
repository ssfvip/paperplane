package com.example.ssf.paperplane;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
