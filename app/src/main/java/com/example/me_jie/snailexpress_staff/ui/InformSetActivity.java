package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.custom.SwitchButton;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 通知设置
 */
public class InformSetActivity extends AutoLayoutActivity {

    @InjectView(R.id.switch_button)
    SwitchButton switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform_set);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {

            }
        });
    }
}
