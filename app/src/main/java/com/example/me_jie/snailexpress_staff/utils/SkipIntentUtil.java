package com.example.me_jie.snailexpress_staff.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.dialog.NoNetworkDialog;
import com.example.me_jie.snailexpress_staff.ui.AdministratorRecipientsDisplayActivity;
import com.example.me_jie.snailexpress_staff.ui.LoginActivity;
import com.example.me_jie.snailexpress_staff.ui.MipcaActivityCapture;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;


/**
 * Created by me-jie on 2017/4/27.
 * 页面小工具
 */

public class SkipIntentUtil {
    /**
     * 跳转页面
     * @param activity
     * @param cl
     */
    public static void skipIntent(Activity activity,Class cl){
        Intent intent = new Intent(activity,cl);
        activity.startActivity(intent);
//        activity.overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
    }

    /**
     * 对toast进行一个简单的封装
     * @param activity
     * @param msg
     */
    private static Toast mToast;
    public static void toastShow(Activity activity, String msg){
        if (null == mToast) {
            mToast = Toast.makeText(activity, msg,
                    Toast.LENGTH_SHORT);
//            mToast.setGravity(Gravity.CENTER|Gravity.BOTTOM, 40, 0);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
    //取消Toast
    public static void toastStop() {
        if (null != mToast) {
            mToast.cancel();
        }
    }
    /**
     * 返回登录页面
     * @param activity
     */
    public static void returnLogin(Activity activity,String msg){
        SkipIntentUtil.toastShow(activity,msg);
        SharedPreferences sp = activity.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("login");
        editor.clear();
        editor.commit();
        SkipIntentUtil.skipIntent(activity, LoginActivity.class);
        MyActivityManager.getInstance().finishAllActivity();
    }

    /**
     * 无网络时弹窗
     * @param activity
     */
    public static void noNetworkPopUpWindows(final Activity activity, LazyLoadProgressDialog dialog){
        if(dialog!=null){
            LazyLoadUtil.SetLazyLadResult(dialog);
            final NoNetworkDialog noNetworkDialog = new NoNetworkDialog(activity).setNoNetworkDialog();
            if(!activity.isFinishing()){
                noNetworkDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!activity.isFinishing()){
                            noNetworkDialog.dismiss();
                        }
                    }
                }, 1000);
            }
        }
    }

    /**
     * 隐藏和显示时间轴
     * @param zoom
     * @param rv
     */
    public static void zoomTimerShaft(TextView zoom, RecyclerView rv){
        String zoomVal = zoom.getText().toString();
        if (zoomVal.equals("收起")) {
            zoom.setText("展开");
            rv.setVisibility(View.GONE);
        } else if (zoomVal.equals("展开")) {
            zoom.setText("收起");
            rv.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 返回来源页面 首页点击扫二维码 收件
     */
    public static void sourceAcceptReturn(Activity activity, String source){
        if(source.equals("administrator_home") || source.equals("administrator_recipients")){
            Intent intent = new Intent(activity, MipcaActivityCapture.class);
            intent.putExtra("source",source);
            activity.startActivity(intent);
            activity.finish();
        }else{
            activity.finish();
        }
    }
    /**
     * 返回来源页面 首页点击扫二维码 寄件
     */
    public static void sourceSenderReturn(Activity activity, String source){
        if(source.equals("administrator_home") || source.equals("administrator_took") || source.contains("mipca_capture")){
            Intent intent = new Intent(activity, MipcaActivityCapture.class);
            intent.putExtra("source",source);
            activity.startActivity(intent);
            activity.finish();
        }else{
            activity.finish();
        }
    }
    /**
     * RV列表刷新超时
     * @param activity
     */
    public static void rvRefreshTimeout(final Activity activity, final TwinklingRefreshLayout refreshLayout,int state){
        if(state == 1){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final NoNetworkDialog noNetworkDialog = new NoNetworkDialog(activity).setNoNetworkDialog();
                    if(!activity.isFinishing()){
                        noNetworkDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(!activity.isFinishing()){
                                    noNetworkDialog.dismiss();
                                }
                            }
                        }, 1000);
                    }
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                }
            }, 30000);
        }else{
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }

    }

    /**
     * RV刷新列表成功
     * @param commonAdapter
     * @param refreshLayout
     */
    public static void rvRefreshSuccess(CommonAdapter commonAdapter,TwinklingRefreshLayout refreshLayout){
        if(commonAdapter!=null){
            commonAdapter.notifyDataSetChanged();
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }
    }

    /**
     * 请求操作完成返回收件列表
     * @param home_source
     * @param activity
     * @param intent
     */
    public static void successfulResultAccept(String home_source,Activity activity,Intent intent){
        if(!TextUtils.isEmpty(home_source) && home_source.equals("acceptPay")){
            intent = new Intent(activity, AdministratorRecipientsDisplayActivity.class);
            intent.putExtra("home_source",home_source);
            activity.startActivity(intent);
        }else{
            SkipIntentUtil.skipIntent(activity, AdministratorRecipientsDisplayActivity.class);
        }
        activity.finish();
    }

    /**
     * 设置 收件和代收汇款标题
     * @param home_source
     * @param titleTv
     */
    public static void setTitleAcceptOrPay(String home_source,TextView titleTv){
        if(!TextUtils.isEmpty(home_source) && home_source.equals("acceptPay")){
            titleTv.setText("代收货款");
        }else{
            titleTv.setText("收件");
        }
    }

    /**
     * 是否是代收货款
     * @param home_source
     * @param intent
     */
    public static void isIntentHomeSource(String home_source,Intent intent){
        if(!TextUtils.isEmpty(home_source) && home_source.equals("acceptPay")){
            intent.putExtra("home_source",home_source);
        }
    }
    /**
     * 关闭AsyncTask
     * @param task
     */
    public static void closeAsyncTask(AsyncTask task){
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }
}
