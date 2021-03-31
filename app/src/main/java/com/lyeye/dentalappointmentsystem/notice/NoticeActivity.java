package com.lyeye.dentalappointmentsystem.notice;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.BmobAppointmentInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NoticeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SharedPreferences sharedPreferences;
    private String userName;
    private List<BmobAppointmentInfo> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        recyclerView = findViewById(R.id.rv_notice_notice);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        userName = sharedPreferences.getString("username", "");
        BmobQuery<BmobAppointmentInfo> bmobAppointmentInfoBmobQuery = new BmobQuery<>();
        bmobAppointmentInfoBmobQuery.addWhereEqualTo("userName", userName);
        bmobAppointmentInfoBmobQuery.findObjects(new FindListener<BmobAppointmentInfo>() {
            @Override
            public void done(List<BmobAppointmentInfo> list, BmobException e) {
                if (list != null) {
                    noticeList = list;
                    recyclerView.setLayoutManager(new XLinearLayoutManager(NoticeActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(new NoticeRecyclerViewAdapter(NoticeActivity.this, noticeList));
                }
            }
        });
    }
}
