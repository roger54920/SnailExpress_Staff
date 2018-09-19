package com.example.me_jie.snailexpress_staff.mvp;

/**
 * Created by me-jie on 2017/4/27.
 * 站长/员工寄件
 */

public interface ProxySenderView<T> {
    void onProxyUserSendApplyFinish(T t); //站信息
    void onSaveProxySenderFinish(T t); //确认寄件

}
