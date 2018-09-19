package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.RecipientsDisplayBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.AcceptPayListView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 代付货款
 */

public class AcceptPayListPresenter implements BasePresenter<AcceptPayListView> {
    private AcceptPayListView view;

    public void postJsonAcceptPayListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/acceptPayList", json, RecipientsDisplayBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                RecipientsDisplayBean recipientsDisplayBean = (RecipientsDisplayBean) result;
                if(recipientsDisplayBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(recipientsDisplayBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onAcceptPayListFinish(recipientsDisplayBean);
                        }
                    }else if(recipientsDisplayBean.getStatus().equals("2") || recipientsDisplayBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, recipientsDisplayBean.getMsg());
                    }else if(recipientsDisplayBean.getStatus().equals("4")){
                      SkipIntentUtil.returnLogin(activity,recipientsDisplayBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(AcceptPayListView view) {
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
