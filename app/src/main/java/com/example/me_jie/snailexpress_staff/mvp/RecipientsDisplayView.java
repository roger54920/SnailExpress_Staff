package com.example.me_jie.snailexpress_staff.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 站长收件列表
 */

public interface RecipientsDisplayView<T>{
    void onARecipientsDisplayListFinish(T t); //站长收件列表展示
    void onARecipientsDisplayBeanFinish(T t);//Item收件详情
}
