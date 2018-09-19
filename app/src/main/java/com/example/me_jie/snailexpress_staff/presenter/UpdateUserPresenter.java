package com.example.me_jie.snailexpress_staff.presenter;


import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.LoginBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.UpdateUserView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 个人信息维护
 */

public class UpdateUserPresenter implements BasePresenter<UpdateUserView>{
    private UpdateUserView view;
    public void postJsonResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/user/stationmasterRegstion", json, LoginBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                LoginBean updateUser = (LoginBean) result;
                if(updateUser!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(updateUser.getStatus().equals("1")) {
                        if (view != null) {
                            view.onBeanUpdateUserFinish(updateUser);
                        }
                    }else if(updateUser.getStatus().equals("2")|| updateUser.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,updateUser.getMsg());
                    }else if(updateUser.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, updateUser.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(UpdateUserView view) {
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
