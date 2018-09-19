package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.ExpressRobSingleBean;
import com.example.me_jie.snailexpress_staff.bean.StatisticalDataBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ExpressRobSingleView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 员工/保安 抢单
 */

public class ExpressRobSinglePresenter implements BasePresenter<ExpressRobSingleView>{
    private ExpressRobSingleView view;

    /**
     * 员工手上是否有单未完成
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonIfCourierContinueResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/order/ifCourierContinueAbleGetQRCode", json, ExpressRobSingleBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                ExpressRobSingleBean expressRobSingleBean = (ExpressRobSingleBean) result;
                if(expressRobSingleBean!=null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (expressRobSingleBean.getStatus().equals("1")) {
                        if (expressRobSingleBean != null) {
                            if (view != null) {
                                view.onIfCourierContinueAbleGetQRCode(expressRobSingleBean);
                            }
                        }
                    }else if(expressRobSingleBean.getStatus().equals("2")|| expressRobSingleBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,expressRobSingleBean.getMsg());
                    }else if(expressRobSingleBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, expressRobSingleBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    /**
     * 抢单列表
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonExpressRobSingleListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/order/getOrderList", json, ExpressRobSingleBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                ExpressRobSingleBean expressRobSingleBean = (ExpressRobSingleBean) result;
                if(expressRobSingleBean!=null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (expressRobSingleBean.getStatus().equals("1")) {
                        if (expressRobSingleBean != null) {
                            if (view != null) {
                                view.onExpressRobSingleListFinish(expressRobSingleBean);
                            }
                        }
                    }else if(expressRobSingleBean.getStatus().equals("2")|| expressRobSingleBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,expressRobSingleBean.getMsg());
                    }else if(expressRobSingleBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, expressRobSingleBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    /**
     * 抢单
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonExpressRobSingleResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/order/grabOrder", json, ExpressRobSingleBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                ExpressRobSingleBean expressRobSingleBean = (ExpressRobSingleBean) result;
                if(expressRobSingleBean!=null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (expressRobSingleBean.getStatus().equals("1")) {
                        if (expressRobSingleBean != null) {
                            if (view != null) {
                                view.onExpressRobSingleFinish(expressRobSingleBean);
                            }
                        }
                    }else if(expressRobSingleBean.getStatus().equals("2") || expressRobSingleBean.getStatus().equals("3")){
                        if (view != null) {
                            view.onExpressRobSingleFinish(expressRobSingleBean);
                        }
                        SkipIntentUtil.toastShow(activity,expressRobSingleBean.getMsg());
                    }else if(expressRobSingleBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, expressRobSingleBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    /**
     * 员工首页统计数据
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonStaffStatisticalDataResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/index/getPerformanceCount", json, StatisticalDataBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                StatisticalDataBean statisticalDataBean = (StatisticalDataBean) result;
                if(statisticalDataBean!=null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (statisticalDataBean.getStatus().equals("1")) {
                        if (statisticalDataBean != null) {
                            if (view != null) {
                                view.onStaffStatisticalDataFinish(statisticalDataBean);
                            }
                        }
                    }else if(statisticalDataBean.getStatus().equals("2") || statisticalDataBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,statisticalDataBean.getMsg());
                    }else if(statisticalDataBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, statisticalDataBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(ExpressRobSingleView view) {
        this.view =  view;
    }

    /**
     * 不调用的时候进行 清空
     */
    @Override
    public void dettach() {
        if(view!=null){
            view = null;
        }
    }
}
