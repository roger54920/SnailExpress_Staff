package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 寄件信息录入
 */
public class SendMessageEnteringActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message_entering);
        StatusBarUtil.SetStatusBar(this);
    }
}
