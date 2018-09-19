package com.example.me_jie.snailexpress_staff.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.me_jie.snailexpress_staff.BuildConfig;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.AllRegionBean;
import com.example.me_jie.snailexpress_staff.bean.IfMobileExitBean;
import com.example.me_jie.snailexpress_staff.bean.LoginBean;
import com.example.me_jie.snailexpress_staff.bean.UpLoadImageBean;
import com.example.me_jie.snailexpress_staff.custom.RadioGroup;
import com.example.me_jie.snailexpress_staff.dialog.ActionSheetDialog;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.AllRegionView;
import com.example.me_jie.snailexpress_staff.mvp.IfMobileExitView;
import com.example.me_jie.snailexpress_staff.mvp.UpLoadImageView;
import com.example.me_jie.snailexpress_staff.mvp.UpdateUserView;
import com.example.me_jie.snailexpress_staff.mvp.VerificationCodeView;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.AllRegionPresenter;
import com.example.me_jie.snailexpress_staff.presenter.IfMobileExitPresenter;
import com.example.me_jie.snailexpress_staff.presenter.SnailSitePresenter;
import com.example.me_jie.snailexpress_staff.presenter.UpLoadImagePresenter;
import com.example.me_jie.snailexpress_staff.presenter.UpdateUserPresenter;
import com.example.me_jie.snailexpress_staff.presenter.VerificationCodePresenter;
import com.example.me_jie.snailexpress_staff.utils.BitmapUtil;
import com.example.me_jie.snailexpress_staff.utils.CacheUtils;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.example.me_jie.snailexpress_staff.utils.TelNumMatch;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 注册
 */
public class StaffRegisterActivity extends AutoLayoutActivity implements IfMobileExitView, VerificationCodeView, UpdateUserView, UpLoadImageView, AllRegionView {

    @InjectView(R.id.station_agent)
    LinearLayout stationAgent;
    @InjectView(R.id.staff)
    LinearLayout staff;
    @InjectView(R.id.station_agent_radio)
    RadioButton stationAgentRadio;
    @InjectView(R.id.staff_radio)
    RadioButton staffRadio;
    @InjectView(R.id.ba_radio)
    RadioButton baRadio;
    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    @InjectView(R.id.regist_group)
    RadioGroup registGroup;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.sr_name)
    EditText srName;
    @InjectView(R.id.sr_phone)
    EditText srPhone;
    @InjectView(R.id.sr_code)
    EditText srCode;
    @InjectView(R.id.get_code)
    Button getCode;
    @InjectView(R.id.staff_sr_name)
    EditText staffSrName;
    @InjectView(R.id.staff_sr_phone)
    EditText staffSrPhone;
    @InjectView(R.id.staff_sr_code)
    EditText staffSrCode;
    @InjectView(R.id.staff_get_code)
    Button staffGetCode;
    @InjectView(R.id.add_img)
    ImageView addImg;
    @InjectView(R.id.commit_btn)
    Button commitBtn;
    @InjectView(R.id.security_sr_name)
    EditText securitySrName;
    @InjectView(R.id.security_sr_phone)
    EditText securitySrPhone;
    @InjectView(R.id.security_sr_code)
    EditText securitySrCode;
    @InjectView(R.id.security_get_code)
    Button securityGetCode;
    @InjectView(R.id.security)
    LinearLayout security;
    private Intent intent;

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private IfMobileExitPresenter ifMobileExitPresenter = new IfMobileExitPresenter();//是否注册手机号
    private VerificationCodePresenter verificationCodePresenter = new VerificationCodePresenter(); //获取验证码
    private SnailSitePresenter snailSitePresenter = new SnailSitePresenter();//获取全部的蜗牛站点
    private UpdateUserPresenter updateUserPresenter = new UpdateUserPresenter();//注册
    private UpLoadImagePresenter upLoadImagePresenter = new UpLoadImagePresenter();//上传图片
    private String phone = "";//手机号

    private CountDownTimer administratorTimer; //站长倒计时
    private CountDownTimer staffTimer; //员工倒计时
    private CountDownTimer securityTimer; //保安倒计时
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //调用照相机返回图片文件
    private File tempFile;
    //提交的连接地址
    private String srcPath;
    //本地的地址
    private String cropImagePath;
    private String relativePath = null;//上传成功返回路径

    SharedPreferences sp; //免登陆
    SharedPreferences.Editor editor;
    private AllRegionPresenter allRegionPresenter = new AllRegionPresenter();//全国地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_register);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);


        registGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.station_agent_radio:
                        concealLayout();
                        stationAgent.setVisibility(View.VISIBLE);
                        break;
                    case R.id.staff_radio:
                        concealLayout();
                        staff.setVisibility(View.VISIBLE);
                        break;
                    case R.id.ba_radio:
                        concealLayout();
                        security.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    /**
     * 隐藏 相对应的布局
     */
    private void concealLayout() {
        staff.setVisibility(View.GONE);
        stationAgent.setVisibility(View.GONE);
        security.setVisibility(View.GONE);
    }

    @OnClick({R.id.return_arrows, R.id.add_img, R.id.commit_btn, R.id.get_code, R.id.staff_get_code, R.id.security_get_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.skipIntent(this, LoginActivity.class);
                finish();
                break;
            case R.id.add_img:
                actionSheetDialog();
                break;
            case R.id.commit_btn:
                if (stationAgentRadio.isChecked() == true) {
                    String srname = srName.getText().toString();
                    String srphone = srPhone.getText().toString();
                    String srcode = srCode.getText().toString();
                    if (!srname.equals("")) {
                        if (!srphone.equals("")) {
                            if (!srcode.equals("")) {
                                if (srcPath == null || srcPath == "") {
                                    fillMessageRequest(srname, srphone, srcode, "stationmaster");
                                } else {
                                    requestUpLoadImageResult();
                                    if (relativePath != null) {
                                        fillMessageRequest(srname, srphone, srcode, "stationmaster");
                                    }
                                }
                            } else {
                                SkipIntentUtil.toastShow(this, "请输入站长验证码！");
                            }
                        } else {
                            SkipIntentUtil.toastShow(this, "请输入站长手机号！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请输入站长姓名！");
                    }

                } else if (staffRadio.isChecked() == true) {
                    String staffSrname = staffSrName.getText().toString();
                    String staffSrphone = staffSrPhone.getText().toString();
                    String staffSrcode = staffSrCode.getText().toString();
                    if (!staffSrname.equals("")) {
                        if (!staffSrphone.equals("")) {
                            if (!staffSrcode.equals("")) {
                                if (srcPath == null || srcPath == "") {
                                    fillMessageRequest(staffSrname, staffSrphone, staffSrcode, "employee");
                                } else {
                                    requestUpLoadImageResult();
                                    if (relativePath != null) {
                                        fillMessageRequest(staffSrname, staffSrphone, staffSrcode, "employee");
                                    }
                                }
                            } else {
                                SkipIntentUtil.toastShow(this, "请输入员工验证码！");
                            }
                        } else {
                            SkipIntentUtil.toastShow(this, "请输入员工手机号！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请输入员工姓名！");
                    }
                } else if (baRadio.isChecked() == true) {
                    String securitySrname = securitySrName.getText().toString();
                    String securitySrphone = securitySrPhone.getText().toString();
                    String securitySrcode = securitySrCode.getText().toString();
                    if (!securitySrname.equals("")) {
                        if (!securitySrphone.equals("")) {
                            if (!securitySrcode.equals("")) {
                                if (srcPath == null || srcPath == "") {
                                    fillMessageRequest(securitySrname, securitySrphone, securitySrcode, "securitycleanPerson");
                                } else {
                                    requestUpLoadImageResult();
                                    if (relativePath != null) {
                                        fillMessageRequest(securitySrname, securitySrphone, securitySrcode, "securitycleanPerson");
                                    }
                                }
                            } else {
                                SkipIntentUtil.toastShow(this, "请输入保安验证码！");
                            }
                        } else {
                            SkipIntentUtil.toastShow(this, "请输入保安手机号！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请输入保安姓名！");
                    }
                }
                break;
            case R.id.get_code:
                phone = srPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    if (TelNumMatch.isPhone(phone) == true) {
                        srCode.setText("");
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestifMobileExit("{\"mobile\":\"" + phone + "\"}");
                    } else {
                        Toast.makeText(this, "请正确输入手机号！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.staff_get_code:
                phone = staffSrPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    if (TelNumMatch.isPhone(phone) == true) {
                        staffSrCode.setText("");
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestifMobileExit("{\"mobile\":\"" + phone + "\"}");
                    } else {
                        Toast.makeText(this, "请正确输入手机号！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.security_get_code:
                phone = securitySrPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    if (TelNumMatch.isPhone(phone) == true) {
                        securitySrCode.setText("");
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestifMobileExit("{\"mobile\":\"" + phone + "\"}");
                    } else {
                        Toast.makeText(this, "请正确输入手机号！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 底部弹窗
     */
    private void actionSheetDialog() {
        final ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this, new ActionSheetDialog.ActionSheetClickListener() {
            @Override
            public void onClickTakePhoto(ActionSheetDialog actionSheetDialog) {
                //权限判断
                if (ContextCompat.checkSelfPermission(StaffRegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(StaffRegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                actionSheetDialog.dismiss();
            }

            @Override
            public void onClickChoosePhoto(ActionSheetDialog actionSheetDialog) {
                //权限判断
                if (ContextCompat.checkSelfPermission(StaffRegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(StaffRegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                actionSheetDialog.dismiss();
            }

            @Override
            public void onClickCancel(ActionSheetDialog actionSheetDialog) {
                actionSheetDialog.dismiss();
            }
        }).setActionSheetDialog();
        actionSheetDialog.show();

    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/thesnailfamily/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    }

                    cropImagePath = getRealFilePathFromUri(this.getApplicationContext(), uri);
                    srcPath = cropImagePath;
                    Bitmap bitMap = BitmapFactory.decodeFile(srcPath);
                    addImg.setImageBitmap(bitMap);
//                    //此处后面可以将bitMap转为二进制上传后台网络

                }
                break;
        }
    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 填写注册信息
     */
    private void fillMessageRequest(String name, String phone, String code, String roleName) {
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("mobile", phone);
        params.put("checkCode", code);
        params.put("roleName", roleName);
        params.put("photo", relativePath);
        JSONObject jsonObject = new JSONObject(params);
        updateUserJson(jsonObject.toString());
    }

    /**
     * 初始化上传图片
     */
    private void requestUpLoadImageResult() {
        new OkHttpResolve(this);
        upLoadImagePresenter.attach(this);
        upLoadImagePresenter.postJsonResult("user", srcPath, this, lazyLoadProgressDialog);
    }

    /**
     * 全国地区初始化请求数据
     */

    private void requestDataAllRegion() {
        new OkHttpResolve(this);
        allRegionPresenter.attach(this);
        allRegionPresenter.postAllRegionJsonResult("{\"id\":\" \"}", this, null);
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
     * 初始化Json请求数据
     *
     * @param str
     */
    private void updateUserJson(String str) {
        new OkHttpResolve(this);
        updateUserPresenter.attach(this);
        updateUserPresenter.postJsonResult(str, this, lazyLoadProgressDialog);
    }

    @Override
    public void onBeanFinish(Object o) {
        IfMobileExitBean ifMobileExitBean = (IfMobileExitBean) o;
        if (ifMobileExitBean != null) {
            if (ifMobileExitBean.isIfMobileExit() == false) {
                requestverificationCode("{\"mobile\":\"" + phone + "\"}");
            } else {
                SkipIntentUtil.toastShow(this, "手机号已存在，请重新输入！");
            }
        }
    }

    @Override
    public void onBeanVerificationCodeFinish(Object o) {
        LoginBean verificationCodeBean = (LoginBean) o;
        if (verificationCodeBean != null) {
            if (stationAgentRadio.isChecked() == true) {
                administratorCountDownTimer();
                initCountDownTimer();
                administratorTimer.start();
            } else if (staffRadio.isChecked() == true) {
                staffCountDownTimer();
                initCountDownTimer();
                staffTimer.start();
            } else if (baRadio.isChecked() == true) {
                securityCountDownTimer();
                initCountDownTimer();
                securityTimer.start();
            }
        }
    }

    /**
     * 初始化 倒计时
     */
    private void initCountDownTimer() {
        if (administratorTimer != null) {
            administratorTimer.cancel();
            getCode.setEnabled(true);
            getCode.setText("获取验证码");
            getCode.setBackgroundResource(R.drawable.verify_btn_shape_sel);
        }
        if (staffTimer != null) {
            staffTimer.cancel();
            staffGetCode.setEnabled(true);
            staffGetCode.setText("获取验证码");
            staffGetCode.setBackgroundResource(R.drawable.verify_btn_shape_sel);
        }
        if (securityTimer != null) {
            securityTimer.cancel();
            securityGetCode.setEnabled(true);
            securityGetCode.setText("获取验证码");
            securityGetCode.setBackgroundResource(R.drawable.verify_btn_shape_sel);
        }

    }

    @Override
    public void onBeanUpdateUserFinish(Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            if (loginBean.getUser().getRoleName() != null) {
                if (!TextUtils.isEmpty(loginBean.getUser().getPhoto())) {
                    //保存到本地
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    BitmapUtil.saveImageToGallery(bitMap, loginBean.getUser().getMobile());
                    BitmapUtil.RecursionDeleteFile(new File(cropImagePath));
                }
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
    public void onAllRegionListFinish(Object o) {
        final AllRegionBean allRegionBean = (AllRegionBean) o;
        if (allRegionBean != null) {
            new AsyncTask<String, Void, AllRegionBean>() {
                @Override
                protected AllRegionBean doInBackground(String... params) {
                    CacheUtils.writeJson(StaffRegisterActivity.this, allRegionBean, "nationalregions.txt", false,this);
                    finish();
                    return null;
                }
            }.execute();
        }
    }

    private int time = 60000;
    private int t = 1000;

    /**
     * 倒计时
     */
    private void administratorCountDownTimer() {
        administratorTimer = new CountDownTimer(time, t) {
            @Override
            public void onTick(long millisUntilFinished) {
                getCode.setBackgroundResource(R.drawable.verify_btn_shape_nor);
                getCode.setEnabled(false);
                getCode.setText((millisUntilFinished / t) + "秒");
            }

            @Override
            public void onFinish() {
                getCode.setBackgroundResource(R.drawable.verify_btn_shape_sel);
                getCode.setEnabled(true);
                getCode.setText("重新获取");
            }
        };
    }

    private void staffCountDownTimer() {
        staffTimer = new CountDownTimer(time, t) {
            @Override
            public void onTick(long millisUntilFinished) {
                staffGetCode.setBackgroundResource(R.drawable.verify_btn_shape_nor);
                staffGetCode.setEnabled(false);
                staffGetCode.setText((millisUntilFinished / t) + "秒");
            }

            @Override
            public void onFinish() {
                staffGetCode.setBackgroundResource(R.drawable.verify_btn_shape_sel);
                staffGetCode.setEnabled(true);
                staffGetCode.setText("重新获取");
            }
        };
    }

    private void securityCountDownTimer() {
        securityTimer = new CountDownTimer(time, t) {
            @Override
            public void onTick(long millisUntilFinished) {
                securityGetCode.setBackgroundResource(R.drawable.verify_btn_shape_nor);
                securityGetCode.setEnabled(false);
                securityGetCode.setText((millisUntilFinished / t) + "秒");
            }

            @Override
            public void onFinish() {
                securityGetCode.setBackgroundResource(R.drawable.verify_btn_shape_sel);
                securityGetCode.setEnabled(true);
                securityGetCode.setText("重新获取");
            }
        };
    }

    @Override
    public void onUploadImageFinish(Object o) {
        UpLoadImageBean upLoadImageBean = (UpLoadImageBean) o;
        if (upLoadImageBean != null) {
            relativePath = upLoadImageBean.getRelativePath();
        } else {
            relativePath = null;
        }
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
        snailSitePresenter.dettach();
        updateUserPresenter.dettach();
        upLoadImagePresenter.dettach();
        if (administratorTimer != null) {
            administratorTimer.cancel();
        }
        if (securityTimer != null) {
            securityTimer.cancel();
        }
        if (staffTimer != null) {
            staffTimer.cancel();
        }
    }
}
