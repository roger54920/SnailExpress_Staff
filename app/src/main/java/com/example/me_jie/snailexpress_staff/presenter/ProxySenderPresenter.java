package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.BaseBean;
import com.example.me_jie.snailexpress_staff.bean.ProxySenderBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ProxySenderView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 站长/员工寄件
 */

public class ProxySenderPresenter implements BasePresenter<ProxySenderView> {
    private ProxySenderView view;

    /**
     * 获取寄件相关信息
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void getProxyUserSendApplyResult(final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.getResult(Constants.TOP + "business/expressSend/proxyUserSendApply", "proxyUserSendApply", ProxySenderBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                ProxySenderBean proxySenderBean = (ProxySenderBean) result;
                if(proxySenderBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(proxySenderBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onProxyUserSendApplyFinish(proxySenderBean);
                        }
                    }else if(proxySenderBean.getStatus().equals("2") || proxySenderBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, proxySenderBean.getMsg());
                    }else if(proxySenderBean.getStatus().equals("4")){
                      SkipIntentUtil.returnLogin(activity,proxySenderBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    /**
     * 确认寄件信息
     * @param json
     */
    public void postSaveProxySenderResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressSend/saveProxySender", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean saveProxySender = (BaseBean) result;
                if(saveProxySender !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(saveProxySender.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSaveProxySenderFinish(saveProxySender);
                        }
                    }else if(saveProxySender.getStatus().equals("2")|| saveProxySender.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, saveProxySender.getMsg());
                    }else if(saveProxySender.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, saveProxySender.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(ProxySenderView view) {
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
