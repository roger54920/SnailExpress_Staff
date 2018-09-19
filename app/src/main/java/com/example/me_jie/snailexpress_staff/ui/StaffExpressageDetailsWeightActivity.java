package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.SendDetailsBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.SendOperationView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.SendOperationPresenter;
import com.example.me_jie.snailexpress_staff.utils.GadgetUtil;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 员工快递详情 重量
 */
public class StaffExpressageDetailsWeightActivity extends AutoLayoutActivity implements SendOperationView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.rechargeable_btn)
    Button rechargeableBtn;
    @InjectView(R.id.parcel_weighs_edt)
    EditText parcelWeighsEdt;

    private SendOperationPresenter sendOperationPresenter = new SendOperationPresenter();//员工称重
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_expressage_details_weight);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        GadgetUtil.setPricePoint(parcelWeighsEdt);
    }

    /**
     * 寄件员工称重
     *
     * @param json
     */
    private void getSendEmployeesWeighingResult(String json) {
        new OkHttpResolve(this);
        sendOperationPresenter.attach(this);
        sendOperationPresenter.postJsonSendOperationResult(json, this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.rechargeable_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.rechargeable_btn:
                String parcelWeight = parcelWeighsEdt.getText().toString();
                if (!TextUtils.isEmpty(parcelWeight)) {
                    String lastStr = parcelWeight.substring(parcelWeight.length() - 1, parcelWeight.length());
                    if (!lastStr.equals(".")) {
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        HashMap params = new HashMap<>();
                        params.put("goodsWeight", parcelWeight);
                        params.put("cost", "20");
                        params.put("senderId", getIntent().getStringExtra("senderId"));
                        params.put("taskId", getIntent().getStringExtra("taskId"));
                        params.put("procInsId", getIntent().getStringExtra("procInsId"));
                        getSendEmployeesWeighingResult(new JSONObject(params).toString());
                    } else {
                        SkipIntentUtil.toastShow(this, "请正确输入包裹重量！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请输入包裹重量！");
                }
                break;
        }
    }

    @Override
    public void onSendOperationFinish(Object o) {
        SendDetailsBean sendDetailsBean = (SendDetailsBean) o;
        if (sendDetailsBean != null) {
            Constants.SOURCE = getIntent().getStringExtra("source");
            switch (Constants.SOURCE) {
                case "weighingDetermination":
                    SkipIntentUtil.skipIntent(this, StaffCourierServiceParcelActivity.class);
                    finish();
                    break;
                case "weighingDetermination2":
                    SkipIntentUtil.skipIntent(this, SendExpressageActivity.class);
                    finish();
                    break;
            }

//            SkipIntentUtil.skipIntent(this, StaffCourierServiceParcelActivity.class);
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
        sendOperationPresenter.dettach();
    }
}
