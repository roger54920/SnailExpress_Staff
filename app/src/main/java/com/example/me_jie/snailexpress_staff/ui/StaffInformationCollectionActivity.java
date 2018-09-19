package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.custom.RadioGroup;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ARecipientsEnteringView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.ARecipientsPresenter;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 信息收集
 */
public class StaffInformationCollectionActivity extends AutoLayoutActivity implements ARecipientsEnteringView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.input_information_btn)
    Button inputInformationBtn;
    @InjectView(R.id.radioGroup_goodsSource)
    RadioGroup radioGroupGoodsSource;
    @InjectView(R.id.radioGroup_goodsType)
    RadioGroup radioGroupGoodsType;
    private Intent intent;
    private RadioButton goodsType_rb;  //物品类型
    private RadioButton goodsSource_rb;  //购物来源

    private String goodsType_tv;  //物品类型
    private String goodsSource_tv;  //购物来源

    private ARecipientsPresenter aRecipientsPresenter = new ARecipientsPresenter();//员工采集信息
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_information_collection);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);
        initViews();
    }

    private void initViews() {
        radioGroupGoodsType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                goodsType_rb = (RadioButton) findViewById(checkedId);
                goodsType_tv = goodsType_rb.getText().toString();
            }
        });
        radioGroupGoodsSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                goodsSource_rb = (RadioButton) findViewById(checkedId);
                goodsSource_tv = goodsSource_rb.getText().toString();
            }
        });
    }

    /**
     * 输入采集信息
     */
    private void requestInformationCollection(String json) {
        new OkHttpResolve(this);
        aRecipientsPresenter.attach(this);
        aRecipientsPresenter.postGodownEntryRecipientsResult(json, this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.input_information_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                sourceReturn();
                break;
            case R.id.input_information_btn:
                if (goodsSource_tv != null) {
                    if (goodsType_tv != null) {
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        try {
                            JSONObject jsonObject = new JSONObject();
                            JSONObject commodity = new JSONObject();
                            commodity.put("goodsType", goodsType_tv);
                            commodity.put("goodsSource", goodsSource_tv);
                            jsonObject.put("commodity", commodity);
                            jsonObject.put("id", getIntent().getStringExtra("expressAccept_id"));
                            jsonObject.put("expressParcel.id", getIntent().getStringExtra("expressParcel_id"));
                            jsonObject.put("act.taskId", getIntent().getStringExtra("act_taskId"));
                            jsonObject.put("act.procInsId", getIntent().getStringExtra("act_procInsId"));

                            requestInformationCollection(jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请选择产品类型！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请选择订购平台！");
                }
                break;
        }
    }

    /**
     * 返回来源页面
     */
    private void sourceReturn() {
        Constants.SOURCE = getIntent().getStringExtra("source");
        if (Constants.SOURCE.equals("doCourierInputInfo")) {
           finish();
        } else if (Constants.SOURCE.equals("administrator_home") || Constants.SOURCE.equals("administrator_recipients") || Constants.SOURCE.equals("staff_home")) {
            Intent intent = new Intent(this, MipcaActivityCapture.class);
            intent.putExtra("source", Constants.SOURCE);
            startActivity(intent);
            finish();
        } else {
            finish();
        }

    }

    @Override
    public void onASaveRecipientsFinish(Object o) {
        RecipientsBean recipientsBean = (RecipientsBean) o;
        if (recipientsBean != null) {
            Constants.SOURCE = getIntent().getStringExtra("source");
            switch (Constants.SOURCE) {
                case "doCourierInputInfo":
                    SkipIntentUtil.skipIntent(this, ThenAllocationParcelActivity.class);
                    finish();
                    break;
                case "acceptDisplay_doCourierInputInfo2":
                    SkipIntentUtil.successfulResultAccept(getIntent().getStringExtra("home_source"),this,intent);
                    break;
            }


        }
    }

    @Override
    public void onAAddressByPhoneOrParcelNoFinish(Object o) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            sourceReturn();
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
        aRecipientsPresenter.dettach();
    }
}
