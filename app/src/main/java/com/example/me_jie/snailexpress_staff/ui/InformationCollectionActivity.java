package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.custom.RadioGroup;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 信息采集
 */
public class InformationCollectionActivity extends AutoLayoutActivity {


    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.input_information_btn)
    Button inputInformationBtn;
    @InjectView(R.id.myRadioGroup)
    RadioGroup myRadioGroup;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_collection);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
    }

    @OnClick({R.id.return_arrows, R.id.input_information_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.input_information_btn:
                SkipIntentUtil.skipIntent(this,AdministratorRecipientsDisplayActivity.class);
                break;
        }
    }

}
