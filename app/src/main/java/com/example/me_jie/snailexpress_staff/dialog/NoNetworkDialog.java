package com.example.me_jie.snailexpress_staff.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.me_jie.snailexpress_staff.R;


/**
 * 自定义弹窗 无网络提示
 */
public class NoNetworkDialog extends Dialog {
    private Context mContext;
    public NoNetworkDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public NoNetworkDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 设置弹窗
     */
    public NoNetworkDialog setNoNetworkDialog() {
        NoNetworkDialog noNetworkDialog = new NoNetworkDialog(mContext, R.style.ActionDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.coverpopup_no_network_item, null);
        //初始化控件
        //将布局设置给Dialog
        noNetworkDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = noNetworkDialog.getWindow();
        //设置Dialog从窗体中部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;
        lp.x = 0;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        return noNetworkDialog;
    }
}

