package com.lyeye.dentalappointmentsystem.remote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lyeye.dentalappointmentsystem.R;

public class CreateOrJoinARoomActivity extends AppCompatActivity {

    private EditText editText_CreateRoomName, editText_CreateStreamName, editText_JoinRoomName,editText_JoinStreamName;
    private Button button_createRoom, button_joinRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join_a_room);

        String[] permissionNeeded = {
                "android.permission.CAMERA",
                "android.permission.RECORD_AUDIO"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissionNeeded, 101);
            }
        }

        String createRoomName = editText_CreateRoomName.getText().toString();
        String createStreamName = editText_CreateStreamName.getText().toString();
        String joinRoomName = editText_JoinRoomName.getText().toString();
        String joinStreamName = editText_JoinStreamName.getText().toString();
        button_createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createRoomName.length() == 0 || createStreamName.length() == 0) {
                    editText_CreateRoomName.setHint("不能为空");
                    editText_CreateRoomName.setHintTextColor(Color.parseColor("#FFF"));
                    editText_CreateStreamName.setHint("不能为空");
                    editText_CreateStreamName.setHintTextColor(Color.parseColor("#FFF"));
                } else {
                    Intent intent = new Intent(CreateOrJoinARoomActivity.this, RemoteActivity.class);
                    intent.putExtra("CreateRoomName", createRoomName);
                    intent.putExtra("CreateStreamName", createStreamName);
                    startActivity(intent);
                }
            }
        });
        button_joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (joinRoomName.length() == 0 || joinStreamName.length() == 0) {
                    editText_JoinRoomName.setHint("不能为空");
                    editText_JoinRoomName.setHintTextColor(Color.parseColor("#FFF"));
                    editText_JoinStreamName.setHint("不能为空");
                    editText_JoinStreamName.setHintTextColor(Color.parseColor("#FFF"));
                } else {
                    Intent intent = new Intent(CreateOrJoinARoomActivity.this, RemoteActivity.class);
                    intent.putExtra("JoinRoomName", joinRoomName);
                    intent.putExtra("JoinStreamName", joinStreamName);
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {
        editText_CreateRoomName = findViewById(R.id.et_cr_room);
        editText_CreateStreamName = findViewById(R.id.et_cr_stream);
        editText_JoinRoomName = findViewById(R.id.et_jr_room);
        editText_JoinStreamName = findViewById(R.id.et_jr_stream);
        button_createRoom = findViewById(R.id.btn_cr_create);
        button_joinRoom = findViewById(R.id.btn_jr_join);
    }
}