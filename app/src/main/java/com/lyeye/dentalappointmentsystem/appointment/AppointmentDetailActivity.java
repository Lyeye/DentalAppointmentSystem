package com.lyeye.dentalappointmentsystem.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AppointmentDetailActivity extends AppCompatActivity {

    private TextView textView_appointment;
    private NiceSpinner ns_symptom, ns_level, ns_remote;
    List<String> symptomList = new LinkedList<>();
    List<String> levelList = new LinkedList<>(Arrays.asList("严重", "一般", "轻微"));
    List<String> remoteList = new LinkedList<>(Arrays.asList("需要远程协助", "不需要远程协助"));
    private String symptom = "蛀牙", level = "严重", remote = "需要远程协助";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(UrlUtil.getURL("getAllSymptom"))
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
                                symptomList.add(jsonArray.getJSONObject(i).getString("name"));
                                Log.d(null, "symptomList: " + symptomList);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    init();
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
        textView_appointment = findViewById(R.id.tv_ad_appointment);
        ns_symptom = findViewById(R.id.ns_ad_describe);
        ns_level = findViewById(R.id.ns_ad_level);
        ns_remote = findViewById(R.id.ns_ad_remote);

        ns_symptom.attachDataSource(symptomList);
        ns_level.attachDataSource(levelList);
        ns_remote.attachDataSource(remoteList);

        ns_symptom.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                symptom = (String) parent.getItemAtPosition(position);
            }
        });

        ns_level.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                level = (String) parent.getItemAtPosition(position);
            }
        });

        ns_remote.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                remote = (String) parent.getItemAtPosition(position);
            }
        });

        textView_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentDetailActivity.this, DateSelectionActivity.class);
                intent.putExtra("detail_symptom", symptom);
                intent.putExtra("detail_level", level);
                intent.putExtra("detail_remote", remote);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(AppointmentDetailActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("是否取消本次预约").setConfirmText("是的").setCancelText("我再想想")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(AppointmentDetailActivity.this, MainActivity.class);
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
