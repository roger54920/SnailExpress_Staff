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
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.adapter.HistoicFlowAdapter;
import com.example.me_jie.snailexpress_staff.bean.GetWrokstationContainerBean;
import com.example.me_jie.snailexpress_staff.bean.HistoicFlowBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.custom.FullPhotoView;
import com.example.me_jie.snailexpress_staff.custom.FullyLinearLayoutManager;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ARecipientsEnteringView;
import com.example.me_jie.snailexpress_staff.mvp.GetWrokstationContainerView;
import com.example.me_jie.snailexpress_staff.mvp.HistoicFlowView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.ARecipientsPresenter;
import com.example.me_jie.snailexpress_staff.presenter.GetWrokstationContainerPresenter;
import com.example.me_jie.snailexpress_staff.presenter.HistoicFlowPresenter;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 站长收件   确认入库
 */
public class AdministratorRecipientsActivity extends AutoLayoutActivity implements ARecipientsEnteringView, HistoicFlowView, GetWrokstationContainerView {

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
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.affirm_in_storage_btn)
    Button affirmInStorageBtn;
    @InjectView(R.id.container_number)
    TextView containerNumber;
    @InjectView(R.id.select_container_number)
    RelativeLayout selectContainerNumber;
    @InjectView(R.id.title_tv)
    TextView titleTv;
    private Intent intent;

    private ARecipientsPresenter aRecipientsPresenter = new ARecipientsPresenter();//站长收件录入
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    private GetWrokstationContainerPresenter getWrokstationContainerPresenter = new GetWrokstationContainerPresenter();//获取货柜编号
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    //货柜编号选择
    private List<String> containerNumberItems = new ArrayList<>();
    private String containerNumberOptions;//货柜编号名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoLayoutConifg.getInstance().useDeviceSize();
        setContentView(R.layout.activity_administrator_recipients);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);

        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        intent = getIntent();
        if (intent != null) {
            SkipIntentUtil.setTitleAcceptOrPay(getIntent().getStringExtra("home_source"), titleTv);
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

    /**
     * 收件确认入库
     */
    private void requestDataGodownEntry(String json) {
        new OkHttpResolve(this);
        aRecipientsPresenter.attach(this);
        aRecipientsPresenter.postGodownEntryRecipientsResult(json, this, lazyLoadProgressDialog);
    }

    /**
     * 获取货柜编号
     */
    private void requestGetWrokstationContainer(String json) {
        new OkHttpResolve(this);
        getWrokstationContainerPresenter.attach(this);
        getWrokstationContainerPresenter.postGetWrokstationContainerResult(json, this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.zoom, R.id.return_arrows, R.id.affirm_in_storage_btn, R.id.select_container_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_container_number:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestGetWrokstationContainer("{\"workstationId\":\"" + getIntent().getStringExtra("workstationId") + "\"}");
                break;
            case R.id.zoom:
                SkipIntentUtil.zoomTimerShaft(zoom, timelineRecyclerview);
                break;
            case R.id.return_arrows:
                SkipIntentUtil.sourceAcceptReturn(this, getIntent().getStringExtra("source"));
                break;
            case R.id.affirm_in_storage_btn:
                String containersNo = containerNumber.getText().toString();
                if (!containersNo.equals("请选择货柜编号")) {
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    HashMap<String, String> params = new HashMap<>();
                    params.put("id", getIntent().getStringExtra("expressAccept_id"));
                    params.put("expressParcel.id", getIntent().getStringExtra("expressParcel_id"));
                    params.put("act.taskId", getIntent().getStringExtra("act_taskId"));
                    params.put("act.procInsId", getIntent().getStringExtra("act_procInsId"));
                    params.put("containerNO", containersNo);
                    JSONObject jsonObject = new JSONObject(params);
                    requestDataGodownEntry(jsonObject.toString());
                } else {
                    SkipIntentUtil.toastShow(this, "请选择货柜编号！");
                }
                break;
        }
    }

    /**
     * 确认入库
     *
     * @param o
     */
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
    public void onGetWrokstationContainerListFinish(Object o) {
        GetWrokstationContainerBean getWrokstationContainerBean = (GetWrokstationContainerBean) o;
        if (getWrokstationContainerBean != null && getWrokstationContainerBean.getList() != null) {
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
            SkipIntentUtil.sourceAcceptReturn(this, getIntent().getStringExtra("source"));
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
        aRecipientsPresenter.dettach();
        histoicFlowPresenter.dettach();
        getWrokstationContainerPresenter.dettach();
    }

}
