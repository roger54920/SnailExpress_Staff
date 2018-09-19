package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 保安签退
 */
public class PublicSecuritySignOutActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_security_sign_out);
        StatusBarUtil.SetStatusBar(this);
    }
}
