package com.lyeye.dentalappointmentsystem.appointment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.AppointmentRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;

import java.util.ArrayList;
import java.util.List;

public class AppointmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textView_flush;
    private ImageView imageView_appointment;

    private List<AppointmentInfo> appointmentScheduleList = new ArrayList<AppointmentInfo>();
    private SharedPreferences sharedPreferences;
    private long userId;
    private AppointmentInfoImpl appointmentInfoImpl;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        init();
        initRecyclerView();

        /*刷新按钮*/
        textView_flush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecyclerView();
            }
        });

        imageView_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentActivity.this, AppointmentDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {

        recyclerView = findViewById(R.id.rv_am_appointment_info);
        textView_flush = findViewById(R.id.tv_am_reflesh);
        imageView_appointment = findViewById(R.id.iv_am_add);

        appointmentInfoImpl = new AppointmentInfoImpl(AppointmentActivity.this);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", 99999999);
    }

    private void initRecyclerView() {
        appointmentScheduleList = appointmentInfoImpl.findAppointmentInfoByUserId(userId);
        recyclerView.setLayoutManager(new XLinearLayoutManager(AppointmentActivity.this, LinearLayoutManager.VERTICAL, false));
        final AppointmentRecyclerViewAdapter appointmentRecyclerViewAdapter
                = new AppointmentRecyclerViewAdapter(AppointmentActivity.this, appointmentScheduleList);
        appointmentRecyclerViewAdapter.setList(appointmentScheduleList);
        recyclerView.setAdapter(appointmentRecyclerViewAdapter);
    }
}
