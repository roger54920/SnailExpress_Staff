package com.example.me_jie.snailexpress_staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.ChatBean;
import com.example.me_jie.snailexpress_staff.custom.MyScrollview;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 员工快递详情 确认寄件
 */
public class StaffExpressageDetailsActivity extends AutoLayoutActivity {

    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.chat_rv)
    RecyclerView chatRv;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.expressage)
    LinearLayout expressage;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.promptly_send)
    Button promptlySend;
    @InjectView(R.id.sender_name)
    TextView senderName;
    @InjectView(R.id.crate_date)
    TextView crateDate;
    @InjectView(R.id.sender_address)
    TextView senderAddress;
    @InjectView(R.id.sender_mobile)
    TextView senderMobile;
    @InjectView(R.id.receiver_name)
    TextView receiverName;
    @InjectView(R.id.receiver_address)
    TextView receiverAddress;
    @InjectView(R.id.receiver_mobile)
    TextView receiverMobile;
    @InjectView(R.id.shipper_code)
    TextView shipperCode;
    @InjectView(R.id.cost)
    TextView cost;
    @InjectView(R.id.weight)
    TextView weight;
    @InjectView(R.id.sc_rl)
    RelativeLayout scRl;
    @InjectView(R.id.cw_rl)
    RelativeLayout cwRl;
    private List<ChatBean> chatList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_expressage_details);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        chatRv.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        intent = getIntent();
        Constants.SOURCE = intent.getStringExtra("source");
        if (Constants.SOURCE != null) {
            switch (Constants.SOURCE) {
                case "weighingDetermination":
                    promptlySend.setVisibility(View.VISIBLE);
                    break;
                case "weighingDetermination2":
                    promptlySend.setVisibility(View.VISIBLE);
                    break;
                case "0":
                    cwRl.setVisibility(View.VISIBLE);
                    cost.setText("快递费用：" + intent.getStringExtra("cost")+"元");
                    weight.setText("包裹重量：" + intent.getStringExtra("weight")+"kg");
                    promptlySend.setVisibility(View.GONE);
                    break;
            }
            senderName.setText(intent.getStringExtra("sendername"));
            crateDate.setText(intent.getStringExtra("createDate"));
            senderAddress.setText(intent.getStringExtra("sender"));
            senderMobile.setText(intent.getStringExtra("sendermobile"));
            receiverName.setText(intent.getStringExtra("receivername"));
            receiverAddress.setText(intent.getStringExtra("recipients"));
            receiverMobile.setText(intent.getStringExtra("receivermobile"));
            shipperCode.setText("快递公司：" + intent.getStringExtra("shipperCode"));
        }
//        //聊天适配器
//        initMsg();
//        chatRv.setLayoutManager(new FullyLinearLayoutManager(this));
//        chatRv.setAdapter(new CharAdapter(this, chatList));
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

    @OnClick({R.id.return_arrows, R.id.promptly_send,R.id.zoom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.zoom:
                if (zoom.getText().equals("收起")) {
                    expressage.setVisibility(View.GONE);
                    zoom.setText("展开");
                } else if (zoom.getText().equals("展开")) {
                    expressage.setVisibility(View.VISIBLE);
                    zoom.setText("收起");
                }
                break;
            case R.id.promptly_send:
                intent = new Intent(this, StaffExpressageDetailsWeightActivity.class);
                intent.putExtra("source",Constants.SOURCE);
                intent.putExtra("senderId", getIntent().getStringExtra("senderId"));
                intent.putExtra("taskId", getIntent().getStringExtra("taskId"));
                intent.putExtra("procInsId", getIntent().getStringExtra("procInsId"));
                startActivity(intent);
                break;
        }
    }
}