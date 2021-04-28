package com.lyeye.dentalappointmentsystem.family;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.FamilyNoticeRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.adapter.NoticeRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.notice.NoticeActivity;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;
import com.lyeye.dentalappointmentsystem.util.UserLoginMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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

public class FamilyNoticeInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private String userId;
    private List<JSONObject> noticeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_notice);

        init();

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
                                    recyclerView.setLayoutManager(new XLinearLayoutManager(FamilyNoticeInfoActivity.this, LinearLayoutManager.VERTICAL, false));
                                    recyclerView.setAdapter(new FamilyNoticeRecyclerViewAdapter(FamilyNoticeInfoActivity.this, noticeList));
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

    private void init() {
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        recyclerView = findViewById(R.id.rv_fni_notice);
    }

}
