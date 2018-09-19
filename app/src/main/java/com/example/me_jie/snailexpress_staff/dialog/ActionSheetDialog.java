package com.example.me_jie.snailexpress_staff.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.R;


/**
 * Created by me-jie on 2017/5/26.
 * 底部弹窗选择图片
 */

public class ActionSheetDialog extends Dialog implements View.OnClickListener {
    private ActionSheetClickListener onActionSheetClickListener ;
    private ActionSheetDialog actionSheetDialog;
    private Context mContext;
    public ActionSheetDialog(Context context, ActionSheetClickListener onActionSheetClickListener) {
        super(context);
        this.mContext = context;
        this.onActionSheetClickListener = onActionSheetClickListener;
    }

    public ActionSheetDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }


    public interface ActionSheetClickListener {
        void onClickTakePhoto(ActionSheetDialog actionSheetDialog);//拍照
        void onClickChoosePhoto(ActionSheetDialog actionSheetDialog);//从相册选择
        void onClickCancel(ActionSheetDialog actionSheetDialog);//取消
    }
    public ActionSheetDialog setActionSheetDialog() {
        actionSheetDialog = new ActionSheetDialog(mContext, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.coverpopup_upload_image_item, null);
        //初始化控件
        TextView choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        TextView takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        TextView cancel = (TextView) inflate.findViewById(R.id.cancel);
        if(onActionSheetClickListener!=null){
            choosePhoto.setOnClickListener(this);
            takePhoto.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }

        //将布局设置给Dialog
        actionSheetDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = actionSheetDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        lp.width  = lp.MATCH_PARENT;
        lp.height  = lp.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        return actionSheetDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takePhoto:
                onActionSheetClickListener.onClickTakePhoto(actionSheetDialog);
                break;
            case R.id.choosePhoto:
                onActionSheetClickListener.onClickChoosePhoto(actionSheetDialog);
                break;
            case R.id.cancel:
                onActionSheetClickListener.onClickCancel(actionSheetDialog);
                break;
        }
    }
}
