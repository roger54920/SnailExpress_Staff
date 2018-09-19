package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.StatisticalDataBean;
import com.example.me_jie.snailexpress_staff.mvp.StatisticalDataView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 首页统计数据
 */

public class StatisticalDataPresenter implements BasePresenter<StatisticalDataView> {
    private StatisticalDataView view;

    /**
     * 统计条数
     * @param json
     * @param activity
     */
    public void postJsonStatisticalDataCountResult(String json, final Activity activity) {
        OkHttpResolve.postJsonResult(Constants.TOP + "business/index/getCount", json, StatisticalDataBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                StatisticalDataBean statisticalDataBean = (StatisticalDataBean) result;
                if (statisticalDataBean != null) {
                    if (statisticalDataBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onStatisticalDataCountFinish(statisticalDataBean);
                        }
                    } else if (statisticalDataBean.getStatus().equals("2") || statisticalDataBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, statisticalDataBean.getMsg());
                    }else if(statisticalDataBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, statisticalDataBean.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    /**
     * 首页显示三种信息
     * @param json
     * @param activity
     */
    public void postJsonStatisticalDataShowStationmasterResult(String json, final Activity activity) {
        OkHttpResolve.postJsonResult(Constants.TOP + "business/index/showStationmasterList", json, StatisticalDataBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                StatisticalDataBean statisticalDataBean = (StatisticalDataBean) result;
                if (statisticalDataBean != null) {
                    if (statisticalDataBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onStatisticalDataShowStationmasterFinish(statisticalDataBean);
                        }
                    } else if (statisticalDataBean.getStatus().equals("2") || statisticalDataBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, statisticalDataBean.getMsg());
                    }else if(statisticalDataBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, statisticalDataBean.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(StatisticalDataView view) {
        this.view = view;
    }

    /**
     * 不调用的时候进行 清空
     */
    @Override
    public void dettach() {
        if (view != null) {
            view = null;
        }
    }
}
