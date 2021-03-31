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
import com.lyeye.dentalappointmentsystem.entity.BmobAppointmentInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AppointmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textView_flush;
    private ImageView imageView_appointment;

    private List<BmobAppointmentInfo> appointmentScheduleList = new ArrayList<BmobAppointmentInfo>();
    private String userName;
    private SharedPreferences sharedPreferences;


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


        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        userName = sharedPreferences.getString("username", "");
    }

    private void initRecyclerView() {
        BmobQuery<BmobAppointmentInfo> bmobAppointmentInfoBmobQuery = new BmobQuery<>();
        bmobAppointmentInfoBmobQuery.addWhereEqualTo("userName", userName);
        bmobAppointmentInfoBmobQuery.findObjects(new FindListener<BmobAppointmentInfo>() {
            @Override
            public void done(List<BmobAppointmentInfo> list, BmobException e) {
                appointmentScheduleList = list;
                recyclerView.setLayoutManager(new XLinearLayoutManager(AppointmentActivity.this, LinearLayoutManager.VERTICAL, false));
                final AppointmentRecyclerViewAdapter appointmentRecyclerViewAdapter
                        = new AppointmentRecyclerViewAdapter(AppointmentActivity.this, appointmentScheduleList);
                appointmentRecyclerViewAdapter.setList(appointmentScheduleList);
                recyclerView.setAdapter(appointmentRecyclerViewAdapter);
            }
        });
    }
}
