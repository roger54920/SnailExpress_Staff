package com.example.me_jie.snailexpress_staff.utils;

import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.lzy.okgo.OkGo;


/**
 * Created by me-jie on 2017/4/19.
 * 休眠Button 按钮
 */

public class LazyLoadUtil {
    public static void SetDormantButton(final Button button){
        //休眠3秒
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);//休眠3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 要执行的操作
                 */
                button.setClickable(true);
            }
        }.start();
    }
    public static void SetDormantRelativeLayout(final RelativeLayout relativeLayout){
        //休眠3秒
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);//休眠3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 要执行的操作
                 */
                relativeLayout.setClickable(true);
            }
        }.start();
    }

    public static void SetLazyLad(final LazyLoadProgressDialog dialog){
        final LazyLoadProgressDialog lazyLoadProgressDialog = dialog;
        //10秒内加载没有反应，直接关闭
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(30000);//休眠15秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 要执行的操作
                 */
                if(lazyLoadProgressDialog!=null){
                    lazyLoadProgressDialog.cancel();
                }

            }
        }.start();
    }
    public static void SetLazyLadResult(LazyLoadProgressDialog dialog){
        final LazyLoadProgressDialog lazyLoadProgressDialog = dialog;
        //15秒内加载没有反应，直接关闭
        if(lazyLoadProgressDialog!=null){
            lazyLoadProgressDialog.cancel();
        }
    }
    /**
     * 1分钟以后关闭LazyLoadProgressDialog
     * @param dialog
     */
    public static void SetLazyLad0neMinute(LazyLoadProgressDialog dialog){
        final LazyLoadProgressDialog lazyLoadProgressDialog = dialog;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(OkGo.DEFAULT_MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 要执行的操作
                 */
                if(lazyLoadProgressDialog!=null){
                    lazyLoadProgressDialog.cancel();
                }
            }
        }.start();
    }
}
