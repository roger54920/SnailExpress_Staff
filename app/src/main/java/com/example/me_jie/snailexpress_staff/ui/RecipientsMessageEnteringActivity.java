package com.example.me_jie.snailexpress_staff.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsPhoneBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsTabletBean;
import com.example.me_jie.snailexpress_staff.custom.speech.setting.IatSettings;
import com.example.me_jie.snailexpress_staff.custom.speech.util.JsonParser;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.dialog.SelectReceiptAddressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ARecipientsEnteringView;
import com.example.me_jie.snailexpress_staff.mvp.ARecipientsTabletView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.ARecipientsPresenter;
import com.example.me_jie.snailexpress_staff.presenter.ARecipientsTabletPresenter;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.example.me_jie.snailexpress_staff.utils.TelNumMatch;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.me_jie.snailexpress_staff.R.id.phone_electronic_tv;

/**
 * 站长收件信息录入
 */
public class RecipientsMessageEnteringActivity extends AutoLayoutActivity implements ARecipientsEnteringView, ARecipientsTabletView {
    private static final String TAG = "RecipientsMessageEnteri";
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.confirmation_message_btn)
    Button confirmationMessageBtn;
    @InjectView(R.id.checkbox)
    CheckBox checkbox;
    @InjectView(R.id.select_house)
    RelativeLayout selectHouse;
    @InjectView(R.id.sr_name)
    EditText srName;
    @InjectView(R.id.regional)
    TextView regional;
    @InjectView(R.id.house_tv)
    TextView houseTv;
    @InjectView(phone_electronic_tv)
    EditText phoneElectronicTv;
    @InjectView(R.id.voice_dictation)
    ImageView voiceDictation;
    @InjectView(R.id.tablet_name)
    TextView tabletName;
    @InjectView(R.id.title_tv)
    TextView titleTv;

    //门牌号选择
    private List<RecipientsTabletBean.HouseBean> tabletoptions1Items = new ArrayList<>();
    private List<List<String>> tabletoptions2Items = new ArrayList<>();
    private List<List<List<String>>> tabletoptions3Items = new ArrayList<>();
    private List<RecipientsTabletBean.HouseBean> houseSelect; //选择门牌号


    private ARecipientsPresenter aRecipientsPresenter = new ARecipientsPresenter();//站长收件录入
    private RecipientsBean.RecipientsSaveBean recipientsSaveBean = new RecipientsBean.RecipientsSaveBean();//保存手机号信息
    private ARecipientsTabletPresenter aRecipientsTabletPresenter = new ARecipientsTabletPresenter();//门牌号
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private Intent intent;
    private Handler handler = new Handler();
    private boolean isIfUserExit = false;//是否查出数据
    private String[] houseOptions = new String[3];//单元

    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private String[] regionalSplit;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients_message_entering);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);
        initViews();
    }

    private void initViews() {
        String home_source = getIntent().getStringExtra("home_source");
        if (!TextUtils.isEmpty(home_source) && home_source.equals("acceptPay")) {
            titleTv.setText("代收货款信息录入");
        } else {
            titleTv.setText("收件信息录入");
        }
        phoneElectronicTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phoneElectronic = phoneElectronicTv.getText().toString();
                if (!TextUtils.isEmpty(phoneElectronic)) {
                    int length = phoneElectronic.length();
                    if ((length >= 4 && length <= 10) || TelNumMatch.isPhone(phoneElectronic) == true) {
                        if (delayRun != null) {
                            //每次editText有变化的时候，则移除上次发出的延迟线程
                            handler.removeCallbacks(delayRun);
                        }
                        //延迟2000ms，如果不再输入字符，则执行该线程的run方法
                        handler.postDelayed(delayRun, 2000);

                    }
                }
            }
        });
        regionalSplit = getIntent().getStringExtra("remarks").split(",");

        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);

        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME,
                Activity.MODE_PRIVATE);

    }

    /*
    * 延迟线程，看是否还有下一个字符输入
    */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            if (!TextUtils.isEmpty(phoneElectronicTv.getText()) && phoneElectronicTv.getText().length() >= 4) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestAddressByPhoneOrParcelNo("{\"phone\":\"" + phoneElectronicTv.getText() + "\"}");
            }
        }
    };
    int ret = 0; // 函数调用返回值

    @OnClick({R.id.return_arrows, R.id.confirmation_message_btn, R.id.select_house, R.id.voice_dictation})
    public void onViewClicked(View view) {
        if (null == mIat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            Log.e(TAG, "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }
        switch (view.getId()) {
            case R.id.voice_dictation:
                index = 0;
                //语音听写
                // 移动数据分析，收集开始听写事件
                FlowerCollector.onEvent(this, "iat_recognize");

                mIatResults.clear();
                // 设置参数
                setParam();
                boolean isShowDialog = mSharedPreferences.getBoolean(
                        getString(R.string.pref_key_iat_show), true);
                if (isShowDialog) {
                    // 显示听写对话框
                    mIatDialog.setListener(mRecognizerDialogListener);
                    mIatDialog.show();
                    SkipIntentUtil.toastShow(this, getString(R.string.text_begin));
                } else {
                    // 不显示听写对话框
                    ret = mIat.startListening(mRecognizerListener);
                    if (ret != ErrorCode.SUCCESS) {
                        Log.e(TAG, "听写失败,错误码：" + ret);
                    } else {
                        SkipIntentUtil.toastShow(this, getString(R.string.text_begin));
                    }
                }
                break;
            case R.id.select_house:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (houseSelect == null) {
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    requestDataTablet("{\"cellCode\":\"" + getIntent().getStringExtra("cellCode") + "\"}");
                } else {
                    initJsonDataTablet(houseSelect);
                    showPickerView();
                }
                break;
            case R.id.return_arrows:
                SkipIntentUtil.sourceAcceptReturn(this, getIntent().getStringExtra("source"));
                break;
            case R.id.confirmation_message_btn:
                String phoneElectronic = phoneElectronicTv.getText().toString();
                String name = srName.getText().toString();
                if (!TextUtils.isEmpty(phoneElectronic)) {
                    JSONObject json;
                    JSONObject receiver;
                    if (!TextUtils.isEmpty(name.trim())) {
                        if (isIfUserExit == true) {
                            lazyLoadProgressDialog.show();
                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                            try {
                                json = new JSONObject();
                                receiver = new JSONObject();
                                postParameter(json);
                                receiver.put("id", recipientsSaveBean.getId());
                                json.put("receiver", receiver);
                                aRecipientsSave(json.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String electronicPhone = phoneElectronicTv.getText().toString();
                            if (TelNumMatch.isPhone(electronicPhone) == true) {
                                if (!TextUtils.isEmpty(houseTv.getText().toString())) {
                                    lazyLoadProgressDialog.show();
                                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                                    try {
                                        json = new JSONObject();
                                        receiver = new JSONObject();
                                        postParameter(json);
                                        receiver.put("phone", electronicPhone);
                                        receiver.put("name", srName.getText().toString());
                                        if (regionalSplit != null) {
                                            receiver.put("provincialCode", regionalSplit[0]);
                                            if (regionalSplit.length == 7) {
                                                receiver.put("cityCode", regionalSplit[1]);
                                                receiver.put("areaCode", regionalSplit[2]);
                                                receiver.put("place", regionalSplit[3] + houseTv.getText().toString());
                                            } else if (regionalSplit.length == 6) {
                                                receiver.put("cityCode", regionalSplit[0]);
                                                receiver.put("areaCode", regionalSplit[1]);
                                                receiver.put("place", regionalSplit[2] + houseTv.getText().toString());
                                            }
                                        }
                                        json.put("receiver", receiver);
                                        aRecipientsSave(json.toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    SkipIntentUtil.toastShow(this, "请选择门牌号！");
                                }
                            } else {
                                SkipIntentUtil.toastShow(this, "请正确输入手机号！");
                            }
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请输入收件人姓名！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请输入用户手机号或电子包裹箱号！");
                }
                break;
        }
    }

    /**
     * post提交的参数
     *
     * @param jsonObject
     */
    private void postParameter(JSONObject jsonObject) {
        try {
            jsonObject.put("id", getIntent().getStringExtra("expressAccept_id"));
            jsonObject.put("expressParcel.id", getIntent().getStringExtra("expressParcel_id"));
            jsonObject.put("act.taskId", getIntent().getStringExtra("act_taskId"));
            jsonObject.put("act.procInsId", getIntent().getStringExtra("act_procInsId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 站长收件录入
     */
    private void aRecipientsSave(String json) {
        new OkHttpResolve(this);
        aRecipientsPresenter.attach(this);
        aRecipientsPresenter.postSaveRecipientsResult(json, this, lazyLoadProgressDialog);
    }

    /**
     * 通过手机号或电子包裹箱号获取地址信息
     */
    private void requestAddressByPhoneOrParcelNo(String json) {
        new OkHttpResolve(this);
        aRecipientsPresenter.attach(this);
        aRecipientsPresenter.postAddressByPhoneOrParcelNo(json, this, lazyLoadProgressDialog);
    }

    /**
     * 门牌号名初始化请求数据
     */
    private void requestDataTablet(String json) {
        new OkHttpResolve(this);
        aRecipientsTabletPresenter.attach(this);
        aRecipientsTabletPresenter.postRecipientsTabletResult(json, this, lazyLoadProgressDialog);
    }

    //显示选择地址弹窗
    private void showSelectReceiptAddressDialog(List<RecipientsPhoneBean.AddressBean> list) {
        final SelectReceiptAddressDialog.Builder builder = new SelectReceiptAddressDialog.Builder(this, list);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                RecipientsPhoneBean.AddressBean address = builder.selectAddress();
                if (address != null) {
                    dialog.dismiss();
                    setValue(address);
                } else {
                    SkipIntentUtil.toastShow(RecipientsMessageEnteringActivity.this, "请选择地址！");
                }

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

    @Override
    public void onASaveRecipientsFinish(Object o) {
        RecipientsBean recipientsBean = (RecipientsBean) o;
        if (recipientsBean != null) {
            intent = new Intent(this, AdministratorRecipientsActivity.class);
            RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, getIntent().getStringExtra("source"));
            SkipIntentUtil.isIntentHomeSource(getIntent().getStringExtra("home_source"), intent);
            intent.putExtra("workstationId", recipientsBean.getExpressAccept().getWorkstation().getId());
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onAAddressByPhoneOrParcelNoFinish(Object o) {
        RecipientsPhoneBean recipientsPhoneBean = (RecipientsPhoneBean) o;
        if (recipientsPhoneBean != null) {
            if (recipientsPhoneBean.isIfUserExit() == true) {
                isIfUserExit = true;
                srName.setEnabled(false);
                selectHouse.setVisibility(View.GONE);
                List<RecipientsPhoneBean.AddressBean> address = recipientsPhoneBean.getAddress();
                if (address.size() == 1) { //  有问题
                    setValue(address.get(0));
                } else {
                    showSelectReceiptAddressDialog(address);
                }
            } else if (recipientsPhoneBean.isIfPhone() == false && recipientsPhoneBean.isIfUserExit() == false) {
                srName.setEnabled(false);
                selectHouse.setVisibility(View.GONE);
                houseTv.setText("");
                srName.setText("");
                regional.setText("");
                SkipIntentUtil.toastShow(this, "请正确输入电子包裹箱号或手机号！");
            } else {
                isIfUserExit = false;
                srName.setEnabled(true);
                selectHouse.setVisibility(View.VISIBLE);
                tabletName.setVisibility(View.VISIBLE);
                if (regionalSplit != null) {
                    if (regionalSplit.length == 7) {
                        regional.setText(regionalSplit[0] + " " + regionalSplit[1] + " " + regionalSplit[2] + " " + regionalSplit[3]);
                    } else if (regionalSplit.length == 6) {
                        regional.setText(regionalSplit[0] + " " + regionalSplit[1] + " " + regionalSplit[2]);
                    }
                }
                houseTv.setText("");
                srName.setText("");
            }
        }
    }

    /**
     * 设置查询出来的参数
     *
     * @param addressBean
     */
    private void setValue(RecipientsPhoneBean.AddressBean addressBean) {
        srName.setText(addressBean.getName());
        recipientsSaveBean.setId(addressBean.getId());
        String phone = addressBean.getPhone();
        String parcelNo = addressBean.getParcelNo();
        if (TelNumMatch.isPhone(phoneElectronicTv.getText().toString()) == true) {
            if (parcelNo != null) {
                phoneElectronicTv.setText(phone + "(" + parcelNo + ")");
            } else {
                phoneElectronicTv.setText(phone);
            }
        } else {
            phoneElectronicTv.setText(parcelNo + "(" + phone + ")");
        }
        phoneElectronicTv.setSelection(phoneElectronicTv.getText().length());
        handler.removeCallbacks(delayRun);
        String remarks = addressBean.getRemarks();
        if (remarks != null) {
            String[] split = remarks.split(",");
            if (split.length == 7) {
                regional.setText(split[0] + " " + split[1] + " " + split[2] + " " + split[3]);
                houseTv.setText(split[4] + split[5] + split[6]);
            } else if (split.length == 6) {
                regional.setText(split[0] + " " + split[1] + " " + split[2]);
                houseTv.setText(split[3] + split[4] + split[5]);
            } else if (split.length == 4) {
                regional.setText(split[0] + " " + split[1] + " " + split[2]);
                houseTv.setText(split[3]);
            } else if (split.length == 3) {
                regional.setText(split[0] + " " + split[1]);
                houseTv.setText(split[2]);
            }
        }
    }

    @Override
    public void onTabletListFinish(Object o) {
        RecipientsTabletBean recipientsTabletBean = (RecipientsTabletBean) o;
        if (recipientsTabletBean != null) {
            houseSelect = recipientsTabletBean.getHouse();
            initJsonDataTablet(houseSelect);
            showPickerView();
        }
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = tabletoptions1Items.get(options1).getPickerViewText() +
                        tabletoptions2Items.get(options1).get(options2) +
                        tabletoptions3Items.get(options1).get(options2).get(options3);
                houseTv.setTextColor(Color.parseColor("#333333"));
                houseTv.setText(tx);
                tabletName.setVisibility(View.GONE);
                for (int i = 0; i < tabletoptions1Items.size(); i++) {
                    if (tabletoptions1Items.get(i).getName().equals(tabletoptions1Items.get(options1).getPickerViewText())) {
                        houseOptions[0] = tabletoptions1Items.get(i).getName();
                    }
                    if (tabletoptions1Items.get(i).getNodes() != null) {
                        for (int j = 0; j < tabletoptions1Items.get(i).getNodes().size(); j++) {
                            if (tabletoptions1Items.get(i).getNodes().get(j).getName().equals(tabletoptions2Items.get(options1).get(options2))) {
                                houseOptions[1] = tabletoptions1Items.get(i).getNodes().get(j).getName();
                            }
                            if (tabletoptions1Items.get(i).getNodes().get(j).getNodes() != null) {
                                for (int k = 0; k < tabletoptions1Items.get(i).getNodes().get(j).getNodes().size(); k++) {
                                    if (tabletoptions1Items.get(i).getNodes().get(j).getNodes().get(k).getName().equals(tabletoptions3Items.get(options1).get(options2).get(options3))) {
                                        houseOptions[2] = tabletoptions1Items.get(i).getNodes().get(j).getNodes().get(k).getName();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })

                .setTitleText("门牌号选择")
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
        pvOptions.setPicker(tabletoptions1Items, tabletoptions2Items, tabletoptions3Items);//三级选择器
        if (houseOptions != null && houseOptions.length > 0) {
            for (int i = 0; i < tabletoptions1Items.size(); i++) {
                if (tabletoptions1Items.get(i).getName().equals(houseOptions[0])) {
                    for (int j = 0; j < tabletoptions1Items.get(i).getNodes().size(); j++) {
                        if (tabletoptions1Items.get(i).getNodes().get(j).getName().equals(houseOptions[1])) {
                            for (int k = 0; k < tabletoptions1Items.get(i).getNodes().get(j).getNodes().size(); k++) {
                                if (tabletoptions1Items.get(i).getNodes().get(j).getNodes().get(k).getName().equals(houseOptions[2])) {
                                    pvOptions.setSelectOptions(i, j, k);
                                }
                            }
                        }
                    }
                }
            }
        }
        pvOptions.show();
    }

    /**
     * 门牌号数据解析
     *
     * @param house
     */
    private void initJsonDataTablet(List<RecipientsTabletBean.HouseBean> house) {
        /**
         * 添加门牌号数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        tabletoptions1Items = house;

        for (int i = 0; i < house.size(); i++) {//遍历楼
            List<String> HouseList = new ArrayList<>();//该楼列表（第二级）
            List<List<String>> Province_HouseList = new ArrayList<>();//该楼的所有列表（第三级）

            if (house.get(i).getNodes() != null) {
                for (int c = 0; c < house.get(i).getNodes().size(); c++) {//遍历该楼的所有
                    String HouseName = house.get(i).getNodes().get(c).getName();
                    HouseList.add(HouseName);//添加楼

                    List<String> City_AreaList = new ArrayList<>();//该楼的所有列表

                    //如果无数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (house.get(i).getNodes().get(c).getNodes() == null
                            || house.get(i).getNodes().get(c).getNodes().size() == 0) {
                        City_AreaList.add("");
                    } else {
                        for (int d = 0; d < house.get(i).getNodes().get(c).getNodes().size(); d++) {//该楼对应所有数据
                            String AreaName = house.get(i).getNodes().get(c).getNodes().get(d).getName();
                            City_AreaList.add(AreaName);//添加该楼所有数据
                        }
                    }
                    Province_HouseList.add(City_AreaList);//添加该楼所有数据
                }
            }
            /**
             * 添加单元数据
             */
            tabletoptions2Items.add(HouseList);

            /**
             * 添加门牌号数据
             */
            tabletoptions3Items.add(Province_HouseList);
        }
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.e(TAG, "初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            SkipIntentUtil.toastShow(RecipientsMessageEnteringActivity.this, "开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            SkipIntentUtil.toastShow(RecipientsMessageEnteringActivity.this, error.getPlainDescription(false));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            SkipIntentUtil.toastShow(RecipientsMessageEnteringActivity.this, "结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            SkipIntentUtil.toastShow(RecipientsMessageEnteringActivity.this, "当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        index++;
        if (index == 1) {
            //判断说的话是否是数字
            Pattern pattern = Pattern.compile("[0-9]*");
            String result = resultBuffer.toString();
            Matcher matcher = pattern.matcher(result);
            int length = result.length();
            if (matcher.matches()) {
                if ((length >= 4 && length <= 10) || TelNumMatch.isPhone(result) == true) {
                    phoneElectronicTv.setText(result);
                    phoneElectronicTv.setSelection(phoneElectronicTv.length());
                } else {
                    SkipIntentUtil.toastShow(this, "只能说手机号或电子包裹箱号！");
                }
            } else {
                SkipIntentUtil.toastShow(this, "只能说数字！");
            }
        }
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            mIatDialog.dismiss();
            SkipIntentUtil.toastShow(RecipientsMessageEnteringActivity.this, error.getPlainDescription(false) + "...");
        }

    };

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "5000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "500"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
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
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(this);
        SkipIntentUtil.toastStop();
        super.onPause();
        handler.removeCallbacks(delayRun);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        aRecipientsPresenter.dettach();
        handler.removeCallbacks(delayRun);
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }
}
