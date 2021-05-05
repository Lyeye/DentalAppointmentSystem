package com.lyeye.dentalappointmentsystem.remote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.constants.ZegoScenario;
import im.zego.zegoexpress.entity.ZegoCanvas;
import im.zego.zegoexpress.entity.ZegoUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RemoteActivity extends AppCompatActivity {

    private TextureView textureView_doctor, textureView_patient;
    private ImageView imageView_handUp;

    private ZegoExpressEngine engine;
    private ZegoUser user;
    private long appID = 970865325L;
    private String appSign = "e387b620f06eba312b33a030b9407118a2bee791cd8de5f3f60e4826d800de56";
    private SharedPreferences sharedPreferences;
    private String userId, userName, date, startTime, joinRoomName, joinStreamName;

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
                uploadData();
            }
        });
    }


    private void init() {
        textureView_doctor = findViewById(R.id.textureView_remote_doctor);
        textureView_patient = findViewById(R.id.textureView_remote_patient);
        imageView_handUp = findViewById(R.id.iv_remote_exit);
        engine = ZegoExpressEngine.createEngine(appID, appSign, true, ZegoScenario.GENERAL, getApplication(), null);

        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        userId = String.valueOf(sharedPreferences.getInt("userId", 999999999));
        userName = sharedPreferences.getString("userName", "");
        user = new ZegoUser(userName);

        Intent intent = getIntent();
        startTime = intent.getStringExtra("startTime");
        date = intent.getStringExtra("date");
        joinRoomName = intent.getStringExtra("roomName");
        joinStreamName = intent.getStringExtra("streamName");
    }

    private void uploadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = "{\n" +
                            "\t\"userId\":\"" + userId + "\",\n" +
                            "\t\"userName\":\"" + userName + "\",\n" +
                            "\t\"roomName\":\"" + joinRoomName + "\",\n" +
                            "\t\"date\":\"" + date + "\",\n" +
                            "\t\"startTime\":\"" + startTime + "\",\n" +
                            "\t\"finishTime\":\"" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\"\n" +
                            "}";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(UrlUtil.getURL("remote?userId=" + userId))
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    okHttpClient.newCall(request).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), JoinRoomActivity.class);
                            startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), JoinRoomActivity.class);
                            ToastUtil.showMsg(getApplicationContext(), "数据上传不成功");
                            startActivity(intent);
                        }
                    });
                    System.out.println("异常：" + e);
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /** 销毁 SDK */
        ZegoExpressEngine.destroyEngine(null);
    }
}
