package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.AllRegionBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.AllRegionView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;

import java.util.List;

/**
 * Created by me-jie on 2017/4/17.
 * 获取地址信息
 */

public class AllRegionPresenter implements BasePresenter<AllRegionView> {
    private AllRegionView view;

    //获取全国地区信息
    public void postAllRegionJsonResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/area/getAllArea", json, AllRegionBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AllRegionBean allRegionBean = (AllRegionBean) result;
                if (allRegionBean != null) {
                    if(lazyLoadProgressDialog!=null){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    }
                    if(allRegionBean.getStatus().equals("1")) {
                        List<AllRegionBean.AreaBean> area = allRegionBean.getArea();
                        if (area != null && area.size()>0) {
                            if (area.get(0).getNodes() != null) {
                                if (area.get(0).getNodes().get(0).getNodes() != null) {
                                    if (view != null) {
                                        view.onAllRegionListFinish(allRegionBean);
                                    }
                                }
                            }
                        }
                    }else if(allRegionBean.getStatus().equals("2") || allRegionBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,allRegionBean.getMsg());
                    }else if(allRegionBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,allRegionBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(AllRegionView view) {
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
