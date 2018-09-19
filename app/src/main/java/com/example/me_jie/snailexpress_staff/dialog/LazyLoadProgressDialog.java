package com.example.me_jie.snailexpress_staff.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.R;

/**
 * Created by 陈苗辉 on 2016/12/14.
 */
public class LazyLoadProgressDialog extends Dialog{


    private Context context;
    public LazyLoadProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public LazyLoadProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LazyLoadProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    private static LazyLoadProgressDialog progressDialog;
    //创建dialog的样式
    public static LazyLoadProgressDialog createDialog(Context context){

        progressDialog = new LazyLoadProgressDialog(context, R.style.ProgressDialogStyle);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return progressDialog ;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (progressDialog == null) {
            return;
        }
        //添加控件  执行帧动画
        ImageView imageView = (ImageView) progressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    public LazyLoadProgressDialog setTitle(String title) {
        return progressDialog;
    }

    public LazyLoadProgressDialog setMessage(String strMessage){
        TextView tvMessage = (TextView)progressDialog.findViewById(R.id.id_tv_loadingmsg);

        if (tvMessage != null){
            tvMessage.setText(strMessage);
        }
        return progressDialog;
    }
}
