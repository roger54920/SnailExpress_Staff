package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.custom.RatingBar;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 用户扫码确认
 */
public class UserScanQRCodeAffirmActivity extends AutoLayoutActivity {

    @InjectView(R.id.ratingBar)
    RatingBar ratingBar;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.next_step_btn)
    Button nextStepBtn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_scan_qrcode_affirm);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        ratingBar.setClickable(false);//设置可否点击
        ratingBar.setStar(0);//默认设置显示的星星个数
        ratingBar.setStepSize(RatingBar.StepSize.Half);//设置每次点击增加一颗星还是半颗星
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
                Log.d("RatingBar", "RatingBar-Count=" + ratingCount);
            }
        });
    }

    @OnClick({R.id.return_arrows, R.id.next_step_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.next_step_btn:
                SkipIntentUtil.skipIntent(this,InformationCollectionActivity.class);
                break;
        }
    }
}
