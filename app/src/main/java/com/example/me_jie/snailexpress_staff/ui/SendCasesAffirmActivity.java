package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.BaseBean;
import com.example.me_jie.snailexpress_staff.bean.ProxySenderBean;
import com.example.me_jie.snailexpress_staff.bean.SendSaveBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ProxySenderView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.ProxySenderPresenter;
import com.example.me_jie.snailexpress_staff.utils.IDCardValidate;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
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

import static com.example.me_jie.snailexpress_staff.R.id.sender_message;

/**
 * 寄件 填写地址 我要寄件
 */
public class SendCasesAffirmActivity extends AutoLayoutActivity implements ProxySenderView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.nearby_snail)
    TextView nearbySnail;
    @InjectView(R.id.phone)
    TextView phone;
    @InjectView(sender_message)
    RelativeLayout senderMessage;
    @InjectView(R.id.sender_detailed)
    TextView senderDetailed;
    @InjectView(R.id.sender_message_detailed)
    RelativeLayout senderMessageDetailed;
    @InjectView(R.id.provincialCode)
    TextView provincialCode;
    @InjectView(R.id.accpet_message)
    RelativeLayout accpetMessage;
    @InjectView(R.id.accept_detailed)
    TextView acceptDetailed;
    @InjectView(R.id.accpet_message_detailed)
    RelativeLayout accpetMessageDetailed;
    @InjectView(R.id.goodsType)
    TextView goodsType;
    @InjectView(R.id.send_stdmode)
    RelativeLayout sendStdmode;
    @InjectView(R.id.card_no)
    EditText cardNo;
    @InjectView(R.id.checkbox)
    CheckBox checkbox;
    @InjectView(R.id.confirmation_message_btn)
    Button confirmationMessageBtn;
    @InjectView(R.id.cacompany)
    TextView cacompany;
    @InjectView(R.id.send_carrier)
    RelativeLayout sendCarrier;
    @InjectView(R.id.freight_base)
    TextView freightBase;

    //物品类型
    private List<ProxySenderBean.GoodsTypeListBean> goodsTypeListItems = new ArrayList<>();
    private List<ProxySenderBean.GoodsTypeListBean> goodsTypeList;

    //承运公司
    private List<ProxySenderBean.CacompanyBean> cacompanyItems = new ArrayList<>();
    private List<ProxySenderBean.CacompanyBean> cacompanyList;

    //运费标准
    private List<ProxySenderBean.WeightpriceBean> weightprice;

    private String goodsTypeOptions;//物品类型
    private String cacompanyOptions;//承运公司


    private Intent intent;
    private SendSaveBean.SendSaveParameter sendSaveParameter = new SendSaveBean.SendSaveParameter(); // 保存寄件所需要的参数
    private ProxySenderPresenter proxySenderPresenter = new ProxySenderPresenter();//获取寄件相关信息
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cases_affirm);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);

        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        getProxyUserSendApplyResult();
    }

    /**
     * 得到寄件相关信息
     */
    private void getProxyUserSendApplyResult() {
        new OkHttpResolve(this);
        proxySenderPresenter.attach(this);
        proxySenderPresenter.getProxyUserSendApplyResult(this, lazyLoadProgressDialog);
    }

    /**
     * 确认寄件信息
     */
    private void postSaveProxySenderResult(String json) {
        new OkHttpResolve(this);
        proxySenderPresenter.attach(this);
        proxySenderPresenter.postSaveProxySenderResult(json, this, lazyLoadProgressDialog);

    }

    @OnClick({R.id.return_arrows, sender_message, R.id.accpet_message, R.id.send_stdmode, R.id.send_carrier, R.id.checkbox, R.id.confirmation_message_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case sender_message:
                skipSendNewSite("sender_message");
                break;
            case R.id.accpet_message:
                skipSendNewSite("accpet_message");
                break;
            case R.id.send_stdmode:
                if (goodsTypeListItems != null  && goodsTypeListItems.size()>0) {
                    ShowPickerView(goodsType, "物品类型", "goodsType");
                } else {
                    SkipIntentUtil.toastShow(this, "请重新申请信息！");
                }
                break;
            case R.id.send_carrier:
                if (cacompanyItems != null  && cacompanyItems.size()>0) {
                    ShowPickerView(cacompany, "承运公司", "cacompany");
                } else {
                    SkipIntentUtil.toastShow(this, "请重新申请信息！");
                }
                break;
            case R.id.checkbox:
                if (checkbox.isChecked() == true) {
                    confirmationMessageBtn.setEnabled(true);
                } else {
                    confirmationMessageBtn.setEnabled(false);
                }
                break;
            case R.id.confirmation_message_btn:
                String card = cardNo.getText().toString();
                if (!TextUtils.isEmpty(nearbySnail.getText().toString())) {
                    if (!TextUtils.isEmpty(phone.getText().toString())) {
                        if (!TextUtils.isEmpty(provincialCode.getText().toString())) {
                            if (!TextUtils.isEmpty(goodsType.getText().toString())) {
                                if (!TextUtils.isEmpty(cacompany.getText().toString())) {
                                    if (!TextUtils.isEmpty(card)) {
                                        String validate_effective = IDCardValidate.validate_effective(card, true);
                                        if (validate_effective.equals(card)) {
                                            lazyLoadProgressDialog.show();
                                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                                            HashMap<String, String> params = new HashMap<>();
                                            if (sendSaveParameter != null) {
                                                params.put("worksId", sendSaveParameter.getWorksId());
                                                params.put("receivername", sendSaveParameter.getReceivername());
                                                params.put("receivermoblie", sendSaveParameter.getReceivermoblie());
                                                params.put("receiverprovincename", sendSaveParameter.getReceiverprovincename());
                                                params.put("receivercityname", sendSaveParameter.getReceivercityname());
                                                params.put("receiverexpareaname", sendSaveParameter.getReceiverexpareaname());
                                                params.put("receiveraddress", sendSaveParameter.getReceiveraddress());
                                                params.put("sendername", sendSaveParameter.getSendername());
                                                params.put("sendermoblie", sendSaveParameter.getSendermoblie());
                                                params.put("senderprovincename", sendSaveParameter.getSenderprovincename());
                                                params.put("sendercityname", sendSaveParameter.getSendercityname());
                                                params.put("senderexpareaname", sendSaveParameter.getSenderexpareaname());
                                                params.put("senderaddress", sendSaveParameter.getSenderaddress());
                                                params.put("goodsname", sendSaveParameter.getGoodsname());
                                                params.put("shippercode", sendSaveParameter.getShippercode());
                                                params.put("card", validate_effective);
                                                JSONObject jsonObject = new JSONObject(params);
                                                postSaveProxySenderResult(jsonObject.toString());
                                            }
                                        } else {
                                            SkipIntentUtil.toastShow(this, validate_effective);
                                        }
                                    } else {
                                        SkipIntentUtil.toastShow(this, "请输入身份证号码！");
                                    }
                                } else {
                                    SkipIntentUtil.toastShow(this, "请选择承运公司！");
                                }
                            } else {
                                SkipIntentUtil.toastShow(this, "请选择物品类型！");
                            }
                        } else {
                            SkipIntentUtil.toastShow(this, "请添加收件人！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请添加寄件人！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请选择蜗牛站地址！");
                }
                break;
        }
    }
    String tx = ""; //选中的值
    private void ShowPickerView(final TextView textName, final String typeName, final String type) {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                switch (type) {
                    case "goodsType"://商品类型
                        tx = goodsTypeListItems.get(options1).getPickerViewText();
                        for (int i = 0; i < goodsTypeListItems.size(); i++) {
                            if (tx.equals(goodsTypeListItems.get(i).getLabel())) {
                                sendSaveParameter.setGoodsname(goodsTypeListItems.get(i).getValue());
                                goodsTypeOptions = tx;
                            }
                        }
                        break;
                    case "cacompany"://承运公司
                        tx = cacompanyItems.get(options1).getPickerViewText();
                        for (int i = 0; i < cacompanyItems.size(); i++) {
                            if (tx.equals(cacompanyItems.get(i).getLabel())) {
                                sendSaveParameter.setShippercode(cacompanyItems.get(i).getValue());
                                cacompanyOptions = tx;
                            }
                        }
                        break;
                }
                textName.setTextColor(Color.parseColor("#333333"));
                textName.setText(tx);
            }
        })

                .setTitleText(typeName)
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
        switch (type) {
            case "goodsType":
                pvOptions.setPicker(goodsTypeListItems);//一级选择器
                if (goodsTypeOptions != null) {
                    for (int i = 0; i < goodsTypeListItems.size(); i++) {
                        if (goodsTypeListItems.get(i).getLabel().equals(goodsTypeOptions)) {
                            pvOptions.setSelectOptions(i);
                        }
                    }
                }
                break;
            case "cacompany":
                pvOptions.setPicker(cacompanyItems);//一级选择器
                if (cacompanyOptions != null) {
                    for (int i = 0; i < cacompanyItems.size(); i++) {
                        if (cacompanyItems.get(i).getLabel().equals(cacompanyOptions)) {
                            pvOptions.setSelectOptions(i);
                        }
                    }
                }
                break;
        }
        pvOptions.show();
    }
    /**
     * 跳转到添加地址页面
     *
     * @param source
     */
    public void skipSendNewSite(String source) {
        intent = new Intent(this, SendNewSiteActivity.class);
        intent.putExtra("source", source);
        String source_sender = sendSaveParameter.getSource_sender();
        String source_accept = sendSaveParameter.getSource_accept();
        if (source.equals("sender_message")) {
            if (!TextUtils.isEmpty(source_sender)) {
                compileAddress(sendSaveParameter.getSendername(), sendSaveParameter.getSendermoblie(),
                        sendSaveParameter.getSenderprovincename(), sendSaveParameter.getSendercityname(),
                        sendSaveParameter.getSenderexpareaname(), sendSaveParameter.getSenderaddress(), source_sender);
            }
            startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
        } else {
            if (!TextUtils.isEmpty(source_accept)) {
                compileAddress(sendSaveParameter.getReceivername(), sendSaveParameter.getReceivermoblie(),
                        sendSaveParameter.getReceiverprovincename(), sendSaveParameter.getReceivercityname(),
                        sendSaveParameter.getReceiverexpareaname(), sendSaveParameter.getReceiveraddress(), source_accept);
            }
            startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
        }
    }

    /**
     * 编辑地址
     */
    private void compileAddress(String name, String moblie, String provincename, String cityname, String expareaname, String address, String source) {
        intent.putExtra("name", name);
        intent.putExtra("moblie", moblie);
        intent.putExtra("provincename", provincename);
        intent.putExtra("cityname", cityname);
        intent.putExtra("expareaname", expareaname);
        intent.putExtra("address", address);
        intent.putExtra("source_return", source);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String source_return = data.getStringExtra("source_return");
            if (!TextUtils.isEmpty(source_return)) {
                String name = data.getStringExtra("name");
                String moblie = data.getStringExtra("moblie");
                String detailedAddress = data.getStringExtra("provincename") + data.getStringExtra("cityname") + data.getStringExtra("expareaname") + data.getStringExtra("address");
                switch (source_return) {
                    case "sender_message":
                        setSendResultAddress(phone, senderMessageDetailed, senderDetailed, detailedAddress, name, moblie);
                        sendSaveParameter.setSendername(data.getStringExtra("name"));
                        sendSaveParameter.setSendermoblie(data.getStringExtra("moblie"));
                        sendSaveParameter.setSenderprovincename(data.getStringExtra("provincename"));
                        sendSaveParameter.setSendercityname(data.getStringExtra("cityname"));
                        sendSaveParameter.setSenderexpareaname(data.getStringExtra("expareaname"));
                        sendSaveParameter.setSenderaddress(data.getStringExtra("address"));
                        sendSaveParameter.setSource_sender(source_return);
                        break;
                    case "accpet_message":
                        setSendResultAddress(provincialCode, accpetMessageDetailed, acceptDetailed, detailedAddress, name, moblie);
                        sendSaveParameter.setReceivername(data.getStringExtra("name"));
                        sendSaveParameter.setReceivermoblie(data.getStringExtra("moblie"));
                        sendSaveParameter.setReceiverprovincename(data.getStringExtra("provincename"));
                        sendSaveParameter.setReceivercityname(data.getStringExtra("cityname"));
                        sendSaveParameter.setReceiverexpareaname(data.getStringExtra("expareaname"));
                        sendSaveParameter.setReceiveraddress(data.getStringExtra("address"));
                        sendSaveParameter.setSource_accept(source_return);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 设置寄件的地址信息
     */
    private void setSendResultAddress(TextView phone, RelativeLayout messageDetailed, TextView detailed, String detailedAddress, String name, String moblie) {
        phone.setText(name + "  " + moblie);
        phone.setGravity(Gravity.LEFT);
        messageDetailed.setVisibility(View.VISIBLE);
        detailed.setText(detailedAddress);

    }

    @Override
    public void onProxyUserSendApplyFinish(Object o) {
        ProxySenderBean proxySenderBean = (ProxySenderBean) o;
        if (proxySenderBean != null) {
            ProxySenderBean.WorksStrBean worksStrBean = proxySenderBean.getWorksStr().get(0);
            nearbySnail.setText(worksStrBean.getStationName());
            sendSaveParameter.setWorksId(worksStrBean.getId());
            weightprice = proxySenderBean.getWeightprice();
            //运费标准
            if (weightprice != null && weightprice.size() > 0) {
                freightBase.setText("运费标准：首重1公斤" + weightprice.get(0).getLabel() + "元  继重" + weightprice.get(1).getLabel() + "元");
            }
            //物品类型
            goodsTypeList = proxySenderBean.getGoodsTypeList();
            if (goodsTypeList != null && goodsTypeList.size() > 0) {
                goodsTypeListItems = goodsTypeList;
            }
            //承运公司
            cacompanyList = proxySenderBean.getCacompany();
            if (cacompanyList != null && cacompanyList.size() > 0) {
                cacompanyItems = cacompanyList;
            }
        }
    }

    @Override
    public void onSaveProxySenderFinish(Object o) {
        BaseBean saveProxySender = (BaseBean) o;
        if (saveProxySender.getStatus().equals("1")) {
            if (getIntent().getStringExtra("source").equals("adsense_sent_item")) {
                SkipIntentUtil.skipIntent(this, SendExpressageActivity.class);
            } else {
                SkipIntentUtil.skipIntent(this, StaffCourierServiceParcelActivity.class);
            }
            finish();
        }
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
        proxySenderPresenter.dettach();
    }
}
