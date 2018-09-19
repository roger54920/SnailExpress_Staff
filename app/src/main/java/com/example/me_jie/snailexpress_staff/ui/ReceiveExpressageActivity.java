package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.adapter.HistoicFlowAdapter;
import com.example.me_jie.snailexpress_staff.bean.HistoicFlowBean;
import com.example.me_jie.snailexpress_staff.custom.FullyLinearLayoutManager;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.mvp.HistoicFlowView;
import com.example.me_jie.snailexpress_staff.presenter.HistoicFlowPresenter;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.config.AutoLayoutConifg;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 收快递 输入编号 寄快递操作
 */
public class ReceiveExpressageActivity extends AutoLayoutActivity implements HistoicFlowView{

    @InjectView(R.id.receive_expressage_rv)
    RecyclerView receiveExpressageRv;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.affirm_godown_btn)
    Button affirmGodownBtn;
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoLayoutConifg.getInstance().useDeviceSize();
        setContentView(R.layout.activity_receive_expressage);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
//        receiveExpressageRv.setFocusable(false);
//        myScrollview.smoothScrollTo(0, 0);
        String procInsId = getIntent().getStringExtra("act_procInsId");
        if(procInsId!=null){
            histoicFlowPresenter.attach(this);
            RequestOperationUtil.setSendHistoicFlowResult(procInsId,this,histoicFlowPresenter);
        }
    }



    @OnClick({R.id.return_arrows, R.id.affirm_godown_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.affirm_godown_btn:
                SkipIntentUtil.skipIntent(this,ReceiveExpressageTwoActivity.class);
                break;
        }
    }

    @Override
    public void onHistoicFlowFinish(Object o) {
        HistoicFlowBean histoicFlowBean = (HistoicFlowBean) o;
        if(histoicFlowBean!=null){
            receiveExpressageRv.setLayoutManager(new FullyLinearLayoutManager(this));
            receiveExpressageRv.setAdapter(new HistoicFlowAdapter(this, histoicFlowBean.getListTrajectory()));
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
