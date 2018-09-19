package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 保安 用户扫码
 */
public class PublicSecurityUserScanQRCodeActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_security_user_scan_qrcode);
        StatusBarUtil.SetStatusBar(this);
    }
}
