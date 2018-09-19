package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsPhoneBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ARecipientsEnteringView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 站长收件录入
 */

public class ARecipientsPresenter implements BasePresenter<ARecipientsEnteringView>{
    private ARecipientsEnteringView view;
    /**
     * 保存收件站信息  确认信息
     * @param json
     */
    public void postSaveRecipientsResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/appManageTask", json, RecipientsBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                RecipientsBean recipientsBean = (RecipientsBean) result;
                if(recipientsBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                        if(recipientsBean.getStatus().equals("1")) {
                            if (view != null) {
                                view.onASaveRecipientsFinish(recipientsBean);
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
    /**
     * 确认入库
     * @param json
     */
    public void postGodownEntryRecipientsResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/appManageTask", json, RecipientsBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                RecipientsBean recipientsBean = (RecipientsBean) result;
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(recipientsBean.getStatus()!=null) {
                        if (recipientsBean.getStatus().equals("1")) {
                            if (view != null) {
                                view.onASaveRecipientsFinish(recipientsBean);
                            }
                        } else if (recipientsBean.getStatus().equals("2")|| recipientsBean.getStatus().equals("3")) {
                            SkipIntentUtil.toastShow(activity, recipientsBean.getMsg());
                        } else if(recipientsBean.getStatus().equals("4")){
                            SkipIntentUtil.returnLogin(activity, recipientsBean.getMsg());
                        }else {
                            SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                        }
                }
            }
        });
    }
    /**
     * 通过手机号或电子包裹箱号获取地址信息
     * @param json
     */
    public void postAddressByPhoneOrParcelNo(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/address/getAddressByPhoneOrParcelNo", json, RecipientsPhoneBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                RecipientsPhoneBean recipientsPhoneBean = (RecipientsPhoneBean) result;
                if(recipientsPhoneBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(recipientsPhoneBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onAAddressByPhoneOrParcelNoFinish(recipientsPhoneBean);
                        }
                    }else if(recipientsPhoneBean.getStatus().equals("2")|| recipientsPhoneBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,recipientsPhoneBean.getMsg());
                    }else if(recipientsPhoneBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, recipientsPhoneBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(ARecipientsEnteringView view) {
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
