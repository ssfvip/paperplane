package com.example.ssf.paperplane.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ssf.paperplane.R;
import com.example.ssf.paperplane.bean.ZhihuDailyNews;

import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2017/2/21.
 */
public class ZhihuDailyNewsAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private ArrayList<ZhihuDailyNews.Question> mList = new ArrayList<>();
    private LayoutInflater inflater;


    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    public ZhihuDailyNewsAdapter(Context context, ArrayList<ZhihuDailyNews.Question> list) {
        this.mContext = context;
        this.mList = list;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case TYPE_NORMAL:

//                return new NormalViewHolder(inflater.inflate(R.layout.home_list_item_layout, parent, false),
//                        );
                break;
            case TYPE_FOOTER:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImg;
        private TextView tvLatestNewsTitle;

        public NormalViewHolder(View itemView) {
            super(itemView);

//            itemImg = itemView.findViewById(R.id.)
        }

        @Override
        public void onClick(View v) {

        }
    }
}
