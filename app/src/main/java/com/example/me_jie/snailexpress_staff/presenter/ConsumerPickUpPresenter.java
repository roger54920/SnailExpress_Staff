package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ConsumerPickUpView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 已注册用户自提
 */

public class ConsumerPickUpPresenter implements BasePresenter<ConsumerPickUpView>{
    private ConsumerPickUpView view;
    /**
     * 收件列表Item详情
     * @param json
     */
    public void consumerPickUpResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/setConsumerPickUp", json, RecipientsBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                RecipientsBean recipientsBean = (RecipientsBean) result;
                if(recipientsBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(recipientsBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onConsumerPickUpBeanFinish(recipientsBean);
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
    public void attach(ConsumerPickUpView view) {
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
