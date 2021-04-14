package com.lyeye.dentalappointmentsystem.notice;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.NoticeRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;

import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SharedPreferences sharedPreferences;
    private long userId;
    private List<AppointmentInfo> noticeList;
    private AppointmentInfoImpl appointmentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        recyclerView = findViewById(R.id.rv_notice_notice);

        appointmentInfo = new AppointmentInfoImpl(NoticeActivity.this);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", 99999999);
        List<AppointmentInfo> appointmentInfosByUserId = appointmentInfo.findAppointmentInfoByUserId(userId);
        noticeList = appointmentInfosByUserId;
        recyclerView.setLayoutManager(new XLinearLayoutManager(NoticeActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new NoticeRecyclerViewAdapter(NoticeActivity.this, noticeList));
    }
}
