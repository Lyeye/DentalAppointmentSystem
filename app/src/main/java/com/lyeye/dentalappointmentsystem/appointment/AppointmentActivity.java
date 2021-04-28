package com.lyeye.dentalappointmentsystem.appointment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.AppointmentRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.remote.JoinRoomActivity;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AppointmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textView_appointment, textView_refresh;

    private List<JSONObject> appointmentList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private int userId;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        init();
        getAppointmentList();
        initRecyclerView();

        textView_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecyclerView();
            }
        });

        textView_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentActivity.this, AppointmentDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {

        recyclerView = findViewById(R.id.rv_am_appointment_info);
        textView_appointment = findViewById(R.id.tv_am_appointment);
        textView_refresh = findViewById(R.id.tv_am_reflesh);

        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 99999999);
        Log.d(null, "userId: " + userId);


    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new XLinearLayoutManager(AppointmentActivity.this, LinearLayoutManager.VERTICAL, false));
        final AppointmentRecyclerViewAdapter appointmentRecyclerViewAdapter
                = new AppointmentRecyclerViewAdapter(AppointmentActivity.this, appointmentList);
        recyclerView.setAdapter(appointmentRecyclerViewAdapter);
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(AppointmentActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("返回主界面？").setConfirmText("是的").setCancelText("我再想想")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(AppointmentActivity.this, MainActivity.class);
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
