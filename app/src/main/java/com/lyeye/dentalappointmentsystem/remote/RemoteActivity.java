package com.lyeye.dentalappointmentsystem.remote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.lyeye.dentalappointmentsystem.R;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.constants.ZegoScenario;
import im.zego.zegoexpress.entity.ZegoCanvas;
import im.zego.zegoexpress.entity.ZegoUser;

public class RemoteActivity extends AppCompatActivity {

    private TextureView textureView_doctor, textureView_patient;
    private ImageView imageView_handUp;

    private ZegoExpressEngine engine;
    private ZegoUser user;
    private long appID = 970865325L;
    private String appSign = "e387b620f06eba312b33a030b9407118a2bee791cd8de5f3f60e4826d800de56";
    private SharedPreferences sharedPreferences;
    private String userName, joinRoomName, joinStreamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        init();

        engine.loginRoom(joinRoomName, user);
        engine.startPublishingStream(userName);
        engine.startPreview(new ZegoCanvas(textureView_doctor));
        engine.startPlayingStream(joinStreamName, new ZegoCanvas(textureView_patient));

        imageView_handUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                engine.stopPreview();
                engine.stopPublishingStream();
                engine.logoutRoom(joinRoomName);
                Intent intent = new Intent(getApplicationContext(), JoinRoomActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        textureView_doctor = findViewById(R.id.textureView_remote_doctor);
        textureView_patient = findViewById(R.id.textureView_remote_patient);
        imageView_handUp = findViewById(R.id.iv_remote_exit);
        engine = ZegoExpressEngine.createEngine(appID, appSign, true, ZegoScenario.GENERAL, getApplication(), null);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        userName = sharedPreferences.getString("username", null);
        user = new ZegoUser(userName);

        Intent intent = getIntent();
        joinRoomName = intent.getStringExtra("JoinRoomName");
        joinStreamName = intent.getStringExtra("JoinStreamName");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /** 销毁 SDK */
        ZegoExpressEngine.destroyEngine(null);
    }
}
