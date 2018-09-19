package com.example.me_jie.snailexpress_staff.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.SendDetailsBean;
import com.example.me_jie.snailexpress_staff.bean.SendDisplayBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.SendDisplayView;
import com.example.me_jie.snailexpress_staff.activity.BaseActivity;
import com.example.me_jie.snailexpress_staff.net.NetBroadcastReceiver;
import com.example.me_jie.snailexpress_staff.net.NetUtil;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.SendDisplayPresenter;
import com.example.me_jie.snailexpress_staff.utils.AcquisitionTimeUtil;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.MoveLocationUtil;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 寄快递 列表    寄件列表
 */
public class SendExpressageActivity extends BaseActivity implements SendDisplayView, OnDateSetListener {
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.no_content)
    TextView noContentTx;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.select_datetime)
    RelativeLayout selectDatetime;
    @InjectView(R.id.datetime)
    TextView datetime;
    @InjectView(R.id.left_arrows)
    ImageView leftArrows;
    @InjectView(R.id.right_arrows)
    ImageView rightArrows;
    @InjectView(R.id.receive_parcel_count)
    TextView receiveParcelCount;
    @InjectView(R.id.send_display_rv)
    RecyclerView sendDisplayRv;
    @InjectView(R.id.application_by_btn)
    Button applicationByBtn;

    private CommonAdapter<SendDisplayBean.PageBean.ListBean> commonAdapter;
    private List<SendDisplayBean.PageBean.ListBean> sendBeanList;

    private SendDisplayPresenter sendDisplayPresenter = new SendDisplayPresenter();//寄件列表
    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    TimePickerDialog mDialogYearMonthDay;//时间选择器
    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private String specifiedDay;//指定时间

    private Intent intent;

    private BroadcastReceiver receiver;//获取广播对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_expressage);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        receiver = new NetBroadcastReceiver();
        StatusBarUtil.registerBroadrecevicer(this, receiver);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        //启动时判断网络状态
        Constants.NETCONNECT = this.isNetConnect();
        if (Constants.NETCONNECT == false) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
        } else {
            Constants.NETCONNECT = true;
        }
        initViews();


    }

    /**
     * 初始化寄件列表信息
     */
    private void requestSendDisplayResult(String createDate) {
        JSONObject json = new JSONObject();
        try {
            JSONObject page = new JSONObject();
            page.put("pageNo", pageno);
            page.put("pageSize", pagesize);
            json.put("pageInfo", page);

            try {
                json.put("createDate", AcquisitionTimeUtil.stringToLong(createDate, "yyyy-MM-dd"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new OkHttpResolve(this);
        sendDisplayPresenter.attach(this);
        sendDisplayPresenter.postJsonDisplayListResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 初始化寄件列表信息Item详情
     */
    private void requestRecipientsMessageResult(String id) {

        new OkHttpResolve(this);
        sendDisplayPresenter.attach(this);
        sendDisplayPresenter.postJsonDisplayResult(id, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        specifiedDay = AcquisitionTimeUtil.getCurrentDate();
        datetime.setText(specifiedDay);
        //时间选择器
        setTimePickerDialog();

        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        requestSendDisplayResult(specifiedDay);

        //添加头部
        SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setHeaderHeight(50);
        //添加底部
        refreshLayout.setOverScrollBottomShow(false);
        refreshLayout.setAutoLoadMore(true);
        linearLayoutManager = new LinearLayoutManager(this);
        sendDisplayRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        commonAdapter = new CommonAdapter<SendDisplayBean.PageBean.ListBean>(this, R.layout.item_send_expressgae, sendBeanList) {
            @Override
            protected void convert(ViewHolder holder, final SendDisplayBean.PageBean.ListBean sendDisplayBean, final int position) {
                RequestOperationUtil.setAdpaterSender(sendDisplayBean, holder);
            }
        };
        sendDisplayRv.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestRecipientsMessageResult("{\"senderId\":\"" + sendBeanList.get(position).getId() + "\"}");
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageno = 1;
                requestSendDisplayResult(specifiedDay);
                SkipIntentUtil.rvRefreshTimeout(SendExpressageActivity.this, refreshLayout, 0);

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                requestSendDisplayResult(specifiedDay);
                SkipIntentUtil.rvRefreshTimeout(SendExpressageActivity.this, refreshLayout, 0);
            }
        });
        SkipIntentUtil.rvRefreshSuccess(commonAdapter, refreshLayout);
    }

    @OnClick({R.id.application_by_btn,R.id.return_arrows, R.id.select_datetime, R.id.left_arrows, R.id.right_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.application_by_btn:
                intent = new Intent(this,SendCasesAffirmActivity.class);
                intent.putExtra("source","adsense_sent_item");
                startActivity(intent);
                break;
            case R.id.return_arrows:
                returnSkip();
                break;
            case R.id.select_datetime:
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.left_arrows:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                pageno = 1;
                specifiedDay = AcquisitionTimeUtil.getSpecifiedDayBefore(datetime.getText().toString());
                requestSendDisplayResult(specifiedDay);
                break;
            case R.id.right_arrows:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                pageno = 1;
                specifiedDay = AcquisitionTimeUtil.getSpecifiedDayAfter(datetime.getText().toString());
                requestSendDisplayResult(specifiedDay);
                break;
        }
    }

    /**
     * 返回扫码页面
     */
    private void returnSkip() {
        intent = new Intent(this, MipcaActivityCapture.class);
        intent.putExtra("source", "administrator_took");
        startActivity(intent);
        finish();
    }

    /**
     * 日期选择器
     */
    private void setTimePickerDialog() {
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(this)
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCancelStringId("关闭")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setWheelItemTextSize(16)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.cl_999999))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.cl_333333))
                .setThemeColor(getResources().getColor(R.color.cl_6fd1c8))
                .build();
    }

    /**
     * 获取时间
     *
     * @param timePickerDialog
     * @param millseconds
     */
    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long millseconds) {
        String textDate = getDateToString(millseconds);
        datetime.setText(textDate);
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        pageno = 1;
        specifiedDay = textDate;
        requestSendDisplayResult(specifiedDay);
    }

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    @Override
    public void onSendDisplayListFinish(Object o) {
        SendDisplayBean sendDisplayBean = (SendDisplayBean) o;
        if (sendDisplayBean != null) {
            if (sendBeanList == null) {
                sendBeanList = new ArrayList<>();
            }
            datetime.setText(specifiedDay);
            receiveParcelCount.setText("(本日共收包裹" + sendDisplayBean.getPage().getCount() + "件)");
            List<SendDisplayBean.PageBean.ListBean> list = sendDisplayBean.getPage().getList();
            if (sendDisplayBean.getPage().getPageNo() == 1) {
                if (sendDisplayBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContentTx.setVisibility(View.GONE);
                    sendBeanList.clear();
                    sendBeanList = list;
                    setUpParameters();
                    if (sendDisplayBean.getPage().getCount() > 10) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    noContentTx.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }
            } else {
                if (list != null) {
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, sendDisplayRv, sendBeanList.size());
                    for (int i = 0; i < list.size(); i++) {
                        sendBeanList.add(list.get(i));
                    }
                    setUpParameters();
                    if (list.size() == 10) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "数据加载完成！");
                    refreshLayout.setEnableLoadmore(false);
                    refreshLayout.setAutoLoadMore(false);

                }
            }
        }
    }

    @Override
    public void onSendDisplayBeanFinish(Object o) {
        SendDetailsBean sendDetailsBean = (SendDetailsBean) o;
        if (sendDetailsBean != null) {
            String taskKey = sendDetailsBean.getMapsender().getTaskKey();
            if (taskKey != null) {
                switch (taskKey) {
                    case "stationmasterSweep":
                        intent = new Intent(this, AdministratorExpressDetailsActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "stationmasterSweep");
                        intent.putExtra("workstationId", sendDetailsBean.getMapsender().getWorkstationId());
                        startActivity(intent);
                        break;
                    case "outWorkstation":
                        intent = new Intent(this, ReceiveExpressageThreeActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "outWorkstation");
                        startActivity(intent);
                        break;
                    case "weighingDetermination2":
                        intent = new Intent(this, StaffExpressageDetailsActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "weighingDetermination2");
                        RequestOperationUtil.intentSendWeigh(sendDetailsBean, intent);
                        startActivity(intent);
                        break;
                    case "0":
                        intent = new Intent(this, ReceiveExpressageThreeActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "0");
                        startActivity(intent);
                        break;
                }
            }
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
            SkipIntentUtil.rvRefreshTimeout(SendExpressageActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT = false) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestSendDisplayResult(specifiedDay);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            returnSkip();
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
        sendDisplayPresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}
