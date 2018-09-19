package com.example.me_jie.snailexpress_staff.ui;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.ExpressRobSingleBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsDisplayBean;
import com.example.me_jie.snailexpress_staff.bean.SendDisplayBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.HomeShowDataMoreView;
import com.example.me_jie.snailexpress_staff.activity.BaseActivity;
import com.example.me_jie.snailexpress_staff.net.NetBroadcastReceiver;
import com.example.me_jie.snailexpress_staff.net.NetUtil;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.HomeShowDataMorePresenter;
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
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 站长首页点击更多
 */
public class HomeShowDataMoreActivity extends BaseActivity implements HomeShowDataMoreView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.home_show_data_title)
    TextView homeShowDataTitle;
    @InjectView(R.id.home_show_data_rv)
    RecyclerView homeShowDataRv;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.no_content)
    TextView noContent;

    //未分配任务
    private List<ExpressRobSingleBean.PageBean.ListBean> orderList;
    private CommonAdapter<ExpressRobSingleBean.PageBean.ListBean> commonOrderAdapter;
    //收件
    private List<RecipientsDisplayBean.PageBean.ListBean> acceptList;
    private CommonAdapter<RecipientsDisplayBean.PageBean.ListBean> commonAcceptAdapter;
    //寄件
    private List<SendDisplayBean.PageBean.ListBean> sendList;
    private CommonAdapter<SendDisplayBean.PageBean.ListBean> commonSendAdapter;


    private HomeShowDataMorePresenter homeShowDataMorePresenter = new HomeShowDataMorePresenter();//站长首页更多列表
    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private String types;//点击更多类型
    private BroadcastReceiver receiver;//获取广播对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_show_data_more);
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
     * 初始化更多列表信息
     */
    private void requestShowDataMoreResult() {
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
        homeShowDataMorePresenter.attach(this);
        switch (types) {
            case "order_more":
                homeShowDataMorePresenter.postJsonOrderListResult("{\"pageNo\":\"" + pageno + "\",\"pageSize\":\"" + pagesize + "\"}", this, lazyLoadProgressDialog);
                break;
            case "receive_expressAccept_more":
                homeShowDataMorePresenter.postJsonReceiveExpressAcceptListResult(json.toString(), this, lazyLoadProgressDialog);
                break;
            case "receive_expressSend_more":
                homeShowDataMorePresenter.postJsonReceiveExpressSendListResult(json.toString(), this, lazyLoadProgressDialog);
                break;
        }

    }

    /**
     * 初始化布局
     */
    private void initViews() {
        types = getIntent().getStringExtra("types");
        switch (types) {
            case "order_more":
                homeShowDataTitle.setText("未分配任务");
                noContent.setText("暂无未分配任务");
                break;
            case "receive_expressAccept_more":
                homeShowDataTitle.setText("已收件信息");
                noContent.setText("暂无已收件信息");
                break;
            case "receive_expressSend_more":
                homeShowDataTitle.setText("已寄件信息");
                noContent.setText("暂无已寄件信息");
                break;
        }

        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        requestShowDataMoreResult();

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
        homeShowDataRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        switch (types) {
            case "order_more":
                commonOrderAdapter = new CommonAdapter<ExpressRobSingleBean.PageBean.ListBean>(this, R.layout.item_home_more_order, orderList) {
                    @Override
                    protected void convert(ViewHolder holder, final ExpressRobSingleBean.PageBean.ListBean recipientsDisplayBean, final int position) {
                        RequestOperationUtil.setAdpaterOrder(recipientsDisplayBean, holder);
                    }
                };
                homeShowDataRv.setAdapter(commonOrderAdapter);
                break;
            case "receive_expressAccept_more":
                commonAcceptAdapter = new CommonAdapter<RecipientsDisplayBean.PageBean.ListBean>(this, R.layout.item_data_more_accept, acceptList) {
                    @Override
                    protected void convert(ViewHolder holder, final RecipientsDisplayBean.PageBean.ListBean recipientsDisplayBean, final int position) {
                        RequestOperationUtil.setAdpaterAccept(recipientsDisplayBean, holder);
                    }
                };
                homeShowDataRv.setAdapter(commonAcceptAdapter);
                break;
            case "receive_expressSend_more":
                commonSendAdapter = new CommonAdapter<SendDisplayBean.PageBean.ListBean>(this, R.layout.item_home_more_send, sendList) {
                    @Override
                    protected void convert(ViewHolder holder, final SendDisplayBean.PageBean.ListBean sendDisplayBean, final int position) {
                        RequestOperationUtil.setAdpaterSender(sendDisplayBean, holder);
                    }
                };
                homeShowDataRv.setAdapter(commonSendAdapter);
                break;
        }
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageno = 1;
                requestShowDataMoreResult();
                SkipIntentUtil.rvRefreshTimeout(HomeShowDataMoreActivity.this, refreshLayout, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                requestShowDataMoreResult();
                SkipIntentUtil.rvRefreshTimeout(HomeShowDataMoreActivity.this, refreshLayout, 0);
            }
        });
        adapterNDSC();
    }

    /**
     * 刷新适配器
     */
    private void adapterNDSC() {
        switch (types) {
            case "order_more":
                SkipIntentUtil.rvRefreshSuccess(commonOrderAdapter,refreshLayout);
                break;
            case "receive_expressAccept_more":
                SkipIntentUtil.rvRefreshSuccess(commonAcceptAdapter,refreshLayout);
                break;
            case "receive_expressSend_more":
                SkipIntentUtil.rvRefreshSuccess(commonSendAdapter,refreshLayout);
                break;
        }
    }

    @OnClick(R.id.return_arrows)
    public void onViewClicked() {
        SkipIntentUtil.skipIntent(this, AdministratorHomeActivity.class);
        finish();
    }

    @Override
    public void onHomeShowDataOrderListFinish(Object o) {
        ExpressRobSingleBean expressRobSingleBean = (ExpressRobSingleBean) o;
        if (expressRobSingleBean != null) {
            if (orderList == null) {
                orderList = new ArrayList<>();
            }
            List<ExpressRobSingleBean.PageBean.ListBean> list = expressRobSingleBean.getPage().getList();
            if (expressRobSingleBean.getPage().getPageNo() == 1) {
                if (expressRobSingleBean.getPage().getCount() > 0 || list != null) {
                    homeShowDataRv.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    orderList.clear();
                    orderList = list;
                    setUpParameters();
                    if (expressRobSingleBean.getPage().getCount() > 10) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    noContent.setVisibility(View.VISIBLE);
                    homeShowDataRv.setVisibility(View.GONE);
                    refreshLayout.setEnableLoadmore(false);
                }
            } else {
                if (list != null) {
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, homeShowDataRv, orderList.size());
                    for (int i = 0; i < list.size(); i++) {
                        orderList.add(list.get(i));
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
    public void onHomeShowDataReceiveExpressAcceptListFinish(Object o) {
        RecipientsDisplayBean recipientsDisplayBean = (RecipientsDisplayBean) o;
        if (recipientsDisplayBean != null) {
            if (acceptList == null) {
                acceptList = new ArrayList<>();
            }
            List<RecipientsDisplayBean.PageBean.ListBean> list = recipientsDisplayBean.getPage().getList();
            if (recipientsDisplayBean.getPage().getPageNo() == 1) {
                if (recipientsDisplayBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    acceptList.clear();
                    acceptList = list;
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
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, homeShowDataRv, acceptList.size());
                    for (int i = 0; i < list.size(); i++) {
                        acceptList.add(list.get(i));
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
    public void onHomeShowDataReceiveExpressSendListFinish(Object o) {
        SendDisplayBean sendDisplayBean = (SendDisplayBean) o;
        if (sendDisplayBean != null) {
            if (sendList == null) {
                sendList = new ArrayList<>();
            }
            List<SendDisplayBean.PageBean.ListBean> list = sendDisplayBean.getPage().getList();
            if (sendDisplayBean.getPage().getPageNo() == 1) {
                if (sendDisplayBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    sendList.clear();
                    sendList = list;
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
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, homeShowDataRv, sendList.size());
                    for (int i = 0; i < list.size(); i++) {
                        sendList.add(list.get(i));
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
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
            SkipIntentUtil.rvRefreshTimeout(HomeShowDataMoreActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT == false) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestShowDataMoreResult();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SkipIntentUtil.skipIntent(this, AdministratorHomeActivity.class);
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
        homeShowDataMorePresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}
