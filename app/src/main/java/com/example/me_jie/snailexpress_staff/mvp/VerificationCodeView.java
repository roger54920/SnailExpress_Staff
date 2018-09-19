package com.example.me_jie.snailexpress_staff.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 验证验证码
 */

public interface VerificationCodeView<T>{
    void onBeanVerificationCodeFinish(T t);//获取验证码
}
