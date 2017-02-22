package com.example.ssf.paperplane;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.ssf.paperplane.about.AboutPreferenceActivity;
import com.example.ssf.paperplane.bookmarks.BookmarksFragment;
import com.example.ssf.paperplane.homepage.MainFragment;
import com.example.ssf.paperplane.service.CacheService;
import com.example.ssf.paperplane.settings.SettingsPreferenceActivity;
import com.githang.statusbar.StatusBarCompat;

/**
 * 主页的设计是，根据侧滑菜单的切换两种模式，来控制首页和收藏页面的
 * 既MainFragment，BookmarksFragment
 * 实现接口nav...用来设置侧滑item的点击事件的监听
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //两个模式
    private MainFragment mainFragment; //首页
    private BookmarksFragment bookmarksFragment; // 收藏页
    //侧滑布局
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private static final String ACTION_BOOKMARKS = "com.example.ssf.paperplane.bookmarks"; // 用于判断当前页面位置的标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        StatusBarCompat.setStatusBarColor(this,0xFF455ede , true);

        initViews();
        if (savedInstanceState != null) {
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "MainFragment");
            bookmarksFragment = (BookmarksFragment) getSupportFragmentManager().getFragment(savedInstanceState, "BookmarksFragment");
        } else {
            mainFragment = MainFragment.newInstance();
            bookmarksFragment = BookmarksFragment.newInstance();
        }

        if (!mainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment,  // framLayout布局
                            mainFragment, // 被添加的布局
                            "MainFragment") // 标签值，通过标签进行检索，将fragment实例添加到第二个参数的额身上
                    .commit();
        }

        if (!bookmarksFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, bookmarksFragment, "BookmarksFragment")
                    .commit();
        }

//        new BookmarksPresenter(MainActivity.this, bookmarksFragment);

        String action = getIntent().getAction();

        if (action.equals(ACTION_BOOKMARKS)) {
            showBookmarksFragment();
            navigationView.setCheckedItem(R.id.nav_bookmarks);
        } else {
            showMainFragment();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        // 启动服务
        startService(new Intent(this, CacheService.class));
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //设置标题栏
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /**
         * ActionBarDrawerToggle  是 DrawerLayout.DrawerListener实现。
        和 NavigationDrawer 搭配使用，推荐用这个方法，符合Android design规范。
        作用：
        1.改变android.R.id.home返回图标。
        2.Drawer拉出、隐藏，带有android.R.id.home动画效果。
        3.监听Drawer拉出、隐藏；
         */
        /**
         * Construct a new ActionBarDrawerToggle with a Toolbar.
         * <p>
         * The given {@link Activity} will be linked to the specified {@link DrawerLayout} and
         * the Toolbar's navigation icon will be set to a custom drawable. Using this constructor
         * will set Toolbar's navigation click listener to toggle the drawer when it is clicked.
         * <p>
         * This drawable shows a Hamburger icon when drawer is closed and an arrow when drawer
         * is open. It animates between these two states as the drawer opens.
         * <p>
         * String resources must be provided to describe the open/close drawer actions for
         * accessibility services.
         * <p>
         * Please use {@link #ActionBarDrawerToggle(Activity, DrawerLayout, int, int)} if you are
         * setting the Toolbar as the ActionBar of your activity.
         *
         * @param activity                  The Activity hosting the drawer.
         * @param toolbar                   The toolbar to use if you have an independent Toolbar.
         * @param drawerLayout              The DrawerLayout to link to the given Activity's ActionBar
         * @param openDrawerContentDescRes  A String resource to describe the "open drawer" action
         *                                  for accessibility
         * @param closeDrawerContentDescRes A String resource to describe the "close drawer" action
         *                                  for accessibility
         */
        // 标题栏和侧滑的捆绑器，用来显示一个Hamburger用于启动侧滑，
        // 然后侧滑后会显示一个箭头用于退出侧滑,一个监听侧滑的监听设置

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
               R.string.nav_drawer_open, R.string.nav_drawer_close );
        drawer.setDrawerListener(toggle);
        toggle.syncState();//该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果(拖动)，不过是默认的图标（可能没有任何图标）

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // 设置item的点击事件的监听
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onDestroy() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (CacheService.class.getName().equals(service.service.getClassName())) {
                stopService(new Intent(this, CacheService.class));
            }
        }
        super.onDestroy();
    }
    /**
     * 显示收藏页面
     */
    private void showBookmarksFragment() {
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.show(bookmarksFragment);
        tran.hide(mainFragment);
        tran.commit();

        toolbar.setTitle("收藏");

        if (bookmarksFragment.isAdded()) {
//            bookmarksFragment.notifyDataChanged();
        }
    }
    /**
     * 显示首页
     */
    private void showMainFragment() {
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.show(mainFragment);
        tran.hide(bookmarksFragment);
        tran.commit();

        toolbar.setTitle("无用童趣");
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START); // GravityCompat.START既是设置侧滑出来的方向，和xml中设置保持一致

        switch (item.getItemId()){
            case R.id.nav_home: // 首页
                showMainFragment();
                break;
            case R.id.nav_bookmarks:// 收藏页
                showBookmarksFragment();
                break;
            case R.id.nav_change_theme:// 设置主题
                // 添加设置夜晚模式的监听
                drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // 设置不同的装态并且保存用0/1来区分白天模式还是黑夜模式
                        SharedPreferences  sp = getSharedPreferences("user_setting", MODE_PRIVATE);
                        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                                == Configuration.UI_MODE_NIGHT_YES){
                            sp.edit().putInt("theme", 0).apply();
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }else{
                            sp.edit().putInt("theme", 1).apply();
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }
                        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                        recreate();
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {

                    }
                });
                break;
            case R.id.nav_settings:// 设置
                startActivity(new Intent(this, SettingsPreferenceActivity.class));
                break;
            case R.id.nav_about:// 关于
                startActivity(new Intent(this,AboutPreferenceActivity.class));
                break;
            default:
                break;

        }

        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mainFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }
        if (bookmarksFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "BookmarksFragment", bookmarksFragment);
        }
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

//    |   |   |   |   |   ├── BasePresenter.java Presenter基类
//    |   |   |   |   |   ├── BaseView.java View基类
