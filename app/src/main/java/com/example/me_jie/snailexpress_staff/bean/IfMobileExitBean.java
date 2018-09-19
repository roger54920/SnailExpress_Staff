package com.example.me_jie.snailexpress_staff.bean;

/**
 * Created by me-jie on 2017/4/17.
 * 手机号是否注册 实体类
 */

public class IfMobileExitBean extends BaseBean{
    /**
     * status : 1
     * verificationCode : 234567
     * ifMobileExit : false
     */

    private boolean ifMobileExit;


    public boolean isIfMobileExit() {
        return ifMobileExit;
    }

    public void setIfMobileExit(boolean ifMobileExit) {
        this.ifMobileExit = ifMobileExit;
    }
}
