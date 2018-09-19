package com.example.me_jie.snailexpress_staff.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsDisplayBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.RecipientsDisplayView;
import com.example.me_jie.snailexpress_staff.activity.BaseActivity;
import com.example.me_jie.snailexpress_staff.net.NetBroadcastReceiver;
import com.example.me_jie.snailexpress_staff.net.NetUtil;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.RecipientsDisplayPresenter;
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
 * 已分配包裹
 */
public class ThenAllocationParcelActivity extends BaseActivity implements RecipientsDisplayView {

    @InjectView(R.id.then_allocation_parcel_rv)
    RecyclerView thenAllocationParcelRv;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.no_content)
    TextView noContent;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;

    private CommonAdapter<RecipientsDisplayBean.PageBean.ListBean> commonAdapter;
    private List<RecipientsDisplayBean.PageBean.ListBean> recipientsBeenList;
    private RecipientsDisplayPresenter recipientsDisplayPresenter = new RecipientsDisplayPresenter();//收件列表
    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private Intent intent;
    private BroadcastReceiver receiver;//获取广播对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_then_allocation_parcel);
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
            Constants.NETCONNECT =true;
        }
        initViews();
    }

    /**
     * 初始化收件列表信息
     */
    private void requestRecipientsDisplayResult() {
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
     * 初始化布局
     */
    private void initViews() {
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        requestRecipientsDisplayResult();

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
        thenAllocationParcelRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        commonAdapter = new CommonAdapter<RecipientsDisplayBean.PageBean.ListBean>(this, R.layout.item_then_allocation_parcel, recipientsBeenList) {
            @Override
            protected void convert(ViewHolder holder, final RecipientsDisplayBean.PageBean.ListBean recipientsDisplayBean, final int position) {
                RecipientsDisplayBean.PageBean.ListBean.ExpressParcelBean expressParcel = recipientsDisplayBean.getExpressParcel();
                holder.setText(R.id.carrier_source_tv, "承运来源：" + expressParcel.getShipperCode());
                holder.setText(R.id.tracking_number_tv, "快递单号：" + expressParcel.getLgisticCode());
                holder.setText(R.id.datetime, recipientsDisplayBean.getCreateDate());
                RecipientsDisplayBean.PageBean.ListBean.ExpressParcelBean.ReceiverBean receiver = expressParcel.getReceiver();
                if (receiver != null) {
                    holder.setText(R.id.sir_name, receiver.getName());
                    String provinceName = receiver.getProvinceName();
                    String cityName = receiver.getCityName();
                    String expAreaName = receiver.getExpAreaName();
                    String address = receiver.getAddress();
                    if (provinceName.equals(cityName)) {
                        holder.setText(R.id.sir_address, cityName + expAreaName + address);
                    } else {
                        holder.setText(R.id.sir_address, provinceName + cityName + expAreaName + address);
                    }
                    holder.setText(R.id.phone_number_tv, receiver.getMobile());
                }
                holder.setText(R.id.staff_state, recipientsDisplayBean.getBusinessStatus());
                holder.setText(R.id.contact_information_tv, recipientsDisplayBean.getRemarks());

                holder.setBackgroundRes(R.id.ll_content, R.drawable.customer_selector);
            }
        };
        thenAllocationParcelRv.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestRecipientsMessageResult("{\"id\":\"" + recipientsBeenList.get(position).getId() + "\"}");
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
                requestRecipientsDisplayResult();
                SkipIntentUtil.rvRefreshTimeout(ThenAllocationParcelActivity.this, refreshLayout, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                requestRecipientsDisplayResult();
                SkipIntentUtil.rvRefreshTimeout(ThenAllocationParcelActivity.this, refreshLayout, 0);

            }
        });
        SkipIntentUtil.rvRefreshSuccess(commonAdapter,refreshLayout);
    }


    @Override
    public void onARecipientsDisplayListFinish(Object o) {
        RecipientsDisplayBean recipientsDisplayBean = (RecipientsDisplayBean) o;
        if (recipientsDisplayBean != null) {
            if (recipientsBeenList == null) {
                recipientsBeenList = new ArrayList<>();
            }
            List<RecipientsDisplayBean.PageBean.ListBean> list = recipientsDisplayBean.getPage().getList();
            if (recipientsDisplayBean.getPage().getPageNo() == 1) {
                if (recipientsDisplayBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
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
                    noContent.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }
            } else {
                if (list != null) {
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, thenAllocationParcelRv, recipientsBeenList.size());
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
                    SkipIntentUtil.toastShow(this, "数据加载完成！");
                    refreshLayout.setEnableLoadmore(false);
                    refreshLayout.setAutoLoadMore(false);

                }
            }
        }
    }

    @Override
    public void onARecipientsDisplayBeanFinish(Object o) {
        RecipientsBean recipientsBean = (RecipientsBean) o;
        if (recipientsBean != null) {
            String businessStatus = recipientsBean.getExpressAccept().getBusinessStatus();
            String taskDefKey = recipientsBean.getAct().getTaskDefKey();
            if (taskDefKey != null && taskDefKey.equals("doCourierInputInfo")) {
                intentRequiredParameter(recipientsBean, "doCourierInputInfo", StaffInformationCollectionActivity.class);
                startActivity(intent);
            } else {
                if (businessStatus != null) {
                    if (!businessStatus.equals("用户已收件")) {
                        intentRequiredParameter(recipientsBean, "then_allocation_rq", StaffUserQRCodeActivity.class);
                        startActivity(intent);
                    } else {
                        intentRequiredParameter(recipientsBean, "then_allocation_parcel", StaffExpressageMessageActivity.class);
                        startActivity(intent);
                    }
                } else {
                    intentRequiredParameter(recipientsBean, "then_allocation_rq", StaffUserQRCodeActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    /**
     * 员工跳转必要参数
     *
     * @param recipientsBean
     * @param source
     */
    private void intentRequiredParameter(RecipientsBean recipientsBean, String source, Class cl) {
        intent = new Intent(this, cl);
        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, source);
        RecipientsBean.ExpressAcceptBean.ExpressParcelBean.ReceiverBean receiver = recipientsBean.getExpressAccept().getExpressParcel().getReceiver();
        if (receiver != null) {
            String provinceName = receiver.getProvinceName();
            String cityName = receiver.getCityName();
            String expAreaName = receiver.getExpAreaName();
            String address = receiver.getAddress();
            if (provinceName.equals(cityName)) {
                intent.putExtra("recipients", cityName + expAreaName + address);
            } else {
                intent.putExtra("recipients", provinceName + cityName + expAreaName + address);
            }
            intent.putExtra("receivername", receiver.getName());
        }
    }

    @OnClick({R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.skipIntent(this, StaffHomeActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
            SkipIntentUtil.rvRefreshTimeout(ThenAllocationParcelActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT == false) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestRecipientsDisplayResult();
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
        recipientsDisplayPresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}
