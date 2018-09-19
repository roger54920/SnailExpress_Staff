package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
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
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.me_jie.snailexpress_staff.R.id.express_number;

/**
 * 站长收件 蜗牛派送中
 */
public class AdministratorRecipientsOverseaActivity extends AutoLayoutActivity implements HistoicFlowView {

    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(express_number)
    TextView expressNumber;
    @InjectView(R.id.title_tv)
    TextView titleTv;

    private Intent intent;
    private String source;//来源页面
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_recipients_oversea);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        intent = getIntent();
        if (intent != null) {
            SkipIntentUtil.setTitleAcceptOrPay(getIntent().getStringExtra("home_source"),titleTv);
            source = intent.getStringExtra("source");
            RequestOperationUtil.setGlide(this, getIntent().getStringExtra("img_url"), fullPhotoView);
            haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
            expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
            String acceptId = getIntent().getStringExtra("expressAccept_id");
            if (acceptId != null) {
                histoicFlowPresenter.attach(this);
                RequestOperationUtil.setAddresseeHistoicFlowResult(acceptId, this, histoicFlowPresenter);
            }
        }

    }

    @OnClick({R.id.zoom, R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zoom:
                SkipIntentUtil.zoomTimerShaft(zoom, timelineRecyclerview);
                break;
            case R.id.return_arrows:
                returnSource(getIntent().getStringExtra("source"), getIntent().getStringExtra("home_source"));
                break;
        }
    }

    /**
     * 返回之前的页面
     *
     * @param source
     */
    private void returnSource(String source, String home_source) {
        if (!TextUtils.isEmpty(home_source)) {
            finish();
        } else {
            switch (source) {
                case "recipients_dispaly":
                    finish();
                    break;
                case "staff_home":
                    returnMipcaCapture(source);
                    break;
                case "administrator_home":
                    returnMipcaCapture(source);
                    break;
                case "administrator_recipients":
                    returnMipcaCapture(source);
                    break;
                case "staff_then_allocation":
                    SkipIntentUtil.skipIntent(this, ThenAllocationParcelActivity.class);
                    finish();
                    break;
            }
        }
    }

    /**
     * 返回二维码页面
     *
     * @param source
     */
    private void returnMipcaCapture(String source) {
        intent = new Intent(this, MipcaActivityCapture.class);
        intent.putExtra("source", source);
        startActivity(intent);
        finish();
    }

    @Override
    public void onHistoicFlowFinish(Object o) {
        HistoicFlowBean histoicFlowBean = (HistoicFlowBean) o;
        if (histoicFlowBean != null) {
            timelineRecyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
            timelineRecyclerview.setAdapter(new HistoicFlowAdapter(this, histoicFlowBean.getListTrajectory()));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            returnSource(getIntent().getStringExtra("source"), getIntent().getStringExtra("home_source"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        histoicFlowPresenter.dettach();
    }

}
