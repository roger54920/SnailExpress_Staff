package com.example.me_jie.snailexpress_staff.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.R;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by me-jie on 2017/4/13.
 * 超期包裹中 item的时间轴
 */

public class ExceedTimeLineAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public ExceedTimeLineAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LineItemViewHolder lineItemViewHolder = new LineItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_exced_time_line, parent, false));
        return lineItemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LineItemViewHolder lineItemViewHolder = (LineItemViewHolder) holder;
        lineItemViewHolder.exceedTimeImg.setImageResource(R.drawable.timeline_2);
    }

    class LineItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.exceed_time_img)
        ImageView exceedTimeImg;
        @InjectView(R.id.context)
        TextView context;
        public LineItemViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
