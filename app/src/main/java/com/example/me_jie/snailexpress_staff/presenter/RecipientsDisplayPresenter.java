package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.RecipientsDisplayBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.RecipientsDisplayView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 站长收件列表
 */

public class RecipientsDisplayPresenter implements BasePresenter<RecipientsDisplayView>{
    private RecipientsDisplayView view;

    /**
     * 收件列表
     * @param json
     */
    public void postJsonDisplayListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/list", json, RecipientsDisplayBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                RecipientsDisplayBean recipientsDisplayBean = (RecipientsDisplayBean) result;
                if(recipientsDisplayBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(recipientsDisplayBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onARecipientsDisplayListFinish(recipientsDisplayBean);
                        }
                    }else if(recipientsDisplayBean.getStatus().equals("2")|| recipientsDisplayBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, recipientsDisplayBean.getMsg());
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
     * 收件列表Item详情
     * @param json
     */
    public void postJsonDisplayResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/getExpressAcceptById", json, RecipientsBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                RecipientsBean recipientsBean = (RecipientsBean) result;
                if(recipientsBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(recipientsBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onARecipientsDisplayBeanFinish(recipientsBean);
                        }
                    }else if(recipientsBean.getStatus().equals("2")|| recipientsBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,recipientsBean.getMsg());
                    }else if(recipientsBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, recipientsBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(RecipientsDisplayView view) {
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
