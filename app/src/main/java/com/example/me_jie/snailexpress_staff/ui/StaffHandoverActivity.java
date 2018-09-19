package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 员工交接工作
 */
public class StaffHandoverActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_handover);
        StatusBarUtil.SetStatusBar(this);
    }
}
