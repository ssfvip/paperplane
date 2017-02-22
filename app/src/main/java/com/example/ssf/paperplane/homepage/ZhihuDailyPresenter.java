package com.example.ssf.paperplane.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.example.ssf.paperplane.bean.StringModelImpl;
import com.example.ssf.paperplane.bean.ZhihuDailyNews;
import com.example.ssf.paperplane.db.DatabaseHelper;
import com.example.ssf.paperplane.interfaces.OnStringListener;
import com.example.ssf.paperplane.service.CacheService;
import com.example.ssf.paperplane.util.Api;
import com.example.ssf.paperplane.util.DateFormatter;
import com.example.ssf.paperplane.util.NetworkState;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * P模式进行交互逻辑编码
 * Created by Administrator on 2017/2/21.
 */
public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private ZhihuDailyContract.View view;
    private Context context;
    // 利用volley解析框架
    private StringModelImpl model;

    private DateFormatter formatter = new DateFormatter(); // 转换时间格式的额工具
    private Gson gson = new Gson();

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<ZhihuDailyNews.Question> list = new ArrayList<>();


    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this); //  初始化presenter，忘记了会导致view（ZhihuDailyFragment）会空指针
        model = new StringModelImpl(context);

        dbHelper = new DatabaseHelper(context, "History.db", null, 5);
        db = dbHelper.getWritableDatabase();

    }

    /**
     * 解析网络数据
     *
     * @param date     传入的时间参数
     * @param clearing 判断缓存参数
     */
    @Override
    public void loadPosts(long date, final boolean clearing) {

        if (clearing) {
            view.showLoading();
        }
        if (NetworkState.networkConnected(context)) {
            model.load(Api.ZHIHU_HISTORY + formatter.ZhihuDailyDateFormat(date), new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    try {
                        ZhihuDailyNews post = gson.fromJson(result, ZhihuDailyNews.class);
                        ContentValues values = new ContentValues();
                        if (clearing) {
                            list.clear();
                        }
                        // 遍历Stories下边的子条目
                        for (ZhihuDailyNews.Question item : post.getStories()) {
                            list.add(item);
                            //启用数据库进行缓存
                            if (!queryIfIDExists(item.getId())){
                                db.beginTransaction();
                                DateFormat format = new SimpleDateFormat("yyyMMdd");
                                try {
                                    Date date = format.parse(post.getDate());
                                    //存入数据库
                                    values.put("zhihu_id", item.getId());
                                    values.put("zhihu_news", gson.toJson(item));
                                    values.put("zhihu_content", "");
                                    values.put("zhihu_time", date.getTime() / 1000);
                                    db.insert("Zhihu", null, values);
                                    values.clear();
                                    db.setTransactionSuccessful(); //将当前事务标记为成功。不做任何更多的数据库调用,调用endTransaction之间的工作。做尽可能少的非数据库工作在这种情况下
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }finally {
                                    db.endTransaction();// 结束一个事务。看到beginTransaction指出如何使用这个事务被提交和回滚。
                                }
                            }
//            请求知乎消息列表的时候，并没有返回消息的详细内容呀。不过详细内容我们还是需要缓存的，
//            网络请求在UI线程上进行可能会引起ANR，那更好的解决办法就是在Service里面完成了
                            Intent intent = new Intent("com.marktony.zhihudaily.LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_ZHIHU);
                            intent.putExtra("id", item.getId());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


                        }
                        view.showResults(list);
                    } catch (JsonSyntaxException e) {
                        view.showError();
                    }
                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showError();
                }
            });
        }else { //无网络状态下
            if (clearing){
                list.clear();
                Cursor cursor = db.query("Zhihu", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        ZhihuDailyNews.Question question = gson.fromJson(cursor.getString(cursor.getColumnIndex("zhihu_news")), ZhihuDailyNews.Question.class);
                        list.add(question);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                view.stopLoading();
                view.showResults(list);
            }else {
                view.showError();
            }
        }
    }

    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void startReading(int position) {

    }

    @Override
    public void feelLucky() {

    }

    @Override
    public void start() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    /**
     * 利用ID来查询数据库
     * @param id
     * @return
     */
    private boolean queryIfIDExists(int id){
        Cursor cursor = db.query("Zhihu",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                if (id == cursor.getInt(cursor.getColumnIndex("zhihu_id"))){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return false;
    }
}
