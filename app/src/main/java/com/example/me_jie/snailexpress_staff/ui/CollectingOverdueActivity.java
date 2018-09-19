package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 代收货款
 */
public class CollectingOverdueActivity extends AutoLayoutActivity {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.confirmation_collection_overdue_btn)
    Button confirmationCollectionOverdueBtn;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecting_overdue);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
    }

    @OnClick({R.id.return_arrows, R.id.confirmation_collection_overdue_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.confirmation_collection_overdue_btn:
                intent = new Intent(CollectingOverdueActivity.this,AdministratorRecipientsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
