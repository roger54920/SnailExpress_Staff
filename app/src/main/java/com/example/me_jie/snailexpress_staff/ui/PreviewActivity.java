package com.example.me_jie.snailexpress_staff.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.app.AppConstant;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.bean.UpLoadImageBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.ARecipientsEnteringView;
import com.example.me_jie.snailexpress_staff.mvp.UpLoadImageView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.ARecipientsPresenter;
import com.example.me_jie.snailexpress_staff.presenter.UpLoadImagePresenter;
import com.example.me_jie.snailexpress_staff.utils.BitmapUtil;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 站长  照相的相片
 */
public class PreviewActivity extends Activity implements ARecipientsEnteringView, UpLoadImageView {
    @InjectView(R.id.img)
    ImageView img;
    @InjectView(R.id.homecamera_bottom_relative)
    AutoRelativeLayout homecameraBottomRelative;
    @InjectView(R.id.rephotograph)
    TextView rephotograph;
    @InjectView(R.id.confirm)
    TextView confirm;
    @InjectView(R.id.rl_show_img)
    AutoRelativeLayout rlShowImg;
    private Intent intent;
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private ARecipientsPresenter aRecipientsPresenter = new ARecipientsPresenter();//站长信息录入
    private UpLoadImagePresenter upLoadImagePresenter = new UpLoadImagePresenter();//上传图片

    private int screenWidth;
    private int screenHeight;
    private String img_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        if (StatusBarUtil.checkDeviceHasNavigationBar(this) == true) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 0, 0, StatusBarUtil.getNavigationBarHeight(this));
            rlShowImg.setLayoutParams(lp);
        }
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        intent = getIntent();
        img_path = intent.getStringExtra(AppConstant.KEY.IMG_PATH);
        img.setImageURI(Uri.parse(img_path));
        initData();

    }

    private void initData() {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        int menuPopviewHeight = (screenHeight - screenWidth * 4 / 3) - 120;//200
        //这里相机取景框我这是为宽高比3:4 所以限制底部控件的高度是剩余部分
        RelativeLayout.LayoutParams bottomParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, menuPopviewHeight);
        bottomParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        homecameraBottomRelative.setLayoutParams(bottomParam);
    }

    /**
     * 站长收件录入
     */
    private void aRecipientsSave(String json) {
        new OkHttpResolve(this);
        aRecipientsPresenter.attach(this);
        aRecipientsPresenter.postSaveRecipientsResult(json, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化上传图片
     */
    private void requestUpLoadImageResult() {
        new OkHttpResolve(this);
        upLoadImagePresenter.attach(this);
        upLoadImagePresenter.postJsonResult("expressAccept", img_path, this, lazyLoadProgressDialog);
    }

    @Override
    public void onASaveRecipientsFinish(Object o) {
        RecipientsBean recipientsBean = (RecipientsBean) o;
        if (recipientsBean != null) {
            BitmapUtil.RecursionDeleteFile(new File(img_path));
            intent = new Intent(this, RecipientsMessageEnteringActivity.class);
            RecipientsBean.ExpressAcceptBean expressAccept = recipientsBean.getExpressAccept();
            if (expressAccept != null) {
                SkipIntentUtil.isIntentHomeSource(getIntent().getStringExtra("home_source"), intent);
                intent.putExtra("source", getIntent().getStringExtra("source"));
                intent.putExtra("expressAccept_id", expressAccept.getId());
                intent.putExtra("expressParcel_id", expressAccept.getExpressParcel().getId());
                intent.putExtra("workstation_id", expressAccept.getWorkstation().getId());
                RequestOperationUtil.intentAcceptMessageEnteringParameters(recipientsBean.getExpressAccept(), intent);
            }
            RecipientsBean.ActBean act = recipientsBean.getAct();
            if (act != null) {
                intent.putExtra("act_taskId", act.getTaskId());
                intent.putExtra("act_procInsId", act.getProcInsId());
            }
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onAAddressByPhoneOrParcelNoFinish(Object o) {

    }

    @Override
    public void onUploadImageFinish(Object o) {
        UpLoadImageBean upLoadImageBean = (UpLoadImageBean) o;
        if (upLoadImageBean.getStatus().equals("1")) {
            HashMap<String, String> params = new HashMap<>();
            params.put("id", intent.getStringExtra("expressAccept_id"));
            params.put("expressParcel.id", intent.getStringExtra("expressParcel_id"));
            params.put("act.taskId", intent.getStringExtra("act_taskId"));
            params.put("act.procInsId", intent.getStringExtra("act_procInsId"));
            params.put("expressParcel.photo", upLoadImageBean.getRelativePath());
            JSONObject jsonObject = new JSONObject(params);
            lazyLoadProgressDialog.show();
            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
            aRecipientsSave(jsonObject.toString());
        }
    }


    @OnClick({R.id.rephotograph, R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rephotograph:
                returnCamera();
                break;
            case R.id.confirm:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestUpLoadImageResult();
                break;
        }
    }

    /**
     * 返回相机
     */
    private void returnCamera() {
        BitmapUtil.RecursionDeleteFile(new File(img_path));
        intent = new Intent(PreviewActivity.this, AdministratorCameraActivity.class);
        intent.putExtra("expressAccept_id", getIntent().getStringExtra("expressAccept_id"));
        intent.putExtra("expressParcel_id", getIntent().getStringExtra("expressParcel_id"));
        intent.putExtra("act_taskId", getIntent().getStringExtra("act_taskId"));
        intent.putExtra("act_procInsId", getIntent().getStringExtra("act_procInsId"));
        intent.putExtra("source", getIntent().getStringExtra("source"));
        SkipIntentUtil.isIntentHomeSource(getIntent().getStringExtra("home_source"), intent);
        startActivity(intent);
        PreviewActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            returnCamera();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        aRecipientsPresenter.dettach();
        upLoadImagePresenter.dettach();
    }

}
