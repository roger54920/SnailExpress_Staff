package com.example.me_jie.snailexpress_staff.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 首页展示数据更多
 */

public interface HomeShowDataMoreView<T>{
    void onHomeShowDataOrderListFinish(T t);//抢单
    void onHomeShowDataReceiveExpressAcceptListFinish(T t);//收件
    void onHomeShowDataReceiveExpressSendListFinish(T t);//寄件
}
