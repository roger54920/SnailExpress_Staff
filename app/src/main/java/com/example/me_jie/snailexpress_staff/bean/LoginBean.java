package com.example.me_jie.snailexpress_staff.bean;

/**
 * Created by me-jie on 2017/4/25.
 */

public class LoginBean extends BaseBean{

    private User user;
    private long sessionTimeout;    //登陆成功后免登陆时间

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
