package com.lyeye.dentalappointmentsystem.administration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.UserRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.impl.UserImpl;
import com.lyeye.dentalappointmentsystem.remote.JoinRoomActivity;
import com.lyeye.dentalappointmentsystem.welcome.WelcomeActivity;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textView_admin;
    private Button button_clearUser, button_clearAppointment;

    private SharedPreferences sharedPreferences;
    private UserImpl userImpl;
    private AppointmentInfoImpl appointmentInfoImpl;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        init();

        button_clearUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(UserListActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("确定清空用户信息吗").setConfirmText("是的").setCancelText("我再想想")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                userImpl.clear();
                                appointmentInfoImpl.clear();
                                Intent intent = new Intent(UserListActivity.this, UserListActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).show();
            }
        });

        button_clearAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(UserListActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("确定清空预约信息吗").setConfirmText("是的").setCancelText("我再想想")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                appointmentInfoImpl.clear();
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).show();
            }
        });

        recyclerView.setLayoutManager(new XLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new UserRecyclerViewAdapter(this, userList));
    }

    private void init() {
        recyclerView = findViewById(R.id.rv_ul_user_list);
        textView_admin = findViewById(R.id.tv_ul_admin);
        button_clearUser = findViewById(R.id.btn_ul_clear_user);
        button_clearAppointment = findViewById(R.id.btn_ul_clear_appointmentInfo);
        sharedPreferences = getSharedPreferences("admin_info", MODE_PRIVATE);
        userImpl = new UserImpl(UserListActivity.this);
        appointmentInfoImpl = new AppointmentInfoImpl(UserListActivity.this);

        textView_admin.setText("你好" + sharedPreferences.getString("adminName", ""));
        userList = userImpl.findAll();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(UserListActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("是否退出").setConfirmText("是的").setCancelText("我再想想")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(UserListActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}