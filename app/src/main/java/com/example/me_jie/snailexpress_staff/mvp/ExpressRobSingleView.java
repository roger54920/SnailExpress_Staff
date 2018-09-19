package com.example.me_jie.snailexpress_staff.mvp;

/**
 * Created by me-jie on 2017/4/27.
 * 抢单
 */

public interface ExpressRobSingleView<T> {
    void onExpressRobSingleListFinish(T t); //抢单列表
    void onIfCourierContinueAbleGetQRCode(T t);//员工手上是否有单未完成
    void onExpressRobSingleFinish(T t); //抢单
    void onStaffStatisticalDataFinish(T t);//员工首页统计数据
}
