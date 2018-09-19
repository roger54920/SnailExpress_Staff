package com.example.me_jie.snailexpress_staff.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.me_jie.snailexpress_staff.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        //延迟2S跳转
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
//            }
//        }, 2000);
    }
}
