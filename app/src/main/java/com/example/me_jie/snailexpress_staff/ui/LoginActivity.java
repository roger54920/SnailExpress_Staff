package com.example.me_jie.snailexpress_staff.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.AllRegionBean;
import com.example.me_jie.snailexpress_staff.bean.IfMobileExitBean;
import com.example.me_jie.snailexpress_staff.bean.LoginBean;
import com.example.me_jie.snailexpress_staff.custom.GlideCircleTransform;
import com.example.me_jie.snailexpress_staff.custom.SwitchView;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.AllRegionView;
import com.example.me_jie.snailexpress_staff.mvp.IfMobileExitView;
import com.example.me_jie.snailexpress_staff.mvp.LoginView;
import com.example.me_jie.snailexpress_staff.mvp.VerificationCodeView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.AllRegionPresenter;
import com.example.me_jie.snailexpress_staff.presenter.IfMobileExitPresenter;
import com.example.me_jie.snailexpress_staff.presenter.LoginPresenter;
import com.example.me_jie.snailexpress_staff.presenter.VerificationCodePresenter;
import com.example.me_jie.snailexpress_staff.utils.BitmapUtil;
import com.example.me_jie.snailexpress_staff.utils.CacheUtils;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.MyActivityManager;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.example.me_jie.snailexpress_staff.utils.TelNumMatch;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.me_jie.snailexpress_staff.Constants.UP_LOAD_IMAGE_TOP;
import static com.example.me_jie.snailexpress_staff.R.id.verify_btn;
import static com.example.me_jie.snailexpress_staff.utils.BitmapUtil.fileIsExists;

/**
 * 登录
 */
public class LoginActivity extends AutoLayoutActivity implements LoginView, IfMobileExitView, VerificationCodeView, AllRegionView ,EasyPermissions.PermissionCallbacks {
    private static final String TAG = "LoginActivity";
    @InjectView(verify_btn)
    Button verifyBtn;
    @InjectView(R.id.login_btn)
    Button loginBtn;
    @InjectView(R.id.register_btn)
    Button registerBtn;
    @InjectView(R.id.sr_number)
    EditText srNumber;
    @InjectView(R.id.sr_verification)
    EditText srVerification;
    @InjectView(R.id.checkbox)
    CheckBox checkbox;
    @InjectView(R.id.circle)
    ImageView circle;
    @InjectView(R.id.slide_switch_btn)
    SwitchView slideSwitchBtn;

    private Intent intent;
    private boolean checkAdministrator = true;//站长/员工滑动状态
    private boolean checkStaff = false;
    private IfMobileExitPresenter ifMobileExitPresenter = new IfMobileExitPresenter();//是否注册手机号
    private VerificationCodePresenter verificationCodePresenter = new VerificationCodePresenter(); //获取验证码
    private LoginPresenter loginPresenter = new LoginPresenter();//登录
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private String phone = "";//手机号

    SharedPreferences sp; //免登陆
    SharedPreferences.Editor editor;
    private AllRegionPresenter allRegionPresenter = new AllRegionPresenter();//全国地址


    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    @Override
    protected void onStart() {
        super.onStart();
        requestCodeCameraPermissions();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeCameraPermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码/自定义相机需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        StatusBarUtil.SetWriteStatusBar(this, true);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sp.edit();
        //免登陆
        String roleName = sp.getString("roleName", null);
        long timeout = sp.getLong("timeout", 0);
        if (roleName != null && timeout != 0) {
            if (System.currentTimeMillis() >= timeout) {
                editor.remove("login");
                editor.clear();
                editor.commit();
                SkipIntentUtil.skipIntent(this, LoginActivity.class);
                overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
                MyActivityManager.getInstance().finishAllActivity();
                requestWriteOff();
            } else {
                if (roleName.equals("stationmaster")) {
                    SkipIntentUtil.skipIntent(this, AdministratorHomeActivity.class);
                    finish();
                } else {
                    SkipIntentUtil.skipIntent(this, StaffHomeActivity.class);
                    finish();
               }
            }
        }
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        slideSwitchBtn.setOnSwitchListener(new SwitchView.OnSwitchListener() {
            @Override
            public void onCheck(SwitchView sv, boolean checkLeft, boolean checkRight) {
                checkAdministrator = checkLeft;
                checkStaff = checkRight;
            }
        });
        srNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String srphone = srNumber.getText().toString();
                if (TelNumMatch.isPhone(srphone)) {
                    String sdPath = BitmapUtil.getSDPath();
                    if (!TextUtils.isEmpty(sdPath)) {
                        sdPath = sdPath + "/thesnailfamily/" + srphone + ".jpg";
                        boolean b = fileIsExists(sdPath);
                        if (b == true) {
                            Bitmap diskBitmap = BitmapUtil.getDiskBitmap(sdPath);
                            if(diskBitmap!=null){
                                circle.setImageBitmap(BitmapUtil.toRoundBitmap(diskBitmap));
                            }else{
                                circle.setImageResource(R.drawable.circle_shape);
                            }
                        } else {
                            circle.setImageResource(R.drawable.circle_shape);
                        }
                    } else {
                        circle.setImageResource(R.drawable.circle_shape);
                    }
                } else {
                    circle.setImageResource(R.drawable.circle_shape);
                }
            }
        });
    }

    /**
     * 初始化登录
     *
     * @param str
     */
    private void requestLoginVerification(String str) {
        new OkHttpResolve(this);
        loginPresenter.attach(this);
        loginPresenter.postJsonLoginVerificationResult(str, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化注销
     */
    private void requestWriteOff() {
        new OkHttpResolve(this);
        loginPresenter.attach(this);
        loginPresenter.userWriteOffResult(this);
    }

    /**
     * 初始化是否注册手机号
     *
     * @param str
     */
    private void requestifMobileExit(String str) {
        new OkHttpResolve(this);
        ifMobileExitPresenter.attach(this);
        ifMobileExitPresenter.postJsonResult(str, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化请求验证码
     *
     * @param str
     */
    private void requestverificationCode(String str) {
        new OkHttpResolve(this);
        verificationCodePresenter.attach(this);
        verificationCodePresenter.postJsonCodeResult(str, this, lazyLoadProgressDialog);
    }

    /**
     * 全国地区初始化请求数据
     */

    private void requestDataAllRegion() {
        new OkHttpResolve(this);
        allRegionPresenter.attach(this);
        allRegionPresenter.postAllRegionJsonResult("{\"id\":\" \"}", this, null);
    }

    @OnClick({R.id.verify_btn, R.id.login_btn, R.id.register_btn, R.id.checkbox, R.id.circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case verify_btn:
                phone = srNumber.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    if (TelNumMatch.isPhone(phone) == true) {
                        srVerification.setText("");
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        if (checkAdministrator == true) {
                            requestifMobileExit("{\"mobile\":\"" + phone + "\",\"roleName\":\"stationmaster\"}");
                        } else if (checkStaff == true) {
                            requestifMobileExit("{\"mobile\":\"" + phone + "\",\"roleName\":\"employee,securitycleanPerson\"}");
                        }

                    } else {
                        Toast.makeText(this, "请正确输入手机号！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_btn:
                String srphone = srNumber.getText().toString();
                if (!TextUtils.isEmpty(srphone)) {
                    if (TelNumMatch.isPhone(srphone) == true) {
                        String srverification = srVerification.getText().toString().trim();
                        if (srverification.length() == 6) {
                            lazyLoadProgressDialog.show();
                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                            HashMap<String, String> params = new HashMap<>();
                            params.put("username", srphone);
                            params.put("appLogin", "true");
                            if (checkAdministrator == true) {
                                params.put("rolename", "stationmaster");
                                params.put("validateCode", srverification);
                                JSONObject jsonObject = new JSONObject(params);
                                requestLoginVerification(jsonObject.toString());
                            } else if (checkStaff == true) {
                                params.put("rolename", "employee,securitycleanPerson");
                                params.put("validateCode", srverification);
                                JSONObject jsonObject = new JSONObject(params);
                                requestLoginVerification(jsonObject.toString());
                            }
                        } else {
                            Toast.makeText(this, "请输入验证码！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "请正确输入手机号！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.register_btn:
                intent = new Intent(LoginActivity.this, StaffRegisterActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.checkbox:
                if (checkbox.isChecked() == true) {
                    loginBtn.setEnabled(true);
                } else {
                    loginBtn.setEnabled(false);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String moblie = data.getStringExtra("phone");
            if (!moblie.equals("") || moblie != null) {
                srNumber.setText(data.getStringExtra("phone"));
                verifyBtn.setEnabled(true);
                verifyBtn.setText("获取验证码");
            }
        }
    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            verifyBtn.setEnabled(false);
            verifyBtn.setText((millisUntilFinished / 1000) + "秒");
            verifyBtn.setBackgroundResource(R.drawable.verify_btn_shape_nor);
        }

        @Override
        public void onFinish() {
            verifyBtn.setEnabled(true);
            verifyBtn.setText("重新获取");
            verifyBtn.setBackgroundResource(R.drawable.verify_btn_shape_sel);
        }
    };

    @Override
    public void onBeanFinish(Object o) {
        IfMobileExitBean ifMobileExitBean = (IfMobileExitBean) o;
        if (ifMobileExitBean != null) {
            if (ifMobileExitBean.isIfMobileExit() == true) {
                requestverificationCode("{\"mobile\":\"" + phone + "\"}");
            } else {
                SkipIntentUtil.toastShow(this, "账号不存在，请检查后登录！");
            }
        }
    }

    @Override
    public void onBeanLoginFinish(Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            if (loginBean.getUser().getRoleName() != null) {
                final String mobile = loginBean.getUser().getMobile();

                Glide.with(this).load(UP_LOAD_IMAGE_TOP + loginBean.getUser().getPhoto())
                        .asBitmap()
                        .transform(new GlideCircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE) //添加缓存
                        .placeholder(circle.getDrawable())//加载成功之前
                        .error(circle.getDrawable())//加载失败
                        .crossFade()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                BitmapUtil.saveImageToGallery(resource, mobile);
                            }
                        });

                sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                editor = sp.edit();
                String roleName = loginBean.getUser().getRoleName();
                long sessionTimeout = loginBean.getSessionTimeout();
                if (roleName.equals("stationmaster")) {
                    avoidLandingSkip(roleName, sessionTimeout, AdministratorHomeActivity.class);
                } else {
                    avoidLandingSkip(roleName, sessionTimeout, StaffHomeActivity.class);
                }
                requestDataAllRegion();
            }
        }
    }

    /**
     * @param roleName
     * @param sessionTimeout
     * @param cl
     */
    private void avoidLandingSkip(String roleName, long sessionTimeout, Class cl) {
        if ((roleName != null || !roleName.equals("")) && sessionTimeout != 0) {
            editor.putString("roleName", roleName);
            editor.putLong("timeout", System.currentTimeMillis() + sessionTimeout);
            editor.commit();
        }
        SkipIntentUtil.skipIntent(this, cl);
    }

    @Override
    public void onBeanWriteOffFinish(Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            Log.e(TAG, "onBeanWriteOffFinish: 注销成功！");
        }
    }

    @Override
    public void onBeanVerificationCodeFinish(Object o) {
        LoginBean verificationCodeBean = (LoginBean) o;
        if (verificationCodeBean != null) {
            timer.start();
        }
    }

    @Override
    public void onAllRegionListFinish(Object o) {
        final AllRegionBean allRegionBean = (AllRegionBean) o;
        if (allRegionBean != null) {
            new AsyncTask<String, Void, AllRegionBean>() {
                @Override
                protected AllRegionBean doInBackground(String... params) {
                    CacheUtils.writeJson(LoginActivity.this, allRegionBean, "nationalregions.txt", false,this);
                    finish();
                    return null;
                }
            }.execute();
        }
    }

    /**
     * Android中的“再按一次返回键退出程序”实现
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出蜗牛快递！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MyActivityManager.getInstance().finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        ifMobileExitPresenter.dettach();
        verificationCodePresenter.dettach();
        loginPresenter.dettach();
        if (timer != null) {
            timer.cancel();
        }
    }
}
