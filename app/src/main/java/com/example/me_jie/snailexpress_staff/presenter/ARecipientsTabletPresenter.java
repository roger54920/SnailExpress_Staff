package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.RecipientsTabletBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ARecipientsTabletView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 获取门牌号
 */

public class ARecipientsTabletPresenter implements BasePresenter<ARecipientsTabletView>{
    private ARecipientsTabletView view;
    /**
     * 保存收件站信息  确认信息
     * @param json
     */
    public void postRecipientsTabletResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/area/getHouse", json, RecipientsTabletBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                RecipientsTabletBean recipientsTabletBean = (RecipientsTabletBean) result;
                if(recipientsTabletBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                        if(recipientsTabletBean.getStatus().equals("1")) {
                            if (view != null) {
                                view.onTabletListFinish(recipientsTabletBean);
                            }
                        }else if(recipientsTabletBean.getStatus().equals("2")|| recipientsTabletBean.getStatus().equals("3")){
                            SkipIntentUtil.toastShow(activity,recipientsTabletBean.getMsg());
                        }else if(recipientsTabletBean.getStatus().equals("4")){
                            SkipIntentUtil.returnLogin(activity, recipientsTabletBean.getMsg());
                        }else{
                            SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                        }
                }
            }
        });
    }
    @Override
    public void attach(ARecipientsTabletView view) {
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
