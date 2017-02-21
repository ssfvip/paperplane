package com.example.ssf.paperplane.bean;

import java.util.ArrayList;

/**
 * 知乎新闻具体信息实体类
 * Created by Administrator on 2017/2/21.
 */
//{
//        "body":"html格式内容",
//"image_source":"《帕特森》",
//        "title":"谁说普通人的生活就不能精彩有趣呢？",
//        "image":"http://pic4.zhimg.com/e39083107b7324c6dbb725da83b1d7fb.jpg",
//        "share_url":"http://daily.zhihu.com/story/9165434",
//        "js":[
//
//        ],
//        "ga_prefix":"012121",
//        "section":{
//        "thumbnail":"http://pic4.zhimg.com/61a761f43efac75547edde6fd460e1a7.jpg",
//        "id":28,
//        "name":"放映机"
//        },
//        "images":[
//        "http://pic1.zhimg.com/ffcca2b2853f2af791310e6a6d694e80.jpg"
//        ],
//        "type":0,
//        "id":9165434,
//        "css":Array[1]
//        }
public class ZhihuDailyStory {

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private ArrayList<String> js;
    private String ga_prefix;
    private ArrayList<String> images;
    private int type;
    private int id;
    private ArrayList<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public ArrayList<String> getJs() {
        return js;
    }

    public void setJs(ArrayList<String> js) {
        this.js = js;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

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

    public ArrayList<String> getCss() {
        return css;
    }

    public void setCss(ArrayList<String> css) {
        this.css = css;
    }
}
