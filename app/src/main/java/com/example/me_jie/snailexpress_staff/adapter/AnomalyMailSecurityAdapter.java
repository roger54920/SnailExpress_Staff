package com.example.me_jie.snailexpress_staff.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.me_jie.snailexpress_staff.R;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
/**
 * 已上线
 */

/**
 * Created by me-jie on 2017/4/7.
 */

public class AnomalyMailSecurityAdapter extends RecyclerView.Adapter {

    private Context mContext;
    /**
     * SparseBooleanArray 存放boolean 类型的pair(key,value)
     */
    private SparseBooleanArray mSelectArray;

    public AnomalyMailSecurityAdapter(Context context) {
        this.mContext = context;

        mSelectArray = new SparseBooleanArray();
    }

    public interface setCommonClickListener {
        void RadioButtonClickListener(View view, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_anomaly_mail_security, parent, false));
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        //RadioButton实现单选
        viewHolder.radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectArray.clear();
                mSelectArray.put(position, viewHolder.radio.isChecked());
                notifyDataSetChanged();
            }
        });
        viewHolder.radio.setChecked(mSelectArray.get(position, false));


    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.radio)
        RadioButton radio;
        public ItemViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
