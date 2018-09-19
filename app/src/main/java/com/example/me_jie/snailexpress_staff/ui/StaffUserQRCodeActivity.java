package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.utils.EncodingUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 员工用户扫码
 */
public class StaffUserQRCodeActivity extends AutoLayoutActivity {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.qr_code)
    ImageView qrCode;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.address)
    TextView address;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_user_qrcode);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);


        intent = getIntent();
        Constants.SOURCE = intent.getStringExtra("source");
        if (Constants.SOURCE != null) {
            Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_icon);
            Bitmap bitmap;
            switch (Constants.SOURCE) {
                case "then_allocation_rq":
                    bitmap = EncodingUtil.createQRCode(intent.getStringExtra("lgisticCode"), 435, 435, logoBitmap);
                    qrCode.setImageBitmap(bitmap);
                    expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
                    break;
                case "staff_home":
                    bitmap = EncodingUtil.createQRCode(intent.getStringExtra("lgisticCode"), 435, 435, logoBitmap);
                    qrCode.setImageBitmap(bitmap);
                    expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
                    break;
                case "courier_service_parcel_rq":
                    bitmap = EncodingUtil.createQRCode(intent.getStringExtra("senderId"), 435, 435, logoBitmap);
                    qrCode.setImageBitmap(bitmap);
                    expressNumber.setText("收件人电话："+intent.getStringExtra("receivermobile"));
                    break;

            }
            haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
            address.setText(intent.getStringExtra("recipients"));
            name.setText(intent.getStringExtra("receivername"));
        }
    }

    @OnClick({R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                sourceReturn();
                break;
        }
    }
    /**
     * 返回来源页面
     */
    private void sourceReturn(){
        Constants.SOURCE = getIntent().getStringExtra("source");
        switch (Constants.SOURCE) {
            case "then_allocation_rq":
                SkipIntentUtil.skipIntent(this, ThenAllocationParcelActivity.class);
                break;
            case "courier_service_parcel_rq":
                SkipIntentUtil.skipIntent(this, StaffCourierServiceParcelActivity.class);
                break;
            case "staff_home":
                intent = new Intent(this, MipcaActivityCapture.class);
                intent.putExtra("source", Constants.SOURCE );
                startActivity(intent);
                break;
        }
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            sourceReturn();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
