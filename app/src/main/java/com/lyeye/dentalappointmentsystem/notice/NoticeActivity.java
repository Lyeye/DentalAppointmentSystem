package com.lyeye.dentalappointmentsystem.notice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.NoticeRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;
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

public class NoticeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textView_reflesh;

    private SharedPreferences sharedPreferences;
    private int userId;

    private List<JSONObject> noticeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        init();

        textView_reflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new XLinearLayoutManager(NoticeActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new NoticeRecyclerViewAdapter(NoticeActivity.this, noticeList));
            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.rv_notice_notice);
        textView_reflesh = findViewById(R.id.tv_notice_reflesh);
        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 99999999);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(UrlUtil.getURL("myNotice?userId=" + userId))
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
                                noticeList.add(jsonArray.getJSONObject(i));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setLayoutManager(new XLinearLayoutManager(NoticeActivity.this, LinearLayoutManager.VERTICAL, false));
                                    recyclerView.setAdapter(new NoticeRecyclerViewAdapter(NoticeActivity.this, noticeList));
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
