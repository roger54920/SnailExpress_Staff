package com.example.me_jie.snailexpress_staff.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.ChatBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by me-jie on 2017/4/8.
 * 聊天界面Adpater
 */

public class CharAdapter extends RecyclerView.Adapter {


    private List<ChatBean> mDatas;
    private Context mContext;
    public CharAdapter(Context context, List<ChatBean> chatList) {
        this.mContext = context;
        this.mDatas = chatList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CharItemViewHolder charItemViewHolder = new CharItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.char_item, parent, false));
        return charItemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CharItemViewHolder charItemViewHolder = (CharItemViewHolder) holder;
        ChatBean chatBean = mDatas.get(position);
        if (chatBean.getType() == chatBean.TYPE_RECEIVE) {
            charItemViewHolder.leftLayout.setVisibility(View.VISIBLE);
            charItemViewHolder.rightLayout.setVisibility(View.GONE);
            charItemViewHolder.leftMsg.setText(chatBean.getContent());
        } else if (chatBean.getType() == chatBean.TYPE_SEND) {
            charItemViewHolder.rightLayout.setVisibility(View.VISIBLE);
            charItemViewHolder.leftLayout.setVisibility(View.GONE);
            charItemViewHolder.rightMsg.setText(chatBean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class CharItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.char_datetime)
        TextView charDatetime;
        @InjectView(R.id.head1)
        ImageView head1;
        @InjectView(R.id.left_msg)
        TextView leftMsg;
        @InjectView(R.id.right_msg)
        TextView rightMsg;
        @InjectView(R.id.head2)
        ImageView head2;
        @InjectView(R.id.left_layout)
        LinearLayout leftLayout;
        @InjectView(R.id.right_layout)
        LinearLayout rightLayout;
        public CharItemViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
