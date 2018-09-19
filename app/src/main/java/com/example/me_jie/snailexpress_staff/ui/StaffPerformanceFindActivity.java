package com.example.me_jie.snailexpress_staff.ui;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.StaffPerformanceFindBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.StaffPerformanceFindView;
import com.example.me_jie.snailexpress_staff.activity.BaseActivity;
import com.example.me_jie.snailexpress_staff.net.NetBroadcastReceiver;
import com.example.me_jie.snailexpress_staff.net.NetUtil;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.StaffPerformanceFindPresenter;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.MoveLocationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;
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
 * 员工  业绩查询
 */
public class StaffPerformanceFindActivity extends BaseActivity implements StaffPerformanceFindView {

    @InjectView(R.id.staff_performance_find_rv)
    RecyclerView staffPerformanceFindRv;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.search)
    ImageView search;
    @InjectView(R.id.date_radio_group)
    RadioGroup dateRadioGroup;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.no_content)
    TextView noContent;
    @InjectView(R.id.total_money)
    TextView totalMoney;
    private StaffPerformanceFindPresenter staffPerformanceFindPresenter = new StaffPerformanceFindPresenter();//员工业绩查询
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数

    private CommonAdapter<StaffPerformanceFindBean.PageBean.ListBean> commonAdapter;
    private List<StaffPerformanceFindBean.PageBean.ListBean> staffPerformanceFindList;

    private LinearLayoutManager linearLayoutManager;
    private SearchFragment searchFragment;
    private RadioButton dateRadio_rb;  //选择日期 按钮
    private String dateRadio_tv = "week";  //选择日期  文字
    private String text;//搜索框内容
    private BroadcastReceiver receiver;//获取广播对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_performance_find);
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

    private void initViews() {
        //搜索框
        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                //这里处理逻辑
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                text = keyword;
                requestStaffPerformanceFindResult(text);
            }
        });
        //选择的时间
        dateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                dateRadio_rb = (RadioButton) findViewById(checkedId);
                dateRadio_tv = dateRadio_rb.getTag().toString();
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                text = null;
                requestStaffPerformanceFindResult(text);
            }
        });

        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        text = null;
        requestStaffPerformanceFindResult(text);

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
        staffPerformanceFindRv.setLayoutManager(linearLayoutManager);

    }

    /**
     * 初始化收件列表信息
     */
    private void requestStaffPerformanceFindResult(String text) {
        JSONObject json = new JSONObject();
        try {
            JSONObject page = new JSONObject();
            page.put("pageNo", pageno);
            page.put("pageSize", pagesize);
            json.put("pageInfo", page);
            json.put("text", text);
            json.put("timeType", dateRadio_tv);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(this);
        staffPerformanceFindPresenter.attach(this);
        staffPerformanceFindPresenter.postJsonStaffPerformanceFindResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        commonAdapter = new CommonAdapter<StaffPerformanceFindBean.PageBean.ListBean>(this, R.layout.item_staff_performance_find, staffPerformanceFindList) {
            @Override
            protected void convert(ViewHolder holder, final StaffPerformanceFindBean.PageBean.ListBean staffPerformanceFind, final int position) {
                String score = String.valueOf(staffPerformanceFind.getScore());
                if (score == null || score.equals("0.0")) {
                    holder.setVisible(R.id.evaluate_rl, false);
                } else {
                    holder.setVisible(R.id.evaluate_rl, true);
                    if (score.contains(".0")) {
                        holder.setText(R.id.grade, "评分：" + score.split(".0")[0] + "星");
                    } else {
                        holder.setText(R.id.grade, "评分：" + score + "星");
                    }
                    holder.setText(R.id.service_reward, "打赏：" + staffPerformanceFind.getServiceReward() + "元");
                }
                holder.setText(R.id.datetime, staffPerformanceFind.getCreateDate());
                holder.setText(R.id.sir_name, staffPerformanceFind.getConsumerName());
                holder.setText(R.id.sir_address, staffPerformanceFind.getConsumerAddress());
                holder.setText(R.id.service_charge, "服务费：" + staffPerformanceFind.getServiceCharge() + "元");

            }
        };
        staffPerformanceFindRv.setAdapter(commonAdapter);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageno = 1;
                requestStaffPerformanceFindResult(text);
                SkipIntentUtil.rvRefreshTimeout(StaffPerformanceFindActivity.this, refreshLayout, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                requestStaffPerformanceFindResult(text);
                SkipIntentUtil.rvRefreshTimeout(StaffPerformanceFindActivity.this, refreshLayout, 0);
            }
        });
        SkipIntentUtil.rvRefreshSuccess(commonAdapter,refreshLayout);
    }

    @OnClick({R.id.return_arrows, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.skipIntent(this, StaffHomeActivity.class);
                finish();
                break;
            case R.id.search:
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
                break;
        }
    }

    @Override
    public void onStaffPerformanceFindListFinish(Object o) {
        StaffPerformanceFindBean staffPerformanceFindBean = (StaffPerformanceFindBean) o;
        if (staffPerformanceFindBean != null) {
            switch (dateRadio_tv) {
                case "week":
                    totalMoney.setText("本周共计：" + staffPerformanceFindBean.getPerformanceIncome() + "元");
                    break;
                case "month":
                    totalMoney.setText("本月共计：" + staffPerformanceFindBean.getPerformanceIncome() + "元");
                    break;
                case "year":
                    totalMoney.setText("本年共计：" + staffPerformanceFindBean.getPerformanceIncome() + "元");
                    break;
            }

            if (staffPerformanceFindList == null) {
                staffPerformanceFindList = new ArrayList<>();
            }
            List<StaffPerformanceFindBean.PageBean.ListBean> list = staffPerformanceFindBean.getPage().getList();
            if (staffPerformanceFindBean.getPage().getPageNo() == 1) {
                if (staffPerformanceFindBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    staffPerformanceFindList.clear();
                    staffPerformanceFindList = list;
                    setUpParameters();
                    if (staffPerformanceFindBean.getPage().getCount() > 10) {
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
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, staffPerformanceFindRv, staffPerformanceFindList.size());
                    for (int i = 0; i < list.size(); i++) {
                        staffPerformanceFindList.add(list.get(i));
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
            SkipIntentUtil.rvRefreshTimeout(StaffPerformanceFindActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT == false) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestStaffPerformanceFindResult(text);
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
        staffPerformanceFindPresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}
