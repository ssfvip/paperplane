package com.example.ssf.paperplane.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ssf.paperplane.R;
import com.example.ssf.paperplane.bean.ZhihuDailyNews;
import com.example.ssf.paperplane.interfaces.OnRecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/2/21.
 */
public class ZhihuDailyNewsAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private ArrayList<ZhihuDailyNews.Question> mList = new ArrayList<>();
    private LayoutInflater inflater;

    // 点击事件的接口
    private OnRecyclerViewOnClickListener listener;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    public ZhihuDailyNewsAdapter(Context context, ArrayList<ZhihuDailyNews.Question> list) {
        this.mContext = context;
        this.mList = list;
        this.inflater = LayoutInflater.from(mContext);
    }

    /**
     * 在此处进行添加布局的额操作
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case TYPE_NORMAL:
                // 返回item的布局
                return new NormalViewHolder(inflater.inflate(R.layout.home_list_item_layout, parent, false)
                        ,listener);

            case TYPE_FOOTER: // 上拉加载更多的布局设置
                return new FooterViewHolder(inflater.inflate(R.layout.list_footer, parent, false));
        }
        return null;
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof NormalViewHolder){ // instanceof用来判断内存中实际对象A是不是B类型
            ZhihuDailyNews.Question item = mList.get(position); // 获取集合中的数据

            if (item.getImages().get(0) == null){
                // 对数据进行判空处理，添加默认图片
                ((NormalViewHolder) holder).itemImg.setImageResource(R.drawable.placeholder);
            }else {
                Glide.with(mContext)
                        .load(item.getImages().get(0))
                        .asBitmap() //显示gif静态图片  asGif()显示gif动态图片
                        .placeholder(R.drawable.placeholder) // 放占位的图片
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE) // 方法设置存储的位置和大小
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((NormalViewHolder) holder).itemImg);
            }
            ((NormalViewHolder) holder).tvLatestNewsTitle.setText(item.getTitle());
        }
    }
// 因为含有一个foot，所以item+1显示
    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    /**
     * 获取不同的ViewType，加载不同的布局
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()){ // 如果是下拉到底部，显示foot布局
            return ZhihuDailyNewsAdapter.TYPE_FOOTER;
        }else {
            return ZhihuDailyNewsAdapter.TYPE_NORMAL;
        }

    }

    /**
     * 控制item 的点击事件，对外提供的方法
     * @param listener
     */
     public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.listener = listener;
    }
    /**
     * 正常情况下的item内容以及点击事件的集成
     */
    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImg;
        private TextView tvLatestNewsTitle;
        private OnRecyclerViewOnClickListener listener;
        public NormalViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);

            itemImg = (ImageView) itemView.findViewById(R.id.imageViewCover); // 新闻图片
            tvLatestNewsTitle = (TextView) itemView.findViewById(R.id.textViewTitle); // 新闻标题
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (listener != null){
            listener.OnItemClick(v, getLayoutPosition());
            }
        }
    }

    /**
     * 脚部的上拉加载设置
     */
    public class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
