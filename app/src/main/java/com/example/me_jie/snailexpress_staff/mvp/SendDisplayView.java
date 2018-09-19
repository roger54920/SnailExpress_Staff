package com.example.me_jie.snailexpress_staff.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 寄件列表
 */

public interface SendDisplayView<T>{
    void onSendDisplayListFinish(T t); //寄件列表展示
    void onSendDisplayBeanFinish(T t);//Item寄件详情
}
