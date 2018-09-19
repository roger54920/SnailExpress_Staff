package com.example.me_jie.snailexpress_staff.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.AllRegionBean;
import com.example.me_jie.snailexpress_staff.bean.IfMobileExitBean;
import com.example.me_jie.snailexpress_staff.bean.SendSaveBean;
import com.example.me_jie.snailexpress_staff.custom.speech.setting.IatSettings;
import com.example.me_jie.snailexpress_staff.custom.speech.util.JsonParser;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.AllRegionView;
import com.example.me_jie.snailexpress_staff.mvp.IfMobileExitView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.AllRegionPresenter;
import com.example.me_jie.snailexpress_staff.presenter.IfMobileExitPresenter;
import com.example.me_jie.snailexpress_staff.utils.CacheUtils;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
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

/**
 * 寄件 新增收件人地址
 */
public class SendNewSiteActivity extends AutoLayoutActivity implements AllRegionView, IfMobileExitView {
    private static final String TAG = "SendNewSiteActivity";
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.fillout_title)
    TextView filloutTitle;
    @InjectView(R.id.input_name)
    EditText inputName;
    @InjectView(R.id.input_phone)
    EditText inputPhone;
    @InjectView(R.id.input_region)
    TextView inputRegion;
    @InjectView(R.id.input_address)
    EditText inputAddress;
    @InjectView(R.id.confirmation_btn)
    Button confirmationBtn;
    @InjectView(R.id.voice_dictation_name)
    ImageView voiceDictationName;
    @InjectView(R.id.voice_dictation_mobile)
    ImageView voiceDictationMobile;
    @InjectView(R.id.voice_dictation_detailed_address)
    ImageView voiceDictationDetailedAddress;

    private AllRegionPresenter allRegionPresenter = new AllRegionPresenter();//全国地址
    //全国地区选择
    private List<AllRegionBean.AreaBean> provinceAllBeanList;//省
    private List<AllRegionBean.AreaBean> options1AllItems = new ArrayList<>();
    private List<List<String>> options2AllItems = new ArrayList<>();
    private List<List<List<String>>> options3AllItems = new ArrayList<>();

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private SendSaveBean sendSaveBean = new SendSaveBean();//选择的地址ID
    private Intent intent;

    private String[] allRegionOptions = new String[3];//全国地区

    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    int ret = 0; // 函数调用返回值
    private int index;
    private boolean ifMobileExit = false;
    private IfMobileExitPresenter ifMobileExitPresenter = new IfMobileExitPresenter();//是否注册手机号
    private AsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_site);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);
        initViews();

    }

    private void initViews() {
        task = new AsyncTask<String, Void, List<AllRegionBean.AreaBean>>() {
            @Override
            protected List<AllRegionBean.AreaBean> doInBackground(String... params) {
                List<AllRegionBean.AreaBean> analysisJson = CacheUtils.analysisJson(SendNewSiteActivity.this);
                if (analysisJson != null) {
                    provinceAllBeanList = analysisJson;
                }
                return provinceAllBeanList;
            }
        }.execute();
        intent = getIntent();
        Constants.SOURCE = intent.getStringExtra("source");
        String source_return = intent.getStringExtra("source_return");
        if (Constants.SOURCE.equals("sender_message")) {
            filloutTitle.setText("添加寄件人");
            inputPhone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String phone = inputPhone.getText().toString();
                    if (TelNumMatch.isPhone(phone) == true) {
                        requestifMobileExit("{\"mobile\":\"" + phone + "\",\"roleName\":\"consumer\"}");
                    }
                }
            });
            if (!TextUtils.isEmpty(source_return) && source_return.equals(Constants.SOURCE)) {
                setAssignment();
            }
        } else {
            filloutTitle.setText("添加收件人");
            if (!TextUtils.isEmpty(source_return) && source_return.equals(Constants.SOURCE)) {
                setAssignment();
            }
        }
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);
        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME,
                Activity.MODE_PRIVATE);
    }

    /**
     * 根据收件人或寄件人进行赋值
     */
    private void setAssignment() {
        sendSaveBean.setReceiverprovincename(intent.getStringExtra("provincename"));
        sendSaveBean.setReceivercityname(intent.getStringExtra("cityname"));
        sendSaveBean.setReceiverexpareaname(intent.getStringExtra("expareaname"));
        String name = intent.getStringExtra("name");
        String moblie = intent.getStringExtra("moblie");
        String address = intent.getStringExtra("address");
        inputName.setText(name);
        inputPhone.setText(moblie);
        inputAddress.setText(address);
        inputName.setSelection(name.length());
        inputPhone.setSelection(moblie.length());
        inputAddress.setSelection(address.length());
        String receiverprovincename = sendSaveBean.getReceiverprovincename();
        String receivercityname = sendSaveBean.getReceivercityname();
        String receiverexpareaname = sendSaveBean.getReceiverexpareaname();
        inputRegion.setText(receiverprovincename + " " + receivercityname + " " + receiverexpareaname);
        allRegionOptions[0] = receiverprovincename;
        allRegionOptions[1] = receivercityname;
        allRegionOptions[2] = receiverexpareaname;

    }

    /**
     * 全国地区初始化请求数据
     */

    private void requestDataAllRegion() {
        new OkHttpResolve(this);
        allRegionPresenter.attach(this);
        allRegionPresenter.postAllRegionJsonResult("{\"id\":\" \"}", this, lazyLoadProgressDialog);
    }

    /**
     * 初始化是否注册手机号
     *
     * @param str
     */
    private void requestifMobileExit(String str) {
        new OkHttpResolve(this);
        ifMobileExitPresenter.attach(this);
        ifMobileExitPresenter.postJsonResult(str, this, null);
    }

    /**
     * 全国地区
     *
     * @param o
     */
    @Override
    public void onAllRegionListFinish(Object o) {
        final AllRegionBean newAddressRegionBean = (AllRegionBean) o;
        if (newAddressRegionBean != null) {
            provinceAllBeanList = newAddressRegionBean.getArea();
            if (provinceAllBeanList != null) {
                new AsyncTask<String, Void, AllRegionBean>() {
                    @Override
                    protected AllRegionBean doInBackground(String... params) {
                        CacheUtils.writeJson(SendNewSiteActivity.this, newAddressRegionBean, "nationalregions.txt", false, this);
                        return null;
                    }
                }.execute();
                initJsonDataRegion(provinceAllBeanList);
                ShowPickerView();
            }
        }
    }

    @OnClick({R.id.voice_dictation_name, R.id.voice_dictation_mobile, R.id.voice_dictation_detailed_address, R.id.return_arrows, R.id.input_region, R.id.confirmation_btn})
    public void onViewClicked(View view) {
        if (null == mIat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            Log.e(TAG, "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }
        switch (view.getId()) {
            case R.id.voice_dictation_name:
                setDictation("name");
                break;
            case R.id.voice_dictation_mobile:
                setDictation("mobile");
                break;
            case R.id.voice_dictation_detailed_address:
                setDictation("detailed_address");
                break;
            case R.id.return_arrows:
                finish();
                break;
            case R.id.input_region:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (provinceAllBeanList == null || provinceAllBeanList.size() == 0) {
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad0neMinute(lazyLoadProgressDialog);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (provinceAllBeanList == null || provinceAllBeanList.size() == 0) {
                                    requestDataAllRegion();
                                } else {
                                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                                    initJsonDataRegion(provinceAllBeanList);
                                    ShowPickerView();
                                }
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    initJsonDataRegion(provinceAllBeanList);
                    ShowPickerView();
                }
                break;
            case R.id.confirmation_btn:
                String inputname = inputName.getText().toString();
                String inputphone = inputPhone.getText().toString();
                String inputaddress = inputAddress.getText().toString();
                String inputregion = inputRegion.getText().toString();
                if (!TextUtils.isEmpty(inputname)) {
                    if (!TextUtils.isEmpty(inputphone)) {
                        if (!TextUtils.isEmpty(inputregion)) {
                            if (!inputaddress.equals("")) {
                                if (Constants.SOURCE.equals("sender_message")) {
                                    if (ifMobileExit == true) {
                                        senderReturnMessage(inputname, inputphone, sendSaveBean.getReceiverprovincename(),
                                                sendSaveBean.getReceivercityname(), sendSaveBean.getReceiverexpareaname(),
                                                inputaddress, Constants.SOURCE);
                                    } else {
                                        SkipIntentUtil.toastShow(this, "寄件人还未注册,请先注册！");
                                    }
                                } else {
                                    senderReturnMessage(inputname, inputphone, sendSaveBean.getReceiverprovincename(),
                                            sendSaveBean.getReceivercityname(), sendSaveBean.getReceiverexpareaname(),
                                            inputaddress, Constants.SOURCE);
                                }
                            } else {
                                SkipIntentUtil.toastShow(this, "请输入详细地址！");
                            }
                        } else {
                            SkipIntentUtil.toastShow(this, "请输入选择地区！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请输入手机号！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请输入姓名！");
                }
                break;
        }
    }

    /**
     * 寄件返回信息
     *
     * @param name
     * @param mobile
     * @param provincename
     * @param cityname
     * @param expareaname
     * @param address
     * @param source_return
     */
    private void senderReturnMessage(String name, String mobile, String provincename, String cityname, String expareaname, String address, String source_return) {
        intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("moblie", mobile);
        intent.putExtra("provincename", provincename);
        intent.putExtra("cityname", cityname);
        intent.putExtra("expareaname", expareaname);
        intent.putExtra("address", address);
        intent.putExtra("source_return", source_return);
        setResult(Constants.SUBSCRIPT_ZER0, intent);
        finish();
    }

    /**
     * 设置语音听写
     */
    private void setDictation(String type) {
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
            switch (type) {
                case "name":
                    mIatDialog.setListener(mNameRecognizerDialogListener);
                    break;
                case "mobile":
                    mIatDialog.setListener(mMobileRecognizerDialogListener);
                    break;
                case "detailed_address":
                    mIatDialog.setListener(mDetailedAddressRecognizerDialogListener);
                    break;
                default:
                    break;
            }
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
    }

    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                sendSaveBean = new SendSaveBean();
                //返回的分别是三个级别的选中位置
                String tx = options1AllItems.get(options1).getPickerViewText() + " " +
                        options2AllItems.get(options1).get(options2) + " " +
                        options3AllItems.get(options1).get(options2).get(options3);
                inputRegion.setTextColor(Color.parseColor("#333333"));
                inputRegion.setText(tx);
                if (provinceAllBeanList != null) {
                    for (int i = 0; i < provinceAllBeanList.size(); i++) {
                        if (provinceAllBeanList.get(i).getName().equals(options1AllItems.get(options1).getPickerViewText())) {
                            sendSaveBean.setReceiverprovincename(provinceAllBeanList.get(i).getName());
                            allRegionOptions[0] = provinceAllBeanList.get(i).getName();
                        }
                        for (int j = 0; j < provinceAllBeanList.get(i).getNodes().size(); j++) {
                            if (provinceAllBeanList.get(i).getNodes().get(j).getName().equals(options2AllItems.get(options1).get(options2))) {
                                sendSaveBean.setReceivercityname(provinceAllBeanList.get(i).getNodes().get(j).getName());
                                allRegionOptions[1] = provinceAllBeanList.get(i).getNodes().get(j).getName();
                            }
                            if (provinceAllBeanList.get(i).getNodes().get(j).getNodes() != null) {
                                for (int k = 0; k < provinceAllBeanList.get(i).getNodes().get(j).getNodes().size(); k++) {
                                    if (provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getName().equals(options3AllItems.get(options1).get(options2).get(options3))) {
                                        sendSaveBean.setReceiverexpareaname(provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getName());
                                        allRegionOptions[2] = provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getName();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })

                .setTitleText("地区选择")
                .setTitleSize(20)
                .setTitleColor(Color.parseColor("#333333"))
                .setTitleBgColor(Color.WHITE)
                .setCancelColor(Color.parseColor("#6fd1c8"))
                .setSubmitColor(Color.parseColor("#6fd1c8"))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(16)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(options1AllItems, options2AllItems, options3AllItems);//三级选择器
        if (allRegionOptions != null && allRegionOptions.length > 0) {
            for (int i = 0; i < options1AllItems.size(); i++) {
                if (options1AllItems.get(i).getName().equals(allRegionOptions[0])) {
                    for (int j = 0; j < options1AllItems.get(i).getNodes().size(); j++) {
                        if (options1AllItems.get(i).getNodes().get(j).getName().equals(allRegionOptions[1])) {
                            for (int k = 0; k < options1AllItems.get(i).getNodes().get(j).getNodes().size(); k++) {
                                if (options1AllItems.get(i).getNodes().get(j).getNodes().get(k).getName().equals(allRegionOptions[2])) {
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

    private void initJsonDataRegion(List<AllRegionBean.AreaBean> area) {
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1AllItems = area;

        for (int i = 0; i < area.size(); i++) {//遍历省份
            List<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            List<List<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            if (area.get(i).getNodes() != null) {
                for (int c = 0; c < area.get(i).getNodes().size(); c++) {//遍历该省份的所有城市
                    String CityName = area.get(i).getNodes().get(c).getName();
                    CityList.add(CityName);//添加城市

                    List<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (area.get(i).getNodes().get(c).getNodes() == null
                            || area.get(i).getNodes().get(c).getNodes().size() == 0) {
                        City_AreaList.add("");
                    } else {
                        for (int d = 0; d < area.get(i).getNodes().get(c).getNodes().size(); d++) {//该城市对应地区所有数据
                            String AreaName = area.get(i).getNodes().get(c).getNodes().get(d).getName();
                            City_AreaList.add(AreaName);//添加该城市所有地区数据
                        }

                    }
                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                }
            }
            /**
             * 添加城市数据
             */
            options2AllItems.add(CityList);

            /**
             * 添加地区数据
             */
            options3AllItems.add(Province_AreaList);
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
            SkipIntentUtil.toastShow(SendNewSiteActivity.this, "开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            SkipIntentUtil.toastShow(SendNewSiteActivity.this, error.getPlainDescription(false));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            SkipIntentUtil.toastShow(SendNewSiteActivity.this, "结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printMobileResult(results);

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            SkipIntentUtil.toastShow(SendNewSiteActivity.this, "当前正在说话，音量大小：" + volume);
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

    /**
     * 姓名听写
     *
     * @param results
     */
    private void printNameResult(RecognizerResult results) {
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
            inputName.setText(resultBuffer.toString());
            inputName.setSelection(inputName.length());
        }
    }

    /**
     * 听写UI监听器 姓名听写
     */
    private RecognizerDialogListener mNameRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printNameResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            mIatDialog.dismiss();
            SkipIntentUtil.toastShow(SendNewSiteActivity.this, error.getPlainDescription(false) + "...");
        }

    };

    /**
     * 手机号听写
     *
     * @param results
     */
    private void printMobileResult(RecognizerResult results) {
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
            if (matcher.matches()) {
                if (TelNumMatch.isPhone(result) == true) {
                    inputPhone.setText(result);
                    inputPhone.setSelection(inputPhone.length());
                } else {
                    SkipIntentUtil.toastShow(this, "只能说手机号！");
                }
            } else {
                SkipIntentUtil.toastShow(this, "只能说数字！");
            }
        }
    }

    /**
     * 听写UI监听器 手机号听写
     */
    private RecognizerDialogListener mMobileRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printMobileResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            mIatDialog.dismiss();
            SkipIntentUtil.toastShow(SendNewSiteActivity.this, error.getPlainDescription(false) + "...");
        }

    };

    /**
     * 详细地址听写
     *
     * @param results
     */
    private void printDetailedAddressResult(RecognizerResult results) {
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
            inputAddress.append(resultBuffer.toString());
            inputAddress.setSelection(inputAddress.length());
        }
    }

    /**
     * 听写UI监听器 详细地址
     */
    private RecognizerDialogListener mDetailedAddressRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printDetailedAddressResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            mIatDialog.dismiss();
            SkipIntentUtil.toastShow(SendNewSiteActivity.this, error.getPlainDescription(false) + "...");
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
    public void onBeanFinish(Object o) {
        IfMobileExitBean ifMobileExitBean = (IfMobileExitBean) o;
        if (ifMobileExitBean != null) {
            if (ifMobileExitBean.isIfMobileExit() == true) {
                ifMobileExit = true;
            } else {
                ifMobileExit = false;
                SkipIntentUtil.toastShow(this, "寄件人还未注册,请先注册！");
            }
        }
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
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(this);
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        allRegionPresenter.dettach();
        ifMobileExitPresenter.dettach();
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
        SkipIntentUtil.closeAsyncTask(task);
    }
}
