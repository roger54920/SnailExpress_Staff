package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.adapter.HistoicFlowAdapter;
import com.example.me_jie.snailexpress_staff.bean.HistoicFlowBean;
import com.example.me_jie.snailexpress_staff.custom.FullPhotoView;
import com.example.me_jie.snailexpress_staff.custom.FullyLinearLayoutManager;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.mvp.HistoicFlowView;
import com.example.me_jie.snailexpress_staff.presenter.HistoicFlowPresenter;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 收快递 输入货柜编号
 */
public class ReceiveExpressageInputContainerActivity extends AutoLayoutActivity implements HistoicFlowView{

    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.zoom)
    TextView zoom;
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_expressage_input_container);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);
        String procInsId = getIntent().getStringExtra("act_procInsId");
        if(procInsId!=null){
            histoicFlowPresenter.attach(this);
            RequestOperationUtil.setSendHistoicFlowResult(procInsId,this,histoicFlowPresenter);
        }
    }


    @OnClick({R.id.zoom})
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

