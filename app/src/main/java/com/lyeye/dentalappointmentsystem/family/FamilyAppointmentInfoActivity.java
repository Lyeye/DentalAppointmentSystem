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
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.BmobAppointmentInfo;
import com.lyeye.dentalappointmentsystem.entity.BmobUser;
import com.lyeye.dentalappointmentsystem.util.UserLoginMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FamilyAppointmentInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imageView_noticeInfo;
    private TextView textView_appointment;

    private BmobUser user;
    private String userName;
    private List<BmobAppointmentInfo> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_appointment_info);

        recyclerView = findViewById(R.id.rv_fai_appointmentInfo);
        imageView_noticeInfo = findViewById(R.id.iv_fai_noticeInfo);
        textView_appointment = findViewById(R.id.tv_fai_appointment);

        EventBus.getDefault().register(FamilyAppointmentInfoActivity.this);

        imageView_noticeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamilyAppointmentInfoActivity.this, FamilyNoticeInfoActivity.class);
                startActivity(intent);
            }
        });

        BmobQuery<BmobAppointmentInfo> bmobAppointmentInfoBmobQuery = new BmobQuery<>();
        bmobAppointmentInfoBmobQuery.addWhereEqualTo("userName", userName);
        bmobAppointmentInfoBmobQuery.findObjects(new FindListener<BmobAppointmentInfo>() {
            @Override
            public void done(List<BmobAppointmentInfo> list, BmobException e) {
                if (list != null){
                    scheduleList = list;
                    recyclerView.setLayoutManager(new XLinearLayoutManager(FamilyAppointmentInfoActivity.this, LinearLayoutManager.VERTICAL, false));
                    MyFamilyAppointmentRecyclerViewAdapter myFamilyAppointmentRecyclerViewAdapter = new MyFamilyAppointmentRecyclerViewAdapter(FamilyAppointmentInfoActivity.this, scheduleList);
                    recyclerView.setAdapter(myFamilyAppointmentRecyclerViewAdapter);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUserLoginMessageEvent(UserLoginMessageEvent userLoginMessageEvent) {
        user = userLoginMessageEvent.getUser();
        userName = user.getUserName();
        textView_appointment.setText(userName + "的预约");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(FamilyAppointmentInfoActivity.this);
    }
}
