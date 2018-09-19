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
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.MoveLocationUtil;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 员工/保安寄收包裹 列表
 */
public class StaffCourierServiceParcelActivity extends BaseActivity implements SendDisplayView {

    @InjectView(R.id.staff_courier_service_rv)
    RecyclerView staffCourierServiceRv;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.no_content)
    TextView noContent;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.application_by_btn)
    Button applicationByBtn;
    private CommonAdapter<SendDisplayBean.PageBean.ListBean> commonAdapter;
    private List<SendDisplayBean.PageBean.ListBean> sendDisplayBeanList;
    private SendDisplayPresenter sendDisplayPresenter = new SendDisplayPresenter();//收件列表
    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private Intent intent;
    private BroadcastReceiver receiver;//获取广播对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_courier_service_parcel);
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
     * 初始化收件列表信息
     */
    private void requestSendDisplayResult() {
        JSONObject json = new JSONObject();
        try {
            JSONObject page = new JSONObject();
            page.put("pageNo", pageno);
            page.put("pageSize", pagesize);
            json.put("pageInfo", page);

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
    private void requestSendMessageResult(String id) {
        new OkHttpResolve(this);
        sendDisplayPresenter.attach(this);
        sendDisplayPresenter.postJsonDisplayResult(id, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        requestSendDisplayResult();

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
        staffCourierServiceRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        commonAdapter = new CommonAdapter<SendDisplayBean.PageBean.ListBean>(this, R.layout.item_staff_courier_service_parcel, sendDisplayBeanList) {
            @Override
            protected void convert(ViewHolder holder, final SendDisplayBean.PageBean.ListBean sendDisplayBean, final int position) {
                holder.setText(R.id.shipperCode, "承运来源：" + sendDisplayBean.getExpressParcel().getShipperCode());
                holder.setText(R.id.createDate, sendDisplayBean.getCreateDate());
                SendDisplayBean.PageBean.ListBean.ExpressParcelBean.ReceiverBean receiver = sendDisplayBean.getExpressParcel().getReceiver();
                holder.setText(R.id.sir_name, receiver.getName());
                holder.setText(R.id.sir_address, receiver.getProvinceName() + receiver.getCityName() + receiver.getExpAreaName() + receiver.getAddress());

                String lgisticcode = sendDisplayBean.getExpressParcel().getLgisticCode();
                if (!lgisticcode.equals("0")) {
                    holder.setText(R.id.lgisticcode, "快递单号：" + lgisticcode).setVisible(R.id.lgisticcode, true);
                } else {
                    holder.setVisible(R.id.lgisticcode, false);
                }
                holder.setText(R.id.send_courier_service_parcel_state, sendDisplayBean.getBusinessStatus());
                holder.setText(R.id.send_operation_procedure, sendDisplayBean.getRemarks());

                holder.setBackgroundRes(R.id.ll_content, R.drawable.customer_selector);
            }
        };
        staffCourierServiceRv.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestSendMessageResult("{\"senderId\":\"" + sendDisplayBeanList.get(position).getId() + "\"}");
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
                requestSendDisplayResult();
                SkipIntentUtil.rvRefreshTimeout(StaffCourierServiceParcelActivity.this, refreshLayout, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                requestSendDisplayResult();
                SkipIntentUtil.rvRefreshTimeout(StaffCourierServiceParcelActivity.this, refreshLayout, 0);
            }
        });

        SkipIntentUtil.rvRefreshSuccess(commonAdapter, refreshLayout);
    }

    @Override
    public void onSendDisplayListFinish(Object o) {
        SendDisplayBean sendDisplayBean = (SendDisplayBean) o;
        if (sendDisplayBean != null) {
            if (sendDisplayBeanList == null) {
                sendDisplayBeanList = new ArrayList<>();
            }
            List<SendDisplayBean.PageBean.ListBean> list = sendDisplayBean.getPage().getList();
            if (sendDisplayBean.getPage().getPageNo() == 1) {
                if (sendDisplayBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    sendDisplayBeanList.clear();
                    sendDisplayBeanList = list;
                    setUpParameters();
                    if (sendDisplayBean.getPage().getCount() > 10) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    noContent.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }
            } else {
                if (list != null) {
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, staffCourierServiceRv, sendDisplayBeanList.size());
                    for (int i = 0; i < list.size(); i++) {
                        sendDisplayBeanList.add(list.get(i));
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
            String taskDefKey = sendDetailsBean.getMapsender().getTaskKey();
            String twodcode = sendDetailsBean.getMapsender().getTwodcode();
            if (twodcode != null && twodcode.equals("1")) {
                //扫码页面
                intnetRequiredParameter(sendDetailsBean, "courier_service_parcel_rq", StaffUserQRCodeActivity.class);
                startActivity(intent);
            } else {
                if (taskDefKey != null) {
                    switch (taskDefKey) {
                        case "weighingDetermination"://称重
                            intnetRequiredParameter(sendDetailsBean, "weighingDetermination", StaffExpressageDetailsActivity.class);
                            startActivity(intent);
                            break;
                        case "0"://详情
                            intnetRequiredParameter(sendDetailsBean, "0", StaffExpressageDetailsActivity.class);
                            intent.putExtra("cost", sendDetailsBean.getMapsender().getCost());
                            intent.putExtra("weight", sendDetailsBean.getMapsender().getGoodsWeight());
                            startActivity(intent);
                            break;
                    }
                }
            }

        }
    }

    /**
     * 跳转必要参数
     *
     * @param sendDetailsBean
     * @param source
     */
    private void intnetRequiredParameter(SendDetailsBean sendDetailsBean, String source, Class cl) {
        intent = new Intent(this, cl);
        SendDetailsBean.MapsenderBean mapsender = sendDetailsBean.getMapsender();

        intent.putExtra("source", source);
        intent.putExtra("senderId", sendDetailsBean.getMapsender().getSenderId());
        intent.putExtra("taskId", mapsender.getTaskId());
        intent.putExtra("procInsId", mapsender.getProcInsId());

        RequestOperationUtil.intentSendWeigh(sendDetailsBean, intent);

    }

    @OnClick({R.id.return_arrows,R.id.application_by_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.skipIntent(this, StaffHomeActivity.class);
                finish();
                break;
            case R.id.application_by_btn:
                intent = new Intent(this,SendCasesAffirmActivity.class);
                intent.putExtra("source","staff_send");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
            SkipIntentUtil.rvRefreshTimeout(StaffCourierServiceParcelActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT == false) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestSendDisplayResult();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SkipIntentUtil.skipIntent(this, StaffHomeActivity.class);
            finish();
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
