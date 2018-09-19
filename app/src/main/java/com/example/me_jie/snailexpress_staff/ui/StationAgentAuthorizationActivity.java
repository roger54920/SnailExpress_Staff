package com.example.me_jie.snailexpress_staff.ui;
/**
 * 站长授权
 */

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.adapter.AnomalyMailSecurityAdapter;
import com.example.me_jie.snailexpress_staff.custom.FullyLinearLayoutManager;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StationAgentAuthorizationActivity extends AutoLayoutActivity {

    @InjectView(R.id.on_line_rv)
    RecyclerView onLineRv;
    @InjectView(R.id.no_line_rv)
    RecyclerView noLineRv;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_agent_authorization);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        onLineRv.setFocusable(false);
        noLineRv.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        onLineRv.setLayoutManager(new FullyLinearLayoutManager(this));
        onLineRv.setAdapter(new AnomalyMailSecurityAdapter(this));

        noLineRv.setLayoutManager(new FullyLinearLayoutManager(this));
        noLineRv.setAdapter(new AnomalyMailSecurityAdapter(this));
    }
}
