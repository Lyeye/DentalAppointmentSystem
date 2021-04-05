package com.lyeye.dentalappointmentsystem.family;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.mapper.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.util.UserLoginMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class FamilyNoticeInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private User user;
    private long userId;
    private AppointmentInfoImpl appointmentInfoImpl;
    private List<AppointmentInfo> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_notice);

        recyclerView = findViewById(R.id.rv_fni_notice);

        appointmentInfoImpl = new AppointmentInfoImpl(FamilyNoticeInfoActivity.this);

        EventBus.getDefault().register(this);

        List<AppointmentInfo> appointmentInfosByUserId = appointmentInfoImpl.findAppointmentInfoByUserId(userId);
        noticeList = appointmentInfosByUserId;
        recyclerView.setLayoutManager(new XLinearLayoutManager(FamilyNoticeInfoActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new FamilyNoticeRecyclerViewAdapter(FamilyNoticeInfoActivity.this, noticeList));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUserLoginMessageEvent(UserLoginMessageEvent userLoginMessageEvent) {
        user = userLoginMessageEvent.getUser();
        userId = user.getUserId();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
