package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.adapter.HistoicFlowAdapter;
import com.example.me_jie.snailexpress_staff.bean.HistoicFlowBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.custom.FullPhotoView;
import com.example.me_jie.snailexpress_staff.custom.FullyLinearLayoutManager;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ARecipientsEnteringView;
import com.example.me_jie.snailexpress_staff.mvp.HistoicFlowView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.ARecipientsPresenter;
import com.example.me_jie.snailexpress_staff.presenter.HistoicFlowPresenter;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 站长收件 蜗牛站已收件 确认出库
 */
public class AdministratorRecipientsDomesticActivity extends AutoLayoutActivity implements ARecipientsEnteringView, HistoicFlowView {

    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.confirmation_inspection_btn)
    Button confirmationInspectionBtn;
    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.container_number)
    TextView containerNumber;
    @InjectView(R.id.title_tv)
    TextView titleTv;

    private Intent intent;
    private ARecipientsPresenter aRecipientsPresenter = new ARecipientsPresenter();//站长收件录入
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_recipients_domestic);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);
        intent = getIntent();
        if (intent != null) {
            SkipIntentUtil.setTitleAcceptOrPay(getIntent().getStringExtra("home_source"),titleTv);
            Constants.SOURCE = intent.getStringExtra("source");
            if (Constants.SOURCE != null) {
                RequestOperationUtil.setGlide(this, getIntent().getStringExtra("img_url"), fullPhotoView);
                haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
                expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
                containerNumber.setText("货柜编号：" + intent.getStringExtra("containerNO"));
                String acceptId = getIntent().getStringExtra("expressAccept_id");
                if (acceptId != null) {
                    histoicFlowPresenter.attach(this);
                    RequestOperationUtil.setAddresseeHistoicFlowResult(acceptId, this, histoicFlowPresenter);
                }
            }
        }

    }

    /**
     * 收件确认出库
     */
    private void requestRecipientsDomestic(String json) {
        new OkHttpResolve(this);
        aRecipientsPresenter.attach(this);
        aRecipientsPresenter.postGodownEntryRecipientsResult(json, this, lazyLoadProgressDialog);
    }


    @OnClick({R.id.zoom, R.id.return_arrows, R.id.confirmation_inspection_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zoom:
                SkipIntentUtil.zoomTimerShaft(zoom, timelineRecyclerview);
                break;
            case R.id.return_arrows:
                SkipIntentUtil.sourceAcceptReturn(this, getIntent().getStringExtra("source"));
                break;
            case R.id.confirmation_inspection_btn:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                HashMap<String, String> params = new HashMap<>();
                params.put("id", getIntent().getStringExtra("expressAccept_id"));
                params.put("expressParcel.id", getIntent().getStringExtra("expressParcel_id"));
                params.put("act.taskId", getIntent().getStringExtra("act_taskId"));
                params.put("act.procInsId", getIntent().getStringExtra("act_procInsId"));
                JSONObject jsonObject = new JSONObject(params);
                requestRecipientsDomestic(jsonObject.toString());
                break;
        }

    }

    @Override
    public void onASaveRecipientsFinish(Object o) {
        RecipientsBean recipientsBean = (RecipientsBean) o;
        if (recipientsBean != null) {
            SkipIntentUtil.successfulResultAccept(getIntent().getStringExtra("home_source"), this, intent);
        }
    }

    @Override
    public void onAAddressByPhoneOrParcelNoFinish(Object o) {

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
            SkipIntentUtil.sourceAcceptReturn(this, getIntent().getStringExtra("source"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        aRecipientsPresenter.dettach();
        histoicFlowPresenter.dettach();
    }
}
