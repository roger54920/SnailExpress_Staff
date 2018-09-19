package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 收快递 无确认出库
 */
public class ReceiveExpressageTwoActivity extends AutoLayoutActivity implements HistoicFlowView{

    @InjectView(R.id.receive_expressage_rv)
    RecyclerView receiveExpressageRv;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_expressage_two);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        receiveExpressageRv.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);
        String procInsId = getIntent().getStringExtra("act_procInsId");
        if(procInsId!=null){
            histoicFlowPresenter.attach(this);
            RequestOperationUtil.setSendHistoicFlowResult(procInsId,this,histoicFlowPresenter);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SkipIntentUtil.skipIntent(this,SendExpressageActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @OnClick(R.id.return_arrows)
    public void onViewClicked() {
        SkipIntentUtil.skipIntent(this,SendExpressageActivity.class);
        finish();
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
