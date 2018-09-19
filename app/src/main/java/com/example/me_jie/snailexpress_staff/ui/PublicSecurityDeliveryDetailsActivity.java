package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.adapter.HistoicFlowAdapter;
import com.example.me_jie.snailexpress_staff.bean.ChatBean;
import com.example.me_jie.snailexpress_staff.bean.HistoicFlowBean;
import com.example.me_jie.snailexpress_staff.custom.FullyLinearLayoutManager;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.mvp.HistoicFlowView;
import com.example.me_jie.snailexpress_staff.presenter.HistoicFlowPresenter;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 保安快递详情 确认接收
 */
public class PublicSecurityDeliveryDetailsActivity extends AutoLayoutActivity implements HistoicFlowView{

    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.chat_recyclerview)
    RecyclerView chatRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.affirm_reception)
    Button affirmReception;
    private List<ChatBean> chatList;

    private Intent intent;
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_security_delivery_details);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        timelineRecyclerview.setFocusable(false);
        chatRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);
        String procInsId = getIntent().getStringExtra("act_procInsId");
        if(procInsId!=null){
            histoicFlowPresenter.attach(this);
            RequestOperationUtil.setSendHistoicFlowResult(procInsId,this,histoicFlowPresenter);
        }
        //聊天适配器
//        initMsg();
//        chatRecyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
//        chatRecyclerview.setAdapter(new CharAdapter(this, chatList));
    }

    private void initMsg() {
        if (chatList == null) {
            chatList = new ArrayList<>();
            ChatBean msg1 = new ChatBean("请问几点可以送货？", ChatBean.TYPE_RECEIVE);
            chatList.add(msg1);
            ChatBean msg2 = new ChatBean("今天13:30吧！", ChatBean.TYPE_SEND);
            chatList.add(msg2);
            ChatBean msg = new ChatBean("好的！", ChatBean.TYPE_RECEIVE);
            chatList.add(msg);
        }

    }
    @OnClick({R.id.return_arrows, R.id.affirm_reception,R.id.zoom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zoom:
                if (zoom.getText().equals("展开")) {
                    zoom.setText("收起");
                    timelineRecyclerview.setVisibility(View.VISIBLE);
                } else if (zoom.getText().equals("收起")) {
                    zoom.setText("展开");
                    timelineRecyclerview.setVisibility(View.GONE);
                }
                break;
            case R.id.return_arrows:
                finish();
                break;
            case R.id.affirm_reception:
                intent = new Intent(PublicSecurityDeliveryDetailsActivity.this,StaffUserQRCodeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onHistoicFlowFinish(Object o) {
        HistoicFlowBean histoicFlowBean = (HistoicFlowBean) o;
        if(histoicFlowBean!=null){
            timelineRecyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
            timelineRecyclerview.setAdapter(new HistoicFlowAdapter(this, histoicFlowBean.getListTrajectory()));
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        histoicFlowPresenter.dettach();
    }
}
