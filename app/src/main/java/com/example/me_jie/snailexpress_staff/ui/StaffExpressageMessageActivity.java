package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.ChatBean;
import com.example.me_jie.snailexpress_staff.custom.FullPhotoView;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.utils.RequestOperationUtil;
import com.example.me_jie.snailexpress_staff.utils.SkipIntentUtil;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 员工快递信息
 */
public class StaffExpressageMessageActivity extends AutoLayoutActivity {

    @InjectView(R.id.chat_recyclerview)
    RecyclerView chatRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.sir_name)
    TextView sirName;
    @InjectView(R.id.sir_address)
    TextView sirAddress;
    private List<ChatBean> chatList;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_expressage_message);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        chatRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        intent = getIntent();
        Constants.SOURCE = intent.getStringExtra("source");
        if (Constants.SOURCE != null) {
            RequestOperationUtil.setGlide(this,getIntent().getStringExtra("img_url"),fullPhotoView);
            haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
            expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
            sirAddress.setText(intent.getStringExtra("recipients"));
            sirName.setText(intent.getStringExtra("receivername"));

        }
//        //聊天适配器
//        initMsg();
//        chatRecyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
//        chatRecyclerview.setAdapter(new CharAdapter(this, chatList));
    }

    private void initMsg() {
        if (chatList == null) {
            chatList = new ArrayList<>();
            ChatBean msg1 = new ChatBean("请问几点可以送货？", ChatBean.TYPE_RECEIVE);
            chatList.add(msg1);
            ChatBean msg2 = new ChatBean("今天13:30吧！", ChatBean.TYPE_SEND);
            chatList.add(msg2);
            ChatBean msg = new ChatBean("好的！", ChatBean.TYPE_RECEIVE);
            chatList.add(msg);
        }

    }
    /**
     * 返回来源页面
     */
    private void sourceReturn(){
        Constants.SOURCE = getIntent().getStringExtra("source");
        switch (Constants.SOURCE) {
            case "then_allocation_parcel":
                SkipIntentUtil.skipIntent(this, ThenAllocationParcelActivity.class);
                break;
            case "courier_service_parcel_rq":
                SkipIntentUtil.skipIntent(this, StaffCourierServiceParcelActivity.class);
                break;
            case "staff_home":
                intent = new Intent(this, MipcaActivityCapture.class);
                intent.putExtra("source", Constants.SOURCE );
                startActivity(intent);
                break;
        }
        finish();
    }
    @OnClick({R.id.return_arrows, R.id.haulier_source, R.id.express_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                sourceReturn();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            sourceReturn();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
