package com.example.me_jie.snailexpress_staff.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.activity.BaseActivity;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.bean.SendDetailsBean;
import com.example.me_jie.snailexpress_staff.dialog.LazyLoadProgressDialog;
import com.example.me_jie.snailexpress_staff.mvp.AScanCodeView;
import com.example.me_jie.snailexpress_staff.mvp.SendDisplayView;
import com.example.me_jie.snailexpress_staff.net.NetBroadcastReceiver;
import com.example.me_jie.snailexpress_staff.net.NetUtil;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.AScanCodePresenter;
import com.example.me_jie.snailexpress_staff.presenter.SendDisplayPresenter;
import com.example.me_jie.snailexpress_staff.utils.CacheUtils;
import com.example.me_jie.snailexpress_staff.utils.LazyLoadUtil;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;


/**
 * 站长二维码扫描
 */
public class MipcaActivityCapture extends BaseActivity implements QRCodeView.Delegate, AScanCodeView, SendDisplayView {

    @InjectView(R.id.find_addressee)
    Button findAddressee;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.zxingview)
    ZXingView mQRCodeView;
    @InjectView(R.id.open_flashlight)
    TextView openFlashlight;
    @InjectView(R.id.close_flashlight)
    TextView closeFlashlight;
    private Intent intent;
    private static final String TAG = MipcaActivityCapture.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private BroadcastReceiver receiver;//获取广播对象
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private AScanCodePresenter aScanCodePresenter = new AScanCodePresenter();  // 扫码收件
    private SendDisplayPresenter sendDisplayPresenter = new SendDisplayPresenter();  // 扫码寄件

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        receiver = new NetBroadcastReceiver();
        StatusBarUtil.registerBroadrecevicer(this, receiver);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initViews();
    }
    private void initViews(){
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpot();
        Constants.SOURCE = getIntent().getStringExtra("source");
        if (Constants.SOURCE != null) {
            switch (Constants.SOURCE) {
                case "administrator_home":
                    findAddressee.setVisibility(View.GONE);
                    title.setText("站长扫码");
                    break;
                case "staff_home":
                    findAddressee.setVisibility(View.GONE);
                    title.setText("员工扫码");
                    break;
                case "administrator_took":
                    findAddressee.setVisibility(View.VISIBLE);
                    title.setText("站长扫码");
                    findAddressee.setText("查询已寄件");
                    break;
                case "administrator_recipients":
                    findAddressee.setVisibility(View.VISIBLE);
                    title.setText("站长扫码");
                    findAddressee.setText("查询已收件");
                    break;
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        mQRCodeView.startSpot();
        scanQRCodeSuccess(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQRCodeView.showScanRect();
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = CacheUtils.getRealFilePath(this, data.getData());
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
                }

                @Override
                protected void onPostExecute(String result) {
                    scanQRCodeSuccess(result);
                    mQRCodeView.startSpot();
                }
            }.execute();

        }
    }

    /**
     * 扫码成功
     *
     * @param result
     */
    private void scanQRCodeSuccess(String result) {
        if (TextUtils.isEmpty(result)) {
            SkipIntentUtil.toastShow(this, "未发现二维码！");
        } else {
            //启动时判断网络状态
            Constants.NETCONNECT = this.isNetConnect();
            if (Constants.NETCONNECT == false) {
                SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
            } else {
                Constants.NETCONNECT = true;
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                Constants.SOURCE = getIntent().getStringExtra("source");
                switch (Constants.SOURCE) {
                    case "administrator_took":
                        postSendScanCodeResult("{\"senderId\":\"" + result + "\"}");
                        break;
                    case "administrator_recipients":
                        acceptScan(result);
                        break;
                    case "staff_home":
                        acceptScan(result);
                        break;
                    default:
                        if (result.length() == 32) {
                            postSendScanCodeResult("{\"senderId\":\"" + result + "\"}");
                        } else {
                            acceptScan(result);
                        }
                        break;
                }
            }
        }
    }
    /**
     * 收件扫码
     *
     * @param resultString
     */
    private void acceptScan(String resultString) {
        JSONObject json = new JSONObject();
        try {
            json.put("expressParcel", new JSONObject().put("lgisticCode", resultString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postAScanCodeResult(json.toString());
    }

    @OnClick({R.id.find_addressee, R.id.return_arrows, R.id.open_flashlight, R.id.close_flashlight, R.id.choose_qrcde_from_gallery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.find_addressee:
                if (findAddressee.getText().toString().equals("查询已收件")) {
                    SkipIntentUtil.skipIntent(this, AdministratorRecipientsDisplayActivity.class);
                    finish();
                } else {
                    SkipIntentUtil.skipIntent(this, SendExpressageActivity.class);
                    finish();
                }
                break;
            case R.id.return_arrows:
                returnHome();
                break;
            case R.id.open_flashlight:
                mQRCodeView.openFlashlight();
                openFlashlight.setVisibility(View.GONE);
                closeFlashlight.setVisibility(View.VISIBLE);
                break;
            case R.id.close_flashlight:
                mQRCodeView.closeFlashlight();
                openFlashlight.setVisibility(View.VISIBLE);
                closeFlashlight.setVisibility(View.GONE);
                break;
            case R.id.choose_qrcde_from_gallery:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                break;
        }

    }

    /**
     * 返回首页
     */
    private void returnHome() {
        Constants.SOURCE = getIntent().getStringExtra("source");
        if (Constants.SOURCE != null) {
            if (Constants.SOURCE.equals("staff_home")) {
                SkipIntentUtil.skipIntent(this, StaffHomeActivity.class);
            } else {
                SkipIntentUtil.skipIntent(this, AdministratorHomeActivity.class);
            }
            finish();
        } else {
            SkipIntentUtil.skipIntent(this, AdministratorHomeActivity.class);
            finish();
        }
    }
    /**
     * 初始化收件扫码数据
     */
    private void postAScanCodeResult(String json) {
        new OkHttpResolve(this);
        aScanCodePresenter.attach(this);
        aScanCodePresenter.postJsonScanCodeResult(json, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化寄件扫码数据
     */
    private void postSendScanCodeResult(String json) {
        new OkHttpResolve(this);
        sendDisplayPresenter.attach(this);
        sendDisplayPresenter.postJsonDisplayResult(json, this, lazyLoadProgressDialog);
    }

    @Override
    public void onAScanCodeFinish(Object o) {
        RecipientsBean recipientsBean = (RecipientsBean) o;
        if (recipientsBean != null) {
            intent = null;
            String taskDefKey = recipientsBean.getAct().getTaskDefKey();
            if (taskDefKey != null) {
                switch (taskDefKey) {
                    case "uploadPhoto":
                        intent = new Intent(this, AdministratorCameraActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, getIntent().getStringExtra("source"));
                        RequestOperationUtil.intentAcceptMessageEnteringParameters(recipientsBean.getExpressAccept(), intent);
                        break;
                    case "doStationmastervoiceInputInfo":
                        intent = new Intent(this, RecipientsMessageEnteringActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, getIntent().getStringExtra("source"));
                        RequestOperationUtil.intentAcceptMessageEnteringParameters(recipientsBean.getExpressAccept(), intent);
                        break;
                    case "confirmInWorkstation":
                        intent = new Intent(this, AdministratorRecipientsActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, getIntent().getStringExtra("source"));
                        intent.putExtra("workstationId", recipientsBean.getExpressAccept().getWorkstation().getId());
                        break;
                    case "outWorkstation":
                        intent = new Intent(this, AdministratorRecipientsDomesticActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, getIntent().getStringExtra("source"));
                        intent.putExtra("containerNO", recipientsBean.getExpressAccept().getContainerNO());
                        break;
                    case "outWorkstation2":
                        intent = new Intent(this, AdministratorRecipientsDomesticActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, getIntent().getStringExtra("source"));
                        intent.putExtra("containerNO", recipientsBean.getExpressAccept().getContainerNO());
                        break;
                    case "doCourierInputInfo":
                        intent = new Intent(this, StaffInformationCollectionActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, getIntent().getStringExtra("source"));
                        break;
                    case "doCourierInputInfo2":
                        intent = new Intent(this, StaffInformationCollectionActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, getIntent().getStringExtra("source"));
                        break;

                }
                isAcceptPay(recipientsBean.getExpressAccept().getType());
                startActivity(intent);
            } else {
                Constants.SOURCE = getIntent().getStringExtra("source");
                if (Constants.SOURCE.equals("staff_home")) {
                    String businessStatus = recipientsBean.getExpressAccept().getBusinessStatus();
                    if (businessStatus != null) {
                        if (!businessStatus.equals("用户已收件")) {
                            intentRequiredParameter(recipientsBean, Constants.SOURCE, StaffUserQRCodeActivity.class);
                        } else {
                            intentRequiredParameter(recipientsBean, Constants.SOURCE, StaffExpressageMessageActivity.class);
                        }
                    } else {
                        intentRequiredParameter(recipientsBean, Constants.SOURCE, StaffUserQRCodeActivity.class);
                    }
                    startActivity(intent);
                } else {
                    intent = new Intent(this, AdministratorRecipientsOverseaActivity.class);
                    RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, getIntent().getStringExtra("source"));
                    isAcceptPay(recipientsBean.getExpressAccept().getType());
                    startActivity(intent);
                }
            }

        }
    }

    /**
     * 是否为代收包裹
     */
    private void isAcceptPay(String type) {
        if (type.equals("2")) {
            intent.putExtra("home_source", "acceptPay");
        }
    }

    /**
     * 员工跳转必要参数
     *
     * @param recipientsBean
     * @param source
     */
    private void intentRequiredParameter(RecipientsBean recipientsBean, String source, Class cl) {
        intent = new Intent(this, cl);
        RequestOperationUtil.intentAcceptNecessaryParameters(recipientsBean, intent, source);
        RecipientsBean.ExpressAcceptBean.ExpressParcelBean.ReceiverBean receiver = recipientsBean.getExpressAccept().getExpressParcel().getReceiver();
        if (receiver != null) {
            String provinceName = receiver.getProvinceName();
            String cityName = receiver.getCityName();
            String expAreaName = receiver.getExpAreaName();
            String address = receiver.getAddress();
            if (provinceName.equals(cityName)) {
                intent.putExtra("recipients", cityName + expAreaName + address);
            } else {
                intent.putExtra("recipients", provinceName + cityName + expAreaName + address);
            }
            intent.putExtra("receivername", receiver.getName());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            returnHome();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSendDisplayListFinish(Object o) {

    }

    @Override
    public void onSendDisplayBeanFinish(Object o) {
        SendDetailsBean sendDetailsBean = (SendDetailsBean) o;
        if (sendDetailsBean != null) {
            String taskKey = sendDetailsBean.getMapsender().getTaskKey();
            if (taskKey != null) {
                switch (taskKey) {
                    case "stationmasterSweep":
                        intent = new Intent(this, AdministratorExpressDetailsActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, getIntent().getStringExtra("source"));
                        intent.putExtra("workstationId", sendDetailsBean.getMapsender().getWorkstationId());
                        break;
                    case "outWorkstation":
                        intent = new Intent(this, ReceiveExpressageThreeActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "mipca_capture_outWorkstation");
                        break;
                    case "0":
                        intent = new Intent(this, ReceiveExpressageThreeActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "mipca_capture_0");
                        break;
                }
                startActivity(intent);
            }


        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {

        }
    }
    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        aScanCodePresenter.dettach();
        sendDisplayPresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}