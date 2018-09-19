package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.custom.FullyLinearLayoutManager;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 站长签到
 */
public class StationAgentSignInActivity extends AutoLayoutActivity {

    @InjectView(R.id.sign_out_rv)
    RecyclerView signOutRv;
    @InjectView(R.id.courier_rv)
    RecyclerView courierRv;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    private CommonAdapter<String> SRCommonRVAdapter;
    private CommonAdapter<String> CRCommonRVAdapter;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoLayoutConifg.getInstance().useDeviceSize();
        setContentView(R.layout.activity_station_agent_sign_in);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        signOutRv.setFocusable(false);
        courierRv.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        //签退
        signOutRv.setLayoutManager(new FullyLinearLayoutManager(this));
        addList();
        SRCommonRVAdapter = new CommonAdapter<String>(this,R.layout.item_station_agent_sign,list) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, String s, int position) {

            }
        };
        signOutRv.setAdapter(SRCommonRVAdapter);
        //揽件
        courierRv.setLayoutManager(new FullyLinearLayoutManager(this));
        addList();
        CRCommonRVAdapter = new CommonAdapter<String>(this,R.layout.item_station_agent_sign,list) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, String s, int position) {

            }
        };
        courierRv.setAdapter(CRCommonRVAdapter);
    }
    private void addList(){
        list =  new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            list.add("a"+i);
        }
    }
}
