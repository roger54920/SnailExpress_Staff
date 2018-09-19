package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.StaffPerformanceFindBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.StaffPerformanceFindView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 员工业绩查询
 */

public class StaffPerformanceFindPresenter implements BasePresenter<StaffPerformanceFindView> {
    private StaffPerformanceFindView view;

    public void postJsonStaffPerformanceFindResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "business/performance/list", json, StaffPerformanceFindBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                StaffPerformanceFindBean staffPerformanceFindBean = (StaffPerformanceFindBean) result;
                if (staffPerformanceFindBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (staffPerformanceFindBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onStaffPerformanceFindListFinish(staffPerformanceFindBean);
                        }
                    } else if (staffPerformanceFindBean.getStatus().equals("2") || staffPerformanceFindBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, staffPerformanceFindBean.getMsg());
                    } else if (staffPerformanceFindBean.getStatus().equals("4")) {
                        SkipIntentUtil.returnLogin(activity, staffPerformanceFindBean.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(StaffPerformanceFindView view) {
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
