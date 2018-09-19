package com.example.me_jie.snailexpress_staff.presenter;


import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.ExpressRobSingleBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsDisplayBean;
import com.example.me_jie.snailexpress_staff.bean.SendDisplayBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.HomeShowDataMoreView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 个人信息维护
 */

public class HomeShowDataMorePresenter implements BasePresenter<HomeShowDataMoreView>{
    private HomeShowDataMoreView view;

    /**
     * 抢单列表
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonOrderListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/index/orderList", json, ExpressRobSingleBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                ExpressRobSingleBean expressRobSingleBean = (ExpressRobSingleBean) result;
                if(expressRobSingleBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(expressRobSingleBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onHomeShowDataOrderListFinish(expressRobSingleBean);
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
     * 收件列表
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonReceiveExpressAcceptListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/index/receiveExpressAcceptList", json, RecipientsDisplayBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                RecipientsDisplayBean recipientsDisplayBean = (RecipientsDisplayBean) result;
                if(recipientsDisplayBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(recipientsDisplayBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onHomeShowDataReceiveExpressAcceptListFinish(recipientsDisplayBean);
                        }
                    }else if(recipientsDisplayBean.getStatus().equals("2")|| recipientsDisplayBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,recipientsDisplayBean.getMsg());
                    }else if(recipientsDisplayBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, recipientsDisplayBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    /**
     * 寄件列表
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonReceiveExpressSendListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/index/receiveExpressSendList", json, SendDisplayBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                SendDisplayBean sendDisplayBean = (SendDisplayBean) result;
                if(sendDisplayBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(sendDisplayBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onHomeShowDataReceiveExpressSendListFinish(sendDisplayBean);
                        }
                    }else if(sendDisplayBean.getStatus().equals("2")|| sendDisplayBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,sendDisplayBean.getMsg());
                    }else if(sendDisplayBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, sendDisplayBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(HomeShowDataMoreView view) {
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
