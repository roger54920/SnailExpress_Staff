package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.SnailSiteBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.SnailSiteView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 蜗牛站点
 */

public class SnailSitePresenter implements BasePresenter<SnailSiteView>{
    private SnailSiteView view;

    /**
     * 获取蜗牛站点
     */
    public void getSnailSiteResult(final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.getResult(Constants.TOP + "sys/workstation/findAll", "snailSite",SnailSiteBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                SnailSiteBean snailSiteBean = (SnailSiteBean) result;
                if(snailSiteBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(snailSiteBean.getStatus().equals("1")) {
                        if (snailSiteBean.getWorkStationList() != null) {
                            if (view != null) {
                                view.onSnailListFinish(snailSiteBean);
                            }
                        }
                    }else if(snailSiteBean.getStatus().equals("2")|| snailSiteBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,snailSiteBean.getMsg());
                    }else if(snailSiteBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, snailSiteBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(SnailSiteView view) {
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
