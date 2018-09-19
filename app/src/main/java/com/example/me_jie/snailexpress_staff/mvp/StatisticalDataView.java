package com.example.me_jie.snailexpress_staff.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 首页统计数据
 */

public interface StatisticalDataView<T>{
    void onStatisticalDataCountFinish(T t);//统计条数
    void onStatisticalDataShowStationmasterFinish(T t);//首页显示三种数据
}
