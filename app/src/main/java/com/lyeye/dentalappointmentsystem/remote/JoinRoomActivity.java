package com.lyeye.dentalappointmentsystem.remote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.appointment.DateSelectionActivity;
import com.lyeye.dentalappointmentsystem.home.MainActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class JoinRoomActivity extends AppCompatActivity {

    private EditText editText_JoinRoomName, editText_JoinStreamName;
    private Button button_joinRoom;

    private String joinRoomName, joinStreamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);

        String[] permissionNeeded = {
                "android.permission.CAMERA",
                "android.permission.RECORD_AUDIO"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissionNeeded, 101);
            }
        }

        init();

        button_joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_JoinRoomName.getText().toString().length() == 0 || editText_JoinStreamName.getText().toString().length() == 0) {
                    editText_JoinRoomName.setHint("不能为空");
                    editText_JoinStreamName.setHint("不能为空");
                } else {
                    Intent intent = new Intent(JoinRoomActivity.this, RemoteActivity.class);
                    intent.putExtra("JoinRoomName", editText_JoinRoomName.getText().toString());
                    intent.putExtra("JoinStreamName", editText_JoinStreamName.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {
        editText_JoinRoomName = findViewById(R.id.et_jr_room);
        editText_JoinStreamName = findViewById(R.id.et_jr_stream);
        button_joinRoom = findViewById(R.id.btn_jr_join);

        joinRoomName = editText_JoinRoomName.getText().toString();
        joinStreamName = editText_JoinStreamName.getText().toString();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(JoinRoomActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("是否结束本次远程").setConfirmText("是的").setCancelText("我再想想")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(JoinRoomActivity.this, MainActivity.class);
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