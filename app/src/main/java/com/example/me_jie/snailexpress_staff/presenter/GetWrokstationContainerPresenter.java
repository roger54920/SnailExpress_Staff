package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.GetWrokstationContainerBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.GetWrokstationContainerView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 获取货柜编号
 */

public class GetWrokstationContainerPresenter implements BasePresenter<GetWrokstationContainerView>{
    private GetWrokstationContainerView view;
    /**
     * 登录
     *
     * @param json
     */
    public void postGetWrokstationContainerResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/workstation/getWrokstationContainerNoByWorkstationId", json, GetWrokstationContainerBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                GetWrokstationContainerBean getWrokstationContainerBean = (GetWrokstationContainerBean) result;
                if (getWrokstationContainerBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (getWrokstationContainerBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onGetWrokstationContainerListFinish(getWrokstationContainerBean);
                        }
                    } else if (getWrokstationContainerBean.getStatus().equals("2")|| getWrokstationContainerBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, getWrokstationContainerBean.getMsg());
                    }else if(getWrokstationContainerBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, getWrokstationContainerBean.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(GetWrokstationContainerView view) {
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
