package com.example.me_jie.snailexpress_staff.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.ExpressRobSingleBean;
import com.example.me_jie.snailexpress_staff.bean.LoginBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsDisplayBean;
import com.example.me_jie.snailexpress_staff.bean.SendDisplayBean;
import com.example.me_jie.snailexpress_staff.bean.StatisticalDataBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.dialog.SystemPromptDialog;
import com.example.me_jie.snailexpress_staff.mvp.LoginView;
import com.example.me_jie.snailexpress_staff.mvp.StatisticalDataView;
import com.example.me_jie.snailexpress_staff.activity.BaseActivity;
import com.example.me_jie.snailexpress_staff.net.NetBroadcastReceiver;
import com.example.me_jie.snailexpress_staff.net.NetUtil;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.LoginPresenter;
import com.example.me_jie.snailexpress_staff.presenter.StatisticalDataPresenter;
import com.example.me_jie.snailexpress_staff.utils.MyActivityManager;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 站长主页
 */
public class AdministratorHomeActivity extends BaseActivity implements LoginView, StatisticalDataView {
    private static final String TAG = "AdministratorHomeActivi";
    @InjectView(R.id.recipients)
    RelativeLayout recipients;
    @InjectView(R.id.took)
    RelativeLayout took;
    @InjectView(R.id.unusual_parcel)
    RelativeLayout unusualParcel;
    @InjectView(R.id.detention_parcel)
    RelativeLayout detentionParcel;
    @InjectView(R.id.collecting_money)
    RelativeLayout collectingMoney;
    @InjectView(R.id.sign)
    RelativeLayout sign;
    @InjectView(R.id.write_off)
    TextView writeOff;
    @InjectView(R.id.QRcode)
    TextView QRcode;
    @InjectView(R.id.accept_count_tv)
    TextView acceptCountTv;
    @InjectView(R.id.return_count_tv)
    TextView returnCountTv;
    @InjectView(R.id.sender_count_tv)
    TextView senderCountTv;
    @InjectView(R.id.order_more)
    TextView orderMore;
    @InjectView(R.id.receive_expressAccept_more)
    TextView receiveExpressAcceptMore;
    @InjectView(R.id.receive_expresssend_more)
    TextView receiveExpressSendMore;
    @InjectView(R.id.ers_name)
    TextView ersName;
    @InjectView(R.id.ers_phone)
    TextView ersPhone;
    @InjectView(R.id.ers_address)
    TextView ersAddress;
    @InjectView(R.id.ers_type)
    TextView ersType;
    @InjectView(R.id.conllecting_state)
    TextView conllectingState;
    @InjectView(R.id.express_type)
    TextView expressType;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.express_datetime)
    TextView expressDatetime;
    @InjectView(R.id.sir_name)
    TextView sirName;
    @InjectView(R.id.sir_address)
    TextView sirAddress;
    @InjectView(R.id.accept_phone)
    TextView acceptPhone;
    @InjectView(R.id.courier_send_state)
    TextView courierSendState;
    @InjectView(R.id.goodsname)
    TextView goodsname;
    @InjectView(R.id.sender)
    TextView sender;
    @InjectView(R.id.sender_phone)
    TextView senderPhone;
    @InjectView(R.id.addressee)
    TextView addressee;
    @InjectView(R.id.weight)
    TextView weight;
    @InjectView(R.id.delivery_costs)
    TextView deliveryCosts;
    @InjectView(R.id.shipperCode)
    TextView shipperCode;
    @InjectView(R.id.courier_number)
    TextView courierNumber;
    @InjectView(R.id.send_expressage_state)
    TextView sendExpressageState;
    @InjectView(R.id.courier_sender_state)
    TextView courierSenderState;
    @InjectView(R.id.order_no_content)
    TextView orderNoContent;
    @InjectView(R.id.accept_no_content)
    TextView acceptNoContent;
    @InjectView(R.id.sender_no_content)
    TextView senderNoContent;
    @InjectView(R.id.express_rob_single_ll)
    LinearLayout expressRobSingleLl;
    @InjectView(R.id.ll_content)
    RelativeLayout dataMoreAcceptRl;
    @InjectView(R.id.rl_content)
    RelativeLayout rlContent;

    private Intent intent;

    SharedPreferences sp; //免登陆
    SharedPreferences.Editor editor;


    private LoginPresenter loginPresenter = new LoginPresenter();//登录
    private StatisticalDataPresenter statisticalDataPresenter = new StatisticalDataPresenter();//统计数据
    private BroadcastReceiver receiver;//获取广播对象
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_home);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        receiver = new NetBroadcastReceiver();
        StatusBarUtil.registerBroadrecevicer(this,receiver);
        //启动时判断网络状态
        Constants.NETCONNECT = this.isNetConnect();
        if (Constants.NETCONNECT == false) {
            lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
        }else{
            Constants.NETCONNECT = true;
        }
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sp.edit();

        requestStatisticalDataCount();
        requestStatistica1lDataShowStationmaster();
    }

    @OnClick({R.id.order_more, R.id.receive_expressAccept_more, R.id.receive_expresssend_more, R.id.recipients, R.id.took, R.id.unusual_parcel, R.id.detention_parcel, R.id.collecting_money, R.id.sign, R.id.QRcode, R.id.write_off})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_more:
                skipHomeShowDataMore("order_more");
                break;
            case R.id.receive_expressAccept_more:
                skipHomeShowDataMore("receive_expressAccept_more");
                break;
            case R.id.receive_expresssend_more:
                skipHomeShowDataMore("receive_expressSend_more");
                break;
            case R.id.recipients:
                skipIntentMipcaCapture("administrator_recipients");
                break;
            case R.id.took:
                skipIntentMipcaCapture("administrator_took");
                break;
            case R.id.unusual_parcel:
                break;
            case R.id.detention_parcel:
                break;
            case R.id.collecting_money:
                intent = new Intent(this,AdministratorRecipientsDisplayActivity.class);
                intent.putExtra("home_source","acceptPay");
                startActivity(intent);
                finish();
                break;
            case R.id.sign:
                break;
            case R.id.QRcode:
                skipIntentMipcaCapture("administrator_home");
                break;
            case R.id.write_off:
                onClickWrittenOff();
                break;
        }
    }

    /**
     * 跳转更多列表页面
     *
     * @param types
     */
    private void skipHomeShowDataMore(String types) {
        intent = new Intent(this, HomeShowDataMoreActivity.class);
        intent.putExtra("types", types);
        startActivity(intent);
        finish();
    }

    /**
     * 跳转到扫码页面
     */
    private void skipIntentMipcaCapture(String source) {
        intent = new Intent(this, MipcaActivityCapture.class);
        intent.putExtra("source", source);
        startActivity(intent);
        finish();
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
                SkipIntentUtil.skipIntent(AdministratorHomeActivity.this, LoginActivity.class);
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
     * 初始化统计条数数据
     */
    private void requestStatisticalDataCount() {
        new OkHttpResolve(this);
        statisticalDataPresenter.attach(this);
        statisticalDataPresenter.postJsonStatisticalDataCountResult("{\"id\":\"\"}", this);
    }

    /**
     * 初始化首页三种数据数据
     */
    private void requestStatistica1lDataShowStationmaster() {
        new OkHttpResolve(this);
        statisticalDataPresenter.attach(this);
        statisticalDataPresenter.postJsonStatisticalDataShowStationmasterResult("{\"id\":\"\"}", this);
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
    public void onStatisticalDataCountFinish(Object o) {
        StatisticalDataBean statisticalDataBean = (StatisticalDataBean) o;
        if (statisticalDataBean != null) {
            acceptCountTv.setText("有" + statisticalDataBean.getAcceptCount() + "件包裹到本站");
            returnCountTv.setText("有" + statisticalDataBean.getReturnCount() + "件包裹退回本站");
            senderCountTv.setText("有" + statisticalDataBean.getSendCount() + "件包裹到本站");
        }
    }

    @Override
    public void onStatisticalDataShowStationmasterFinish(Object o) {
        StatisticalDataBean statisticalDataBean = (StatisticalDataBean) o;
        if (statisticalDataBean != null) {
            //抢单
            ExpressRobSingleBean.PageBean.ListBean order = statisticalDataBean.getOrder();
            if (order != null) {
                expressRobSingleLl.setVisibility(View.VISIBLE);
                orderNoContent.setVisibility(View.GONE);
                String businessType = order.getBusinessType();
                ersType.setText(businessType);
                if (businessType.equals("送件")) {
                    ersType.setBackgroundResource(R.drawable.button_return_shape);
                    ersType.setTextColor(Color.parseColor("#6fd1c8"));
                } else {
                    ersType.setBackgroundResource(R.drawable.button_affirm_shape);
                    ersType.setTextColor(Color.parseColor("#FFFFFF"));
                }
                ersName.setText(order.getUsername());
                ersPhone.setText(order.getMobile());
                ersAddress.setText(order.getAddress());
            } else {
                expressRobSingleLl.setVisibility(View.GONE);
                orderNoContent.setVisibility(View.VISIBLE);
            }
            //收件
            RecipientsDisplayBean.PageBean.ListBean expressAccept = statisticalDataBean.getExpressAccept();
            if (expressAccept != null) {
                dataMoreAcceptRl.setVisibility(View.VISIBLE);
                acceptNoContent.setVisibility(View.GONE);
                conllectingState.setText(expressAccept.getBusinessStatus());
                expressType.setText(expressAccept.getExpressParcel().getShipperCode());
                expressNumber.setText(expressAccept.getExpressParcel().getLgisticCode());
                expressDatetime.setText(expressAccept.getCreateDate());
                courierSendState.setText(expressAccept.getRemarks());

                RecipientsDisplayBean.PageBean.ListBean.ExpressParcelBean.ReceiverBean receiver = expressAccept.getExpressParcel().getReceiver();
                if (receiver != null) {
                    sirName.setText(receiver.getName());
                    acceptPhone.setText(receiver.getMobile());
                    String provinceName = receiver.getProvinceName();
                    String cityName = receiver.getCityName();
                    String expAreaName = receiver.getExpAreaName();
                    String address = receiver.getAddress();
                    if (provinceName.equals(cityName)) {
                        sirAddress.setText(cityName + expAreaName + address);
                    } else {
                        sirAddress.setText(provinceName + cityName + expAreaName + address);
                    }
                }
            } else {
                dataMoreAcceptRl.setVisibility(View.GONE);
                acceptNoContent.setVisibility(View.VISIBLE);
            }
            //寄件
            SendDisplayBean.PageBean.ListBean expressSend = statisticalDataBean.getExpressSend();
            if (expressSend != null) {
                rlContent.setVisibility(View.VISIBLE);
                senderNoContent.setVisibility(View.GONE);
                SendDisplayBean.PageBean.ListBean.ExpressParcelBean expressParcel = expressSend.getExpressParcel();
                shipperCode.setText("所选承运公司为：" + expressParcel.getShipperCode());
                SendDisplayBean.PageBean.ListBean.ExpressParcelBean.CommodityBean commodity = expressParcel.getCommodity();
                goodsname.setText(commodity.getGoodsName());
                weight.setText("包裹重量为：" + commodity.getGoodsWeight() + "kg");

                deliveryCosts.setText("包裹快递费用为：" + expressParcel.getCost() + "元");
                SendDisplayBean.PageBean.ListBean.ExpressParcelBean.SenderBean senderBean = expressParcel.getSender();
                SendDisplayBean.PageBean.ListBean.ExpressParcelBean.ReceiverBean receiver = expressParcel.getReceiver();
                sender.setText(senderBean.getName() + " " + senderBean.getProvinceName() + senderBean.getCityName() + senderBean.getExpAreaName() + senderBean.getAddress());
                addressee.setText(receiver.getName() + " " + receiver.getProvinceName() + receiver.getCityName() + receiver.getExpAreaName() + receiver.getAddress());

                String lgisticcode = expressParcel.getLgisticCode();
                if (!lgisticcode.equals("0")) {
                    courierNumber.setText("快递单号：" + lgisticcode);
                    courierNumber.setVisibility(View.VISIBLE);
                } else {
                    courierNumber.setVisibility(View.GONE);
                }
                sendExpressageState.setText(expressSend.getBusinessStatus());
                courierSenderState.setText(expressSend.getRemarks());
                senderPhone.setText(expressSend.getExpressParcel().getReceiver().getMobile());
            } else {
                rlContent.setVisibility(View.GONE);
                senderNoContent.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, null);
            Constants.NETCONNECT = false;
        } else {
            if(Constants.NETCONNECT==false){
                requestStatisticalDataCount();
                requestStatistica1lDataShowStationmaster();
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
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        statisticalDataPresenter.dettach();
        loginPresenter.dettach();
        if(receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }
}
