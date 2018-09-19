package com.example.me_jie.snailexpress_staff.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.HistoicFlowBean;
import com.example.me_jie.snailexpress_staff.utils.AcquisitionTimeUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.ParseException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by me-jie on 2017/4/8.
 * 时间轴
 */

public class HistoicFlowAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<HistoicFlowBean.ListTrajectoryBean> mDatas;
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;

    public HistoicFlowAdapter(Context context, List<HistoicFlowBean.ListTrajectoryBean> timeLineList) {
        this.mContext = context;
        this.mDatas = timeLineList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.timeline_item, parent, false));
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        HistoicFlowBean.ListTrajectoryBean histoicFlowBean = mDatas.get(position);
        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            viewHolder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            viewHolder.tvAcceptTime.setTextColor(Color.parseColor("#6fd1c8"));
            viewHolder.tvAcceptStation.setTextColor(Color.parseColor("#6fd1c8"));
            viewHolder.tvDot.setBackgroundResource(R.drawable.point_top_icon);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            viewHolder.tvTopLine.setVisibility(View.VISIBLE);
            viewHolder.tvAcceptTime.setTextColor(Color.parseColor("#999999"));
            viewHolder.tvAcceptStation.setTextColor(Color.parseColor("#999999"));
            viewHolder.tvDot.setBackgroundResource(R.drawable.point_item_icon);
        }
        try {
            viewHolder.tvAcceptTime.setText(AcquisitionTimeUtil.longToString(histoicFlowBean.getPDate(), "yyyy-MM-dd HH:mm:ss"));
            viewHolder.tvAcceptStation.setText(histoicFlowBean.getPContent());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }
    class ItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tvTopLine)
        TextView tvTopLine;
        @InjectView(R.id.tvDot)
        ImageView tvDot;
        @InjectView(R.id.tvAcceptTime)
        TextView tvAcceptTime;
        @InjectView(R.id.tvAcceptStation)
        TextView tvAcceptStation;
        public ItemViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
