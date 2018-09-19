package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 员工快件揽收包裹
 */
public class StaffTakingParcelActivity extends AutoLayoutActivity {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_taking_parcel);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SkipIntentUtil.skipIntent(this,StaffCourierServiceParcelActivity.class);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.return_arrows)
    public void onViewClicked() {
        SkipIntentUtil.skipIntent(this,StaffCourierServiceParcelActivity.class);
        finish();
    }
}
