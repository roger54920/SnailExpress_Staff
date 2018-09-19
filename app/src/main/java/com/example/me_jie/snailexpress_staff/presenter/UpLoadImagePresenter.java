package com.example.me_jie.snailexpress_staff.presenter;

import android.app.Activity;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.bean.UpLoadImageBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.UpLoadImageView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 上传图片
 */

public class UpLoadImagePresenter implements BasePresenter<UpLoadImageView>{
    private UpLoadImageView view;
    public void postJsonResult(String category,String srcPath,final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
            OkHttpResolve.postJsonUpLoadResult(Constants.TOP + "upload/uploadImage", category,srcPath, UpLoadImageBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                UpLoadImageBean upLoadImageBean = (UpLoadImageBean) result;
                if (upLoadImageBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (upLoadImageBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onUploadImageFinish(upLoadImageBean);
                        }
                    } else if (upLoadImageBean.getStatus().equals("2") || upLoadImageBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, upLoadImageBean.getMsg());
                    } else if(upLoadImageBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity, upLoadImageBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(UpLoadImageView view) {
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
