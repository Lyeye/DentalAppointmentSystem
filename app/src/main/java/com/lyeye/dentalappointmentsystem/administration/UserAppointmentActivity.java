package com.lyeye.dentalappointmentsystem.administration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.UserAppointmentInfoRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;

import java.util.List;

public class UserAppointmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private String userId;
    private AppointmentInfoImpl appointmentInfoImpl;
    private List<AppointmentInfo> appointmentInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment);

        init();
        recyclerView.setLayoutManager(new XLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new UserAppointmentInfoRecyclerViewAdapter(this, appointmentInfoList));
    }

    private void init() {
        recyclerView = findViewById(R.id.rv_uai_appointmentInfo);
        appointmentInfoImpl = new AppointmentInfoImpl(UserAppointmentActivity.this);
//        userId = getIntent().getLongExtra("userId", 99999999);
        Log.d(null, "init: " + userId);
        appointmentInfoList = appointmentInfoImpl.findAppointmentInfoByUserId(userId);
    }
}