package com.example.ssf.paperplane.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ssf.paperplane.R;
import com.example.ssf.paperplane.bean.GuokrHandpickNews;
import com.example.ssf.paperplane.interfaces.OnRecyclerViewOnClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/23.
 */
public class GuokrNewsAdapter extends RecyclerView.Adapter<GuokrNewsAdapter.GuokrPostViewHolder> {

    private final Context mContext;
    private ArrayList<GuokrHandpickNews.result> mList;
    private final LayoutInflater inflate;

    //定义点击时间的接口变量
    private OnRecyclerViewOnClickListener mListener;

    public GuokrNewsAdapter(Context context, ArrayList<GuokrHandpickNews.result> list) {
        this.mContext = context;
        this.mList = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public GuokrPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflate.inflate(R.layout.home_list_item_layout, parent, false);
        return new GuokrPostViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(GuokrPostViewHolder holder, int position) {

        GuokrHandpickNews.result item = mList.get(position);
        Glide.with(mContext)
                .load(item.getHeadline_img_tb())
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.placeholder)
                .centerCrop()
                .into(holder.ivHeadlineImg);

        holder.tvTitle.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class GuokrPostViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        ImageView ivHeadlineImg;
        TextView tvTitle;
        // 初始化点击事件的
        OnRecyclerViewOnClickListener listener;

        public GuokrPostViewHolder(View itemView, OnRecyclerViewOnClickListener mListener) {
            super(itemView);

            ivHeadlineImg = (ImageView) itemView.findViewById(R.id.imageViewCover);
            tvTitle = (TextView) itemView.findViewById(R.id.textViewTitle);

            this.listener = mListener;

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (mListener != null){
                mListener.OnItemClick(v,getLayoutPosition());
            }
        }

    }

    /**
     * 对外提供点击事件的实例
     * @param listener
     */
    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener = listener;
    }
}
