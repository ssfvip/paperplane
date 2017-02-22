package com.example.ssf.paperplane.bean;

import java.util.ArrayList;

/**
 * 知乎新闻类标的实体类
 * Created by Administrator on 2017/2/21.
 */
//{
//        "date":"20170121",
//        "stories":[
//        {
//        "images":Array[1],
//        "type":0,
//        "id":9165425,
//        "ga_prefix":"012122",
//        "title":"小事 · 被家教性骚扰"
//        },
//        {
//        "images":[
//        "http://pic1.zhimg.com/ffcca2b2853f2af791310e6a6d694e80.jpg"
//        ],
//        "type":0,
//        "id":9165434,
//        "ga_prefix":"012121",
//        "title":"谁说普通人的生活就不能精彩有趣呢？"
//        },
public class ZhihuDailyNews {
    private String date;
    private ArrayList<Question> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Question> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Question> stories) {
        this.stories = stories;
    }

    // stories内部
    public class Question {
        private ArrayList<String> images;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;


        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}
