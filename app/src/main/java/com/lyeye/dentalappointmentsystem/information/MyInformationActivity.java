package com.lyeye.dentalappointmentsystem.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.remote.JoinRoomActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyInformationActivity extends AppCompatActivity {

    private TextView textView_username, textView_gender, textView_phone, textView_email, textView_dNumber, textView_birthday;
    private Button button_update;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        init();

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyInformationActivity.this, UpdateInformationActivity.class));
            }
        });
    }

    private void init() {
        textView_username = findViewById(R.id.tv_mi_name);
        textView_gender = findViewById(R.id.tv_mi_gender);
        textView_birthday = findViewById(R.id.tv_mi_birthday);
        textView_email = findViewById(R.id.tv_mi_email);
        textView_phone = findViewById(R.id.tv_mi_phone);
        textView_dNumber = findViewById(R.id.tv_mi_diagnosisNumber);
        button_update = findViewById(R.id.btn_mi_update);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        textView_username.setText("姓名：" + sharedPreferences.getString("username", ""));
        textView_gender.setText("性别：" + sharedPreferences.getString("gender", ""));
        textView_birthday.setText("生日：" + sharedPreferences.getString("birthday", ""));
        textView_phone.setText("手机：" + sharedPreferences.getString("userPhone", ""));
        textView_email.setText("邮箱：" + sharedPreferences.getString("userEmail", ""));
        textView_dNumber.setText("诊察号：" + sharedPreferences.getString("diagnosisNumber", ""));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyInformationActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("是否退回主页面").setConfirmText("是的").setCancelText("我再想想")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(MyInformationActivity.this, MainActivity.class);
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