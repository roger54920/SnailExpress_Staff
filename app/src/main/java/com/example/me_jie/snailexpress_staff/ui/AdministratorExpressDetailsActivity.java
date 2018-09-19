package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.adapter.HistoicFlowAdapter;
import com.example.me_jie.snailexpress_staff.bean.GetWrokstationContainerBean;
import com.example.me_jie.snailexpress_staff.bean.HistoicFlowBean;
import com.example.me_jie.snailexpress_staff.bean.SendDetailsBean;
import com.example.me_jie.snailexpress_staff.custom.FullyLinearLayoutManager;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.GetWrokstationContainerView;
import com.example.me_jie.snailexpress_staff.mvp.HistoicFlowView;
import com.example.me_jie.snailexpress_staff.mvp.SendOperationView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.GetWrokstationContainerPresenter;
import com.example.me_jie.snailexpress_staff.presenter.HistoicFlowPresenter;
import com.example.me_jie.snailexpress_staff.presenter.SendOperationPresenter;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 快递详情   寄件确认入库
 */
public class AdministratorExpressDetailsActivity extends AutoLayoutActivity implements SendOperationView, HistoicFlowView ,GetWrokstationContainerView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.affirm_btn)
    Button affirmBtn;
    @InjectView(R.id.goodsname)
    TextView goodsname;
    @InjectView(R.id.express_sender)
    TextView expressSender;
    @InjectView(R.id.express_addressee)
    TextView expressAddressee;
    @InjectView(R.id.delivery_costs)
    TextView deliveryCosts;
    @InjectView(R.id.shipperCode)
    TextView shipperCode;
    @InjectView(R.id.weight)
    TextView weight;
    @InjectView(R.id.courier_number)
    TextView courierNumber;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.container_number)
    TextView containerNumber;
    @InjectView(R.id.select_container_number)
    RelativeLayout selectContainerNumber;
    private Intent intent;

    private SendOperationPresenter sendOperationPresenter = new SendOperationPresenter();//站长入库
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    private GetWrokstationContainerPresenter getWrokstationContainerPresenter = new GetWrokstationContainerPresenter();//获取货柜编号
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    //货柜编号选择
    private List<String> containerNumberItems = new ArrayList<>();
    private String containerNumberOptions;//货柜编号名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_express_details);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        intent = getIntent();
        Constants.SOURCE = intent.getStringExtra("source");
        if (Constants.SOURCE != null) {
            goodsname.setText(intent.getStringExtra("goodsname"));
            expressSender.setText(intent.getStringExtra("sender"));
            expressAddressee.setText(intent.getStringExtra("recipients"));
            shipperCode.setText("所选承运公司为：" + intent.getStringExtra("shipperCode"));
            weight.setText("包裹重量为：" + intent.getStringExtra("goodsWeight") + "kg");
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
     * 录入货柜
     *
     * @param json
     */
    private void getSendContainerResult(String json) {
        new OkHttpResolve(this);
        sendOperationPresenter.attach(this);
        sendOperationPresenter.postJsonSendOperationResult(json, this, lazyLoadProgressDialog);
    }
    /**
     * 获取货柜编号
     */
    private void requestGetWrokstationContainer(String json) {
        new OkHttpResolve(this);
        getWrokstationContainerPresenter.attach(this);
        getWrokstationContainerPresenter.postGetWrokstationContainerResult(json, this, lazyLoadProgressDialog);
    }
    @OnClick({R.id.return_arrows, R.id.affirm_btn,R.id.zoom,R.id.select_container_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_container_number:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestGetWrokstationContainer("{\"workstationId\":\"" + getIntent().getStringExtra("workstationId") + "\"}");
                break;
            case R.id.return_arrows:
               SkipIntentUtil.sourceSenderReturn(this,getIntent().getStringExtra("source"));
                break;
            case R.id.affirm_btn:
                String containersNo = containerNumber.getText().toString();
                if (!containersNo.equals("请选择货柜编号")) {
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    HashMap params = new HashMap<>();
                    params.put("containerNO", containersNo);
                    params.put("senderId", getIntent().getStringExtra("senderId"));
                    params.put("taskId", getIntent().getStringExtra("taskId"));
                    params.put("procInsId", getIntent().getStringExtra("procInsId"));
                    getSendContainerResult(new JSONObject(params).toString());
                } else {
                    SkipIntentUtil.toastShow(this, "请选择货柜编号！");
                }
                break;
            case R.id.zoom:
               SkipIntentUtil.zoomTimerShaft(zoom,timelineRecyclerview);
                break;
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
    public void onHistoicFlowFinish(Object o) {
        HistoicFlowBean histoicFlowBean = (HistoicFlowBean) o;
        if (histoicFlowBean != null) {
            timelineRecyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
            timelineRecyclerview.setAdapter(new HistoicFlowAdapter(this, histoicFlowBean.getListTrajectory()));
        }
    }

    @Override
    public void onGetWrokstationContainerListFinish(Object o) {
        GetWrokstationContainerBean getWrokstationContainerBean = (GetWrokstationContainerBean) o;
        if(getWrokstationContainerBean!=null && getWrokstationContainerBean.getList()!=null){
            containerNumberItems = getWrokstationContainerBean.getList();
            showPickerView();

        }
    }
    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = containerNumberItems.get(options1);
                containerNumber.setTextColor(Color.parseColor("#333333"));
                containerNumber.setText(tx);
                containerNumberOptions = tx;
            }
        })

                .setTitleText("货柜编号选择")
                .setTitleSize(18)
                .setTitleColor(Color.parseColor("#333333"))
                .setTitleBgColor(Color.WHITE)
                .setCancelColor(Color.parseColor("#6fd1c8"))
                .setSubmitColor(Color.parseColor("#6fd1c8"))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(16)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(containerNumberItems);//一级选择器
        if (pvOptions != null) {
            for (int i = 0; i < containerNumberItems.size(); i++) {
                if (containerNumberItems.get(i).equals(containerNumberOptions)) {
                    pvOptions.setSelectOptions(i);
                }
            }
        }
        pvOptions.show();
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
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
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
