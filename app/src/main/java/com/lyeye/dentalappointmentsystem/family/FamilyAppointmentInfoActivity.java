package com.lyeye.dentalappointmentsystem.family;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.MyFamilyAppointmentRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.mapper.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.util.UserLoginMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class FamilyAppointmentInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imageView_noticeInfo;
    private TextView textView_appointment;

    private User user;
    private String userName;
    private long userId;
    private AppointmentInfoImpl appointmentInfoImpl;
    private List<AppointmentInfo> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_appointment_info);

        recyclerView = findViewById(R.id.rv_fai_appointmentInfo);
        imageView_noticeInfo = findViewById(R.id.iv_fai_noticeInfo);
        textView_appointment = findViewById(R.id.tv_fai_appointment);

        appointmentInfoImpl = new AppointmentInfoImpl(FamilyAppointmentInfoActivity.this);

        EventBus.getDefault().register(FamilyAppointmentInfoActivity.this);

        imageView_noticeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamilyAppointmentInfoActivity.this, FamilyNoticeInfoActivity.class);
                startActivity(intent);
            }
        });

        scheduleList = appointmentInfoImpl.findAppointmentInfoByUserId(userId);
        recyclerView.setLayoutManager(new XLinearLayoutManager(FamilyAppointmentInfoActivity.this, LinearLayoutManager.VERTICAL, false));
        MyFamilyAppointmentRecyclerViewAdapter myFamilyAppointmentRecyclerViewAdapter = new MyFamilyAppointmentRecyclerViewAdapter(FamilyAppointmentInfoActivity.this, scheduleList);
        recyclerView.setAdapter(myFamilyAppointmentRecyclerViewAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUserLoginMessageEvent(UserLoginMessageEvent userLoginMessageEvent) {
        user = userLoginMessageEvent.getUser();
        userId = user.getUserId();
        userName = user.getUserName();
        textView_appointment.setText(userName + "的预约");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(FamilyAppointmentInfoActivity.this);
    }
}
