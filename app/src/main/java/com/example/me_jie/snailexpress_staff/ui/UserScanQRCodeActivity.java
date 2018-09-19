package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 扫码
 */
public class UserScanQRCodeActivity extends AutoLayoutActivity {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.qr_code)
    ImageView qrCode;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_scan_qrcode);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
    }

    @OnClick({R.id.return_arrows, R.id.qr_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.qr_code:
                SkipIntentUtil.skipIntent(this,UserScanQRCodeAffirmActivity.class);
                break;
        }
    }
}
