package com.example.me_jie.snailexpress_staff.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.adapter.ExceedTimeParcelAdapter;
import com.example.me_jie.snailexpress_staff.utils.StatusBarUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 超期包裹
 */
public class ExceedTimeParcelActivity extends AutoLayoutActivity {

    @InjectView(R.id.exceed_time_rv)
    RecyclerView exceedTimeRv;
    private CommonAdapter<String> commonRVAdapter;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exceed_time_parcel);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        exceedTimeRv.setLayoutManager(new LinearLayoutManager(this));
        addList();

        exceedTimeRv.setAdapter(new ExceedTimeParcelAdapter(this));
    }
    private void addList(){
        if(list==null){
            list = new ArrayList<>();
            for (int i = 0; i <5 ; i++) {
                list.add("A"+i);
            }
        }
    }
}
