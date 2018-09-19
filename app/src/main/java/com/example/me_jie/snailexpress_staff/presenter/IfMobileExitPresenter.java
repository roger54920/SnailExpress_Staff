package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.IfMobileExitBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.IfMobileExitView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 手机号是否注册
 */

public class IfMobileExitPresenter implements BasePresenter<IfMobileExitView>{
    private IfMobileExitView view;
    public void postJsonResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/user/ifMobileExit", json, IfMobileExitBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                IfMobileExitBean ifMobileExit = (IfMobileExitBean) result;
                if(ifMobileExit!=null){
                    if(lazyLoadProgressDialog!=null){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    }
                    if(ifMobileExit.getStatus().equals("1")) {
                        if (view != null) {
                            view.onBeanFinish(ifMobileExit);
                        }
                    }else if(ifMobileExit.getStatus().equals("2") || ifMobileExit.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, ifMobileExit.getMsg());
                    }else if(ifMobileExit.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,ifMobileExit.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(IfMobileExitView view) {
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
