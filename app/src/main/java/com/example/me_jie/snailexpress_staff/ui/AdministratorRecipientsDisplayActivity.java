package com.example.me_jie.snailexpress_staff.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsDisplayBean;
import com.example.me_jie.snailexpress_staff.custom.SwipeMenuLayout;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.AcceptPayListView;
import com.example.me_jie.snailexpress_staff.mvp.ConsumerPickUpView;
import com.example.me_jie.snailexpress_staff.mvp.RecipientsDisplayView;
import com.example.me_jie.snailexpress_staff.activity.BaseActivity;
import com.example.me_jie.snailexpress_staff.net.NetBroadcastReceiver;
import com.example.me_jie.snailexpress_staff.net.NetUtil;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.AcceptPayListPresenter;
import com.example.me_jie.snailexpress_staff.presenter.ConsumerPickUpPresenter;
import com.example.me_jie.snailexpress_staff.presenter.RecipientsDisplayPresenter;
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

import static com.example.me_jie.snailexpress_staff.R.id.cost;


/**
 * 站长收件展示
 */
public class AdministratorRecipientsDisplayActivity extends BaseActivity implements RecipientsDisplayView, ConsumerPickUpView, AcceptPayListView, OnDateSetListener {

    @InjectView(R.id.recipients_display_rv)
    RecyclerView recipientsDisplayRv;
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
    @InjectView(R.id.title_tv)
    TextView titleTv;
    private CommonAdapter<RecipientsDisplayBean.PageBean.ListBean> commonAdapter;
    private List<RecipientsDisplayBean.PageBean.ListBean> recipientsBeenList;


    private RecipientsDisplayPresenter recipientsDisplayPresenter = new RecipientsDisplayPresenter();//收件列表
    private ConsumerPickUpPresenter consumerPickUpPresenter = new ConsumerPickUpPresenter();//收件列表自提
    private AcceptPayListPresenter acceptPayListPresenter = new AcceptPayListPresenter();//代付货款列表
    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    TimePickerDialog mDialogYearMonthDay;//时间选择器
    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private String specifiedDay;//指定时间

    private Intent intent;
    private BroadcastReceiver receiver;//获取广播对象
    private String home_source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_recipients_display);
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
     * 初始化布局
     */
    private void initViews() {
        specifiedDay = AcquisitionTimeUtil.getCurrentDate();
        datetime.setText(specifiedDay);
        //时间选择器
        setTimePickerDialog();

        home_source = getIntent().getStringExtra("home_source");
        if (!TextUtils.isEmpty(home_source)) {
            titleTv.setText("代收货款");
            noContentTx.setText("暂无代收货款信息");
        } else {
            titleTv.setText("收件");
            noContentTx.setText("暂无收件信息");
        }
        initRequest();

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
        recipientsDisplayRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * 初始化请求数据
     */
    private void initRequest() {
        if (!TextUtils.isEmpty(home_source)) {
            lazyLoadProgressDialog.show();
            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
            requestAcceptPayListResult(specifiedDay);
        } else {
            lazyLoadProgressDialog.show();
            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
            requestRecipientsDisplayResult(specifiedDay);
        }
    }

    /**
     * 初始化收件列表信息
     */
    private void requestRecipientsDisplayResult(String createDate) {
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
        recipientsDisplayPresenter.attach(this);
        recipientsDisplayPresenter.postJsonDisplayListResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 初始化收件列表信息Item详情
     */
    private void requestRecipientsMessageResult(String id) {

        new OkHttpResolve(this);
        recipientsDisplayPresenter.attach(this);
        recipientsDisplayPresenter.postJsonDisplayResult(id, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化已注册用户自提
     */
    private void requestConsumerPickUpResult(String id) {
        new OkHttpResolve(this);
        consumerPickUpPresenter.attach(this);
        consumerPickUpPresenter.consumerPickUpResult(id, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化代收货款列表信息
     */
    private void requestAcceptPayListResult(String createDate) {
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
        acceptPayListPresenter.attach(this);
        acceptPayListPresenter.postJsonAcceptPayListResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        commonAdapter = new CommonAdapter<RecipientsDisplayBean.PageBean.ListBean>(this, R.layout.item_administrator_recipients_display, recipientsBeenList) {
            @Override
            protected void convert(ViewHolder holder, final RecipientsDisplayBean.PageBean.ListBean recipientsDisplayBean, final int position) {
                RequestOperationUtil.setAdpaterAccept(recipientsDisplayBean, holder);
                if (recipientsDisplayBean.getBusinessStatusCode() != null && recipientsDisplayBean.getBusinessStatusCode().equals("20") && recipientsDisplayBean.getIfConsumerPickUp() != null && recipientsDisplayBean.getIfConsumerPickUp().equals("0") && recipientsDisplayBean.getIfCancel()!=null && recipientsDisplayBean.getIfCancel().equals("0")) {
                    holder.setVisible(R.id.side_menu_ll, true);
                    //根据自己需求设置一些限制条件，比如这里设置了：IOS效果阻塞，item依次是左滑、右滑
                    ((SwipeMenuLayout) LayoutInflater.from(mContext).inflate(R.layout.item_administrator_recipients_display, null)).setIos(true).setLeftSwipe(position % 2 == 0 ? true : false);
                    //自提
                    holder.setOnClickListener(R.id.btn_deselect, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lazyLoadProgressDialog.show();
                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                            requestConsumerPickUpResult("{\"act.procInsId\":\"" + recipientsDisplayBean.getProcInsId() + "\",\"id\":\"" + recipientsDisplayBean.getId() + "\"}");
                        }
                    });
                } else {
                    holder.setVisible(R.id.side_menu_ll, false);
                }
                holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestRecipientsMessageResult("{\"id\":\"" + recipientsDisplayBean.getId() + "\"}");
                    }
                });

                if (!TextUtils.isEmpty(home_source)) {
                    holder.setText(R.id.cost, "代付金额：" +  recipientsDisplayBean.getExpressParcel().getCost()+"元").setVisible(cost, true);
                }else{
                    holder.setVisible(R.id.cost, false);
                }
            }
        };
        recipientsDisplayRv.setAdapter(commonAdapter);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageno = 1;
                refreshRV();
                SkipIntentUtil.rvRefreshTimeout(AdministratorRecipientsDisplayActivity.this, refreshLayout, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                refreshRV();
                SkipIntentUtil.rvRefreshTimeout(AdministratorRecipientsDisplayActivity.this, refreshLayout, 0);
            }
        });
        SkipIntentUtil.rvRefreshSuccess(commonAdapter, refreshLayout);
    }

    @OnClick({R.id.return_arrows, R.id.select_datetime, R.id.left_arrows, R.id.right_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                refreshRV();
                break;
            case R.id.right_arrows:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                pageno = 1;
                specifiedDay = AcquisitionTimeUtil.getSpecifiedDayAfter(datetime.getText().toString());
                refreshRV();
                break;
        }
    }
    /**
     * 刷新列表
     */
    private void refreshRV() {
        if (!TextUtils.isEmpty(home_source)) {
            requestAcceptPayListResult(specifiedDay);
        }else{
            requestRecipientsDisplayResult(specifiedDay);
        }
    }
    /**
     * 返回扫码页面
     */
    private void returnSkip() {
        if(!TextUtils.isEmpty(home_source)){
            SkipIntentUtil.skipIntent(this,AdministratorHomeActivity.class);
        }else{
            intent = new Intent(this, MipcaActivityCapture.class);
            intent.putExtra("source", "administrator_recipients");
            startActivity(intent);
        }
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
        refreshRV();
    }

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }


    @Override
    public void onARecipientsDisplayListFinish(Object o) {
        setRVFinish((RecipientsDisplayBean)o);
    }

    @Override
    public void onARecipientsDisplayBeanFinish(Object o) {
        RecipientsBean recipientsBean = (RecipientsBean) o;
        if (recipientsBean != null) {
            String taskDefKey = recipientsBean.getAct().getTaskDefKey();
            if (taskDefKey != null) {
                switch (taskDefKey) {
                    case "uploadPhoto":
                        intent = new Intent(this, AdministratorCameraActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, "acceptDisplay_uploadPhoto");
                        RequestOperationUtil.intentAcceptMessageEnteringParameters(recipientsBean.getExpressAccept(), intent);
                        break;
                    case "doStationmastervoiceInputInfo":
                        intent = new Intent(this, RecipientsMessageEnteringActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, "acceptDisplay_doStationmastervoiceInputInfo");
                        RequestOperationUtil.intentAcceptMessageEnteringParameters(recipientsBean.getExpressAccept(), intent);
                        break;
                    case "confirmInWorkstation":
                        intent = new Intent(this, AdministratorRecipientsActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, "acceptDisplay_confirmInWorkstation");
                        intent.putExtra("workstationId", recipientsBean.getExpressAccept().getWorkstation().getId());
                        break;
                    case "outWorkstation":
                        intent = new Intent(this, AdministratorRecipientsDomesticActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, "acceptDisplay_outWorkstation");
                        intent.putExtra("containerNO", recipientsBean.getExpressAccept().getContainerNO());
                        break;
                    case "outWorkstation2":
                        intent = new Intent(this, AdministratorRecipientsDomesticActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, "acceptDisplay_outWorkstation2");
                        intent.putExtra("containerNO", recipientsBean.getExpressAccept().getContainerNO());
                        break;
                    case "doCourierInputInfo2":
                        intent = new Intent(this, StaffInformationCollectionActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, "acceptDisplay_doCourierInputInfo2");
                        break;
                    default:
                        intent = new Intent(this, AdministratorRecipientsOverseaActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, "recipients_dispaly");
                        break;
                }
                SkipIntentUtil.isIntentHomeSource(home_source,intent);
                startActivity(intent);
            } else {
                intent = new Intent(this, AdministratorRecipientsOverseaActivity.class);
                RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, "recipients_dispaly");
                SkipIntentUtil.isIntentHomeSource(home_source,intent);
                startActivity(intent);
            }
        }
    }
    @Override
    public void onConsumerPickUpBeanFinish(Object o) {
        RecipientsBean recipientsBean = (RecipientsBean) o;
        if (recipientsBean != null) {
            intent = new Intent(this, AdministratorRecipientsDomesticActivity.class);
            RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, "acceptDisplay_confirmInWorkstation");
            intent.putExtra("containerNO", recipientsBean.getExpressAccept().getContainerNO());
            SkipIntentUtil.isIntentHomeSource(home_source,intent);
            startActivity(intent);
        }
    }

    @Override
    public void onAcceptPayListFinish(Object o) {
        setRVFinish((RecipientsDisplayBean)o);
    }
    /**
     * 请求完成设置列表参数
     *
     * @param recipientsDisplayBean
     */
    private void setRVFinish(RecipientsDisplayBean recipientsDisplayBean) {
        if (recipientsDisplayBean != null) {
            if (recipientsBeenList == null) {
                recipientsBeenList = new ArrayList<>();
            }
            datetime.setText(specifiedDay);
            receiveParcelCount.setText("(本日共收包裹" + recipientsDisplayBean.getPage().getCount() + "件)");
            List<RecipientsDisplayBean.PageBean.ListBean> list = recipientsDisplayBean.getPage().getList();
            if (recipientsDisplayBean.getPage().getPageNo() == 1) {
                if (recipientsDisplayBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContentTx.setVisibility(View.GONE);
                    recipientsBeenList.clear();
                    recipientsBeenList = list;
                    setUpParameters();
                    if (recipientsDisplayBean.getPage().getCount() > 10) {
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
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, recipientsDisplayRv, recipientsBeenList.size());
                    for (int i = 0; i < list.size(); i++) {
                        recipientsBeenList.add(list.get(i));
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
                    SkipIntentUtil.toastShow(AdministratorRecipientsDisplayActivity.this, "数据加载完成！");
                    refreshLayout.setEnableLoadmore(false);
                    refreshLayout.setAutoLoadMore(false);

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
            SkipIntentUtil.rvRefreshTimeout(AdministratorRecipientsDisplayActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT == false) {
                initRequest();
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
        recipientsDisplayPresenter.dettach();
        consumerPickUpPresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }

    }


}
