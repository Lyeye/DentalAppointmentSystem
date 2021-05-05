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
import com.lyeye.dentalappointmentsystem.adapter.MyFamilyAppointmentRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FamilyAppointmentInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imageView_noticeInfo;
    private TextView textView_appointment;


    private String userId;
    private List<JSONObject> appointmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_appointment_info);

        init();
        getAppointmentList();
    }

    private void init() {
        recyclerView = findViewById(R.id.rv_fai_appointmentInfo);
        imageView_noticeInfo = findViewById(R.id.iv_fai_noticeInfo);
        textView_appointment = findViewById(R.id.tv_fai_appointment);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        textView_appointment.setText(intent.getStringExtra("userName") + "的预约");

        imageView_noticeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamilyAppointmentInfoActivity.this, FamilyNoticeInfoActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    private void getAppointmentList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(UrlUtil.getURL("AppointmentListByUserId?userId=" + userId))
                        .get()
                        .build();
                new OkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String string = response.body().string();
                        try {
                            JSONArray jsonArray = new JSONArray(string);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                appointmentList.add(jsonArray.getJSONObject(i));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setLayoutManager(new XLinearLayoutManager(FamilyAppointmentInfoActivity.this, LinearLayoutManager.VERTICAL, false));
                                    MyFamilyAppointmentRecyclerViewAdapter myFamilyAppointmentRecyclerViewAdapter = new MyFamilyAppointmentRecyclerViewAdapter(FamilyAppointmentInfoActivity.this, appointmentList);
                                    recyclerView.setAdapter(myFamilyAppointmentRecyclerViewAdapter);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
}
