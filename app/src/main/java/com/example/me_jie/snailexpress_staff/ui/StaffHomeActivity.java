package com.example.me_jie.snailexpress_staff.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.ExpressRobSingleBean;
import com.example.me_jie.snailexpress_staff.bean.LoginBean;
import com.example.me_jie.snailexpress_staff.bean.StatisticalDataBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.dialog.SystemPromptDialog;
import com.example.me_jie.snailexpress_staff.mvp.ExpressRobSingleView;
import com.example.me_jie.snailexpress_staff.mvp.LoginView;
import com.example.me_jie.snailexpress_staff.activity.BaseActivity;
import com.example.me_jie.snailexpress_staff.net.NetBroadcastReceiver;
import com.example.me_jie.snailexpress_staff.net.NetUtil;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.ExpressRobSinglePresenter;
import com.example.me_jie.snailexpress_staff.presenter.LoginPresenter;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.MoveLocationUtil;
import com.example.me_jie.snailexpress_staff.utils.MyActivityManager;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lzy.okgo.OkGo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 员工首页
 */
public class StaffHomeActivity extends BaseActivity implements LoginView, ExpressRobSingleView {
    private static final String TAG = "StaffHomeActivity";
    @InjectView(R.id.recipients)
    RelativeLayout recipients;
    @InjectView(R.id.took)
    RelativeLayout took;
    @InjectView(R.id.unusual_parcel)
    RelativeLayout unusualParcel;
    @InjectView(R.id.detention_parcel)
    RelativeLayout detentionParcel;
    @InjectView(R.id.sign)
    RelativeLayout sign;
    @InjectView(R.id.QRcode)
    TextView QRcode;
    @InjectView(R.id.write_off)
    TextView writeOff;
    @InjectView(R.id.myScrollview)
    ScrollView myScrollview;

    RecyclerView expressRobSingleRv;
    TwinklingRefreshLayout refreshLayout;
    TextView noContent;
    @InjectView(R.id.yc_sender_number)
    TextView ycSenderNumber;
    @InjectView(R.id.yc_accept_number)
    TextView ycAcceptNumber;
    private Intent intent;

    SharedPreferences sp; //免登陆
    SharedPreferences.Editor editor;


    private LoginPresenter loginPresenter = new LoginPresenter();//登录
    private CommonAdapter<ExpressRobSingleBean.PageBean.ListBean> commonAdapter;
    private List<ExpressRobSingleBean.PageBean.ListBean> expressRobSingleBeenList;
    private ExpressRobSinglePresenter expressRobSinglePresenter = new ExpressRobSinglePresenter();//抢单列表
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private int pageno = 1; //分页
    private int pagesize = 4;//每页条数

    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private String businessType;//抢单类型
    private boolean isExecute = false; //是否执行刷新

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 1000 * 60);// 间隔60秒
        }

        void update() {
            //刷新msg的内容
            if (isExecute == true) {
                pageno = 1;
                requestGetOrderList();
            }
        }
    };

    private BroadcastReceiver receiver;//获取广播对象
    private boolean ifCourierContinue = false;//是否显示弹窗
    private  View dialogLayout;//弹窗布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        receiver = new NetBroadcastReceiver();
        StatusBarUtil.registerBroadrecevicer(this, receiver);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        //启动时判断网络状态
        Constants.NETCONNECT = this.isNetConnect();
        if (Constants.NETCONNECT == false) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
        }else{
            Constants.NETCONNECT =true;
        }
        initViews(null);
        expressRobSingleRv.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sp.edit();

        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        requestIfCourierContinueAbleGetQRCode();
        requestStaffStatisticalData();
        handler.postDelayed(runnable, 1000 * 60);

    }

    /**
     * 初始化布局
     */
    private void initViews(View layout) {
        if (layout != null) {
            refreshLayout = (TwinklingRefreshLayout) layout.findViewById(R.id.refresh);
            expressRobSingleRv = (RecyclerView) layout.findViewById(R.id.express_rob_single_rv);
            noContent = (TextView) layout.findViewById(R.id.no_content);
        } else {
            refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh);
            expressRobSingleRv = (RecyclerView) findViewById(R.id.express_rob_single_rv);
            noContent = (TextView) findViewById(R.id.no_content);
        }
        refreshLayout.setEnableRefresh(false);
        //添加底部
        refreshLayout.setOverScrollBottomShow(false);
        refreshLayout.setAutoLoadMore(true);
        linearLayoutManager = new LinearLayoutManager(this);
        expressRobSingleRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        commonAdapter = new CommonAdapter<ExpressRobSingleBean.PageBean.ListBean>(this, R.layout.item_express_rob_single, expressRobSingleBeenList) {
            @Override
            protected void convert(ViewHolder holder, final ExpressRobSingleBean.PageBean.ListBean recipientsDisplayBean, final int position) {
                RequestOperationUtil.setAdpaterOrder(recipientsDisplayBean, holder);
            }
        };
        expressRobSingleRv.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                businessType = expressRobSingleBeenList.get(position).getBusinessType();
                isGrabSingle(expressRobSingleBeenList.get(position).getOrderId());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                requestGetOrderList();
                SkipIntentUtil.rvRefreshTimeout(StaffHomeActivity.this, refreshLayout, 0);
            }
        });
        SkipIntentUtil.rvRefreshSuccess(commonAdapter,refreshLayout);
    }

    @OnClick({R.id.recipients, R.id.took, R.id.unusual_parcel, R.id.detention_parcel, R.id.sign, R.id.QRcode, R.id.write_off})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recipients:
                SkipIntentUtil.skipIntent(this, ThenAllocationParcelActivity.class);
                finish();
                break;
            case R.id.took:
                SkipIntentUtil.skipIntent(this, StaffCourierServiceParcelActivity.class);
                finish();
                break;
            case R.id.unusual_parcel:
                SkipIntentUtil.skipIntent(this, StaffPerformanceFindActivity.class);
                finish();
                break;
            case R.id.detention_parcel:
                break;
            case R.id.sign:
                break;
            case R.id.QRcode:
                intent = new Intent(this, MipcaActivityCapture.class);
                intent.putExtra("source", "staff_home");
                startActivity(intent);
                finish();
                break;
            case R.id.write_off:
                onClickWrittenOff();
                break;

        }
    }

    //显示抢单弹窗
    private void showExpressRobSingleDialog() {
        final Dialog expressRobDialog = new Dialog(this, R.style.Dialog);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogLayout = inflater.inflate(R.layout.coverpopup_express_rob_single_item, null);
        expressRobDialog.addContentView(dialogLayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        integratedDataDialog(dialogLayout);

        expressRobDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                expressRobSinglePresenter.dettach();
                integratedDataDialog(null);
            }
        });
        expressRobDialog.show();
    }

    /**
     * 方法整合 统一使用
     *
     * @param view
     */
    private void integratedDataDialog(View view) {
        pageno = 1;
        initViews(view);
        requestGetOrderList();
    }

    /**
     * 是否抢单
     */
    private void isGrabSingle(final String orderId) {
        SystemPromptDialog.Builder builder = new SystemPromptDialog.Builder(this, "确定要抢单吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestGrabOrder(orderId);
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().setCanceledOnTouchOutside(true);  //用户选择取消或者是点击屏幕空白部分时让dialog消失。
        builder.create().show();
    }

    //点击注销按钮
    private void onClickWrittenOff() {
        SystemPromptDialog.Builder builder = new SystemPromptDialog.Builder(this, "确定要注销吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                editor.remove("login");
                editor.clear();
                editor.commit();
                SkipIntentUtil.skipIntent(StaffHomeActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
                MyActivityManager.getInstance().finishAllActivity();
                requestWriteOff();
                //设置你的操作事项
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().setCanceledOnTouchOutside(true);  //用户选择取消或者是点击屏幕空白部分时让dialog消失。
        builder.create().show();
    }

    /**
     * 初始化注销
     */
    private void requestWriteOff() {
        new OkHttpResolve(this);
        loginPresenter.attach(this);
        loginPresenter.userWriteOffResult(this);
    }

    /**
     * 初始化员工手上是否有单未完成
     */
    private void requestIfCourierContinueAbleGetQRCode() {
        new OkHttpResolve(this);
        expressRobSinglePresenter.attach(this);
        expressRobSinglePresenter.postJsonIfCourierContinueResult("{\"id\":\"\"}", this, lazyLoadProgressDialog);
    }

    /**
     * 初始化抢单数据
     */
    private void requestGetOrderList() {
        new OkHttpResolve(this);
        expressRobSinglePresenter.attach(this);
        expressRobSinglePresenter.postJsonExpressRobSingleListResult("{\"pageNo\":\"" + pageno + "\",\"pageSize\":\"" + pagesize + "\"}", this, lazyLoadProgressDialog);
    }

    /**
     * 初始化员工统计数据
     */
    private void requestStaffStatisticalData() {
        new OkHttpResolve(this);
        expressRobSinglePresenter.attach(this);
        expressRobSinglePresenter.postJsonStaffStatisticalDataResult("{\"id\":\"\"}", this, lazyLoadProgressDialog);
    }

    /**
     * 初始化员工抢单
     */
    private void requestGrabOrder(String orderId) {
        new OkHttpResolve(this);
        expressRobSinglePresenter.attach(this);
        expressRobSinglePresenter.postJsonExpressRobSingleResult("{\"orderId\":\"" + orderId + "\"}", this, lazyLoadProgressDialog);
    }

    @Override
    public void onBeanLoginFinish(Object o) {

    }

    @Override
    public void onBeanWriteOffFinish(Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            Log.e(TAG, "onBeanWriteOffFinish: 注销成功！");
        }
    }

    @Override
    public void onExpressRobSingleListFinish(Object o) {
        ExpressRobSingleBean expressRobSingleBean = (ExpressRobSingleBean) o;
        if (expressRobSingleBean != null) {
            if (expressRobSingleBeenList == null) {
                expressRobSingleBeenList = new ArrayList<>();
            }
            isExecute = true;
            List<ExpressRobSingleBean.PageBean.ListBean> list = expressRobSingleBean.getPage().getList();
            if (expressRobSingleBean.getPage().getPageNo() == 1) {
                if (expressRobSingleBean.getPage().getCount() > 0) {
                    expressRobSingleRv.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    expressRobSingleBeenList.clear();
                    expressRobSingleBeenList = list;
                    setUpParameters();
                    if (expressRobSingleBean.getPage().getCount() > 4) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    noContent.setVisibility(View.VISIBLE);
                    expressRobSingleRv.setVisibility(View.GONE);
                    refreshLayout.setEnableLoadmore(false);
                }
            } else {
                if (list != null) {
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, expressRobSingleRv, expressRobSingleBeenList.size());
                    for (int i = 0; i < list.size(); i++) {
                        expressRobSingleBeenList.add(list.get(i));
                    }
                    setUpParameters();
                    if (list.size() == 4) {
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
        } else {
            isExecute = false;
        }
    }

    @Override
    public void onIfCourierContinueAbleGetQRCode(Object o) {
        ExpressRobSingleBean expressRobSingleBean = (ExpressRobSingleBean) o;
        if (expressRobSingleBean != null) {
            ifCourierContinue = expressRobSingleBean.isIfCourierContinueAbleGetQRCode();
            if (ifCourierContinue == true) {
                showExpressRobSingleDialog();
            } else {
                integratedDataDialog(null);
                if (expressRobSingleBean.getMsg() != null && expressRobSingleBean.getMsg() != "") {
                    SkipIntentUtil.toastShow(this, expressRobSingleBean.getMsg());
                }
            }
        }
    }

    @Override
    public void onExpressRobSingleFinish(Object o) {
        ExpressRobSingleBean expressRobSingleBean = (ExpressRobSingleBean) o;
        if(expressRobSingleBean!=null){
            Log.e(TAG, "onExpressRobSingleFinish: "+expressRobSingleBean.getStatus() + "  "+ businessType);
            if(expressRobSingleBean.getStatus().equals("1")){
                if (businessType != null) {
                    if (businessType.equals("送件")) {
                        SkipIntentUtil.skipIntent(this, ThenAllocationParcelActivity.class);
                    } else {
                        SkipIntentUtil.skipIntent(this, StaffCourierServiceParcelActivity.class);
                    }
                    finish();
                }
            }else{
                if(ifCourierContinue==true && dialogLayout!=null){
                    integratedDataDialog(dialogLayout);
                }else{
                    integratedDataDialog(null);
                }
            }
        }
    }

    @Override
    public void onStaffStatisticalDataFinish(Object o) {
        StatisticalDataBean statisticalDataBean = (StatisticalDataBean) o;
        if (statisticalDataBean != null) {
            ycSenderNumber.setText("本月已寄件：" + statisticalDataBean.getSendCount());
            ycAcceptNumber.setText("本月已收件：" + statisticalDataBean.getAcceptCount());
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
            SkipIntentUtil.rvRefreshTimeout(StaffHomeActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT == false) {
                integratedDataDialog(null);
                requestIfCourierContinueAbleGetQRCode();
                requestStaffStatisticalData();
            }
        }
    }

    /**
     * Android中的“再按一次返回键退出程序”实现
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出蜗牛快递！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MyActivityManager.getInstance().finishAllActivity();
                System.exit(0);
            }
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
        handler.removeCallbacks(runnable); //停止刷新
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        loginPresenter.dettach();
        expressRobSinglePresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}
