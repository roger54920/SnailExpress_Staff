package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.custom.ClipViewLayout;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 头像裁剪Activity
 */
public class ClipImageActivity extends AutoLayoutActivity {
    private static final String TAG = "ClipImageActivity";
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.clipViewLayout)
    ClipViewLayout clipViewLayout;
    @InjectView(R.id.btn_cancel)
    TextView btnCancel;
    @InjectView(R.id.bt_ok)
    TextView btOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        clipViewLayout.setVisibility(View.VISIBLE);
        //设置图片资源
        clipViewLayout.setImageSrc(getIntent().getData());
    }
    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        zoomedCropBitmap = clipViewLayout.clip();

        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @OnClick({R.id.return_arrows, R.id.btn_cancel, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.bt_ok:
                generateUriAndReturn();
                break;
        }
    }
}
