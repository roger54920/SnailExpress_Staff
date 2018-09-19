package com.example.me_jie.snailexpress_staff.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.me_jie.snailexpress_staff.R;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by me-jie on 2017/4/13.
 * 超期包裹 适配器
 */

public class ExceedTimeParcelAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public ExceedTimeParcelAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_exceed_time_parcel, parent, false));
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.itemExceedTimeRv.setLayoutManager(new LinearLayoutManager(mContext));
        itemViewHolder.itemExceedTimeRv.setAdapter(new ExceedTimeLineAdapter(mContext));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.item_exceed_time_rv)
        RecyclerView itemExceedTimeRv;
        public ItemViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
