package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 异常邮件
 */
public class AnomalyMailActivity extends AutoLayoutActivity {

    @InjectView(R.id.anomaly_mail_rv)
    RecyclerView anomalyMailRv;
    private CommonAdapter<String> commonRVAdapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anomaly_mail_activity);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        anomalyMailRv.setLayoutManager(new LinearLayoutManager(this));
        addList();
        commonRVAdapter = new CommonAdapter<String>(this, R.layout.item_anomaly_mail, list) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, String s, int position) {

            }
        };
        anomalyMailRv.setAdapter(commonRVAdapter);
    }

    private void addList() {
        if (list == null) {
            list = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                list.add("A" + i);
            }
        }
    }
}
