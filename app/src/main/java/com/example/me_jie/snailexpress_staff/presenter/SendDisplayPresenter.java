package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.SendDetailsBean;
import com.example.me_jie.snailexpress_staff.bean.SendDisplayBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.SendDisplayView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 寄件列表
 */

public class SendDisplayPresenter implements BasePresenter<SendDisplayView>{
    private SendDisplayView view;

    /**
     * 寄件列表
     * @param json
     */
    public void postJsonDisplayListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressSend/listSender", json, SendDisplayBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                SendDisplayBean sendDisplayBean = (SendDisplayBean) result;
                if(sendDisplayBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(sendDisplayBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSendDisplayListFinish(sendDisplayBean);
                        }
                    }else if(sendDisplayBean.getStatus().equals("2")|| sendDisplayBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, sendDisplayBean.getMsg());
                    }else if(sendDisplayBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, sendDisplayBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    /**
     * 寄件列表Item详情
     * @param json
     */
    public void postJsonDisplayResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressSend/detalSender", json, SendDetailsBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                SendDetailsBean sendDetailsBean = (SendDetailsBean) result;
                if(sendDetailsBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(sendDetailsBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSendDisplayBeanFinish(sendDetailsBean);
                        }
                    }else if(sendDetailsBean.getStatus().equals("2")|| sendDetailsBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,sendDetailsBean.getMsg());
                    }else if(sendDetailsBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, sendDetailsBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(SendDisplayView view) {
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
