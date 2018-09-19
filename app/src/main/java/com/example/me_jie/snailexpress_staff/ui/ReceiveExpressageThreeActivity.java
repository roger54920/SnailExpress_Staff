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
import com.example.me_jie.snailexpress_staff.bean.SendDetailsBean;
import com.example.me_jie.snailexpress_staff.custom.FullyLinearLayoutManager;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.HistoicFlowView;
import com.example.me_jie.snailexpress_staff.mvp.SendOperationView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.HistoicFlowPresenter;
import com.example.me_jie.snailexpress_staff.presenter.SendOperationPresenter;
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

import static com.example.me_jie.snailexpress_staff.R.id.courier_number;

/**
 * 收快递 确认出库
 */
public class ReceiveExpressageThreeActivity extends AutoLayoutActivity implements SendOperationView, HistoicFlowView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.goodsname)
    TextView goodsname;
    @InjectView(R.id.sender)
    TextView sender;
    @InjectView(R.id.addressee)
    TextView addressee;
    @InjectView(R.id.weight)
    TextView weight;
    @InjectView(R.id.delivery_costs)
    TextView deliveryCosts;
    @InjectView(R.id.snail_number)
    TextView snailNumber;
    @InjectView(courier_number)
    TextView courierNumber;
    @InjectView(R.id.shipperCode)
    TextView shipperCode;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.affirm_stock_btn)
    Button affirmStockBtn;
    @InjectView(R.id.zoom)
    TextView zoom;
    private Intent intent;

    private SendOperationPresenter sendOperationPresenter = new SendOperationPresenter();//寄件确认出库
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_expressage_three);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
//        receiveExpressageRv.setFocusable(false);
//        myScrollview.smoothScrollTo(0, 0);
//        setTimelineRecyclerview();

        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        intent = getIntent();
        Constants.SOURCE = intent.getStringExtra("source");
        if (Constants.SOURCE != null) {
            switch (Constants.SOURCE) {
                case "outWorkstation":
                    myScrollview.setPadding(0,0,0,96);
                    affirmStockBtn.setVisibility(View.VISIBLE);
                    break;
                case "mipca_capture_outWorkstation":
                    myScrollview.setPadding(0,0,0,96);
                    affirmStockBtn.setVisibility(View.VISIBLE);
                    break;
                case "0":
                    myScrollview.setPadding(0,0,0,0);
                    affirmStockBtn.setVisibility(View.GONE);
                    break;
                case "mipca_capture_0":
                    myScrollview.setPadding(0,0,0,0);
                    affirmStockBtn.setVisibility(View.GONE);
                    break;
            }
            goodsname.setText(intent.getStringExtra("goodsname"));
            sender.setText(intent.getStringExtra("sender"));
            addressee.setText(intent.getStringExtra("recipients"));
            shipperCode.setText("所选承运公司为：" + intent.getStringExtra("shipperCode"));
            String containerNO = intent.getStringExtra("containerNO");
            if(!containerNO.equals("")){
                snailNumber.setText("蜗牛站货柜编号：" + containerNO);
            }else{
                snailNumber.setVisibility(View.GONE);
            }
            String goodsWeight = intent.getStringExtra("goodsWeight");
            String cost = intent.getStringExtra("cost");
            if (goodsWeight.equals("0") && cost.equals("0")) {
                deliveryCosts.setVisibility(View.GONE);
                weight.setText("您的包裹未称重");
            } else {
                deliveryCosts.setText("您的包裹快递费为：" + cost + "元");
                weight.setText("您的包裹重量为：" + goodsWeight + "kg");
            }
            String lgisticCode = intent.getStringExtra("lgisticCode");
            if (!lgisticCode.equals("0")) {
                courierNumber.setVisibility(View.VISIBLE);
                courierNumber.setText("快递单号：" + lgisticCode);
            } else {
                courierNumber.setVisibility(View.GONE);
            }
            String senderId = getIntent().getStringExtra("senderId");
            if (senderId != null) {
                histoicFlowPresenter.attach(this);
                RequestOperationUtil.setSendHistoicFlowResult(senderId, this, histoicFlowPresenter);
            }

        }
    }

    /**
     * 初始化寄件出库
     */
    private void requestSendOutputResult(String json) {
        new OkHttpResolve(this);
        sendOperationPresenter.attach(this);
        sendOperationPresenter.postJsonSendOperationResult(json.toString(), this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.affirm_stock_btn, R.id.zoom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.sourceSenderReturn(this,getIntent().getStringExtra("source"));
                break;
            case R.id.affirm_stock_btn:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                HashMap<String, String> params = new HashMap<>();
                params.put("senderId", getIntent().getStringExtra("senderId"));
                params.put("taskId", getIntent().getStringExtra("taskId"));
                params.put("procInsId", getIntent().getStringExtra("procInsId"));
                JSONObject jsonObject = new JSONObject(params);
                requestSendOutputResult(jsonObject.toString());
                break;
            case R.id.zoom:
                SkipIntentUtil.zoomTimerShaft(zoom,timelineRecyclerview);
                break;
        }
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
    public void onSendOperationFinish(Object o) {
        SendDetailsBean sendDetailsBean = (SendDetailsBean) o;
        if (sendDetailsBean != null) {
            SkipIntentUtil.skipIntent(this, SendExpressageActivity.class);
            finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SkipIntentUtil.sourceSenderReturn(this,getIntent().getStringExtra("source"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        sendOperationPresenter.dettach();
        histoicFlowPresenter.dettach();
    }
}

