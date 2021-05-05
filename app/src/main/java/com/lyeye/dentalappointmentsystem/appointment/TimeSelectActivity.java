package com.lyeye.dentalappointmentsystem.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TimeSelectActivity extends AppCompatActivity {

    private TextView textView_cancel, textView_date;
    private Button button1, button2, button3, button4, button5, button6, button7, button8;

    private ArrayList<Button> buttons = new ArrayList<>();

    private String dateSelected;
    private String symptom, level, remote, hospitalName, userName;
    private int userId, hospitalId;
    private List<String> hasAppointmentTimes = new ArrayList<>();
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select);

        init();

        for (int i = 0; i < 8; i++) {
            Button button = buttons.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String time = button.getText().toString();
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(TimeSelectActivity.this);
                    sweetAlertDialog.setTitleText(dateSelected)
                            .setContentText("确定预约" + time + "吗？")
                            .setConfirmText("预约").setCancelText("我再想想")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String json = "{\n" +
                                                        "    \"appointmentInfo\":{\n" +
                                                        "        \"userId\":0,\n" +
                                                        "        \"hospitalId\":0,\n" +
                                                        "        \"hospitalName\":\"" + hospitalName + "\",\n" +
                                                        "        \"userName\":\"" + userName + "\",\n" +
                                                        "        \"symptom\":\"" + symptom + "\",\n" +
                                                        "        \"date\":\"" + dateSelected + "\",\n" +
                                                        "        \"time\":\"" + time + "\",\n" +
                                                        "        \"isRemote\":" + remote + ",\n" +
                                                        "        \"isArrive\":0\n" +
                                                        "    },\n" +
                                                        "    \"inquiryInfo\":{\n" +
                                                        "        \"userId\":0,\n" +
                                                        "        \"userName\":\"" + userName + "\",\n" +
                                                        "        \"hospital\":\"" + hospitalName + "\",\n" +
                                                        "        \"symptom\":\"" + symptom + "\",\n" +
                                                        "        \"level\":\"" + level + "\",\n" +
                                                        "        \"isRemote\":" + remote + "\n" +
                                                        "    }\n" +
                                                        "}";
                                                Request request = new Request.Builder()
                                                        .url(UrlUtil.getURL("makeAnAppointment?userId=" + userId + "&hospitalId=" + hospitalId + "&isRemote=" + remote))
                                                        .post(RequestBody.create(MediaType.parse("application/json"), json))
                                                        .build();
                                                ResponseBody responseBody = new OkHttpClient().newCall(request).execute().body();
                                                String string = responseBody.string();
                                                Log.d(null, "responseBody: " + responseBody);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.d(null, "e: " + e);
                                            }
                                        }
                                    }).start();
                                    Intent intent = new Intent(TimeSelectActivity.this, AppointmentActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.cancel();
                                    ToastUtil.showMsg(TimeSelectActivity.this, "已取消");
                                }
                            })
                            .show();
                }
            });
        }

        textView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeSelectActivity.this, AppointmentDetailActivity.class);
                startActivity(intent);
            }
        });


    }

    private void init() {

        button1 = findViewById(R.id.btn_td_am1);
        button2 = findViewById(R.id.btn_td_am2);
        button3 = findViewById(R.id.btn_td_am3);
        button4 = findViewById(R.id.btn_td_am4);
        button5 = findViewById(R.id.btn_td_pm1);
        button6 = findViewById(R.id.btn_td_pm2);
        button7 = findViewById(R.id.btn_td_pm3);
        button8 = findViewById(R.id.btn_td_pm4);
        textView_date = findViewById(R.id.tv_td_date);
        textView_cancel = findViewById(R.id.tv_td_cancel);

        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);

        sharedPreferences = getSharedPreferences("JsonInfo", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 999999999);
        hospitalId = sharedPreferences.getInt("hospitalId", 999999999);
        hospitalName = sharedPreferences.getString("hospitalName", "");
        userName = sharedPreferences.getString("userName", "");

        Intent intent = getIntent();
        symptom = intent.getStringExtra("symptom");
        level = intent.getStringExtra("level");
        remote = intent.getStringExtra("remote");
        dateSelected = intent.getStringExtra("dateSelected");
        remote = remote.equals("需要远程协助") ? "1" : "0";

        textView_date.setText(dateSelected);

        hasAppointmentTimes();
    }

    private void hasAppointmentTimes() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(UrlUtil.getURL("AppointmentListByHospitalIdAndDate?hospitalId=" + hospitalId + "&date=" + dateSelected))
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
                        if (!string.equals("该诊所该日无预约信息")) {
                            try {
                                JSONArray jsonArray = new JSONArray(string);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    hasAppointmentTimes.add(jsonArray.getJSONObject(i).getString("time"));
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        describe();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    private void describe() {
        int[] count = new int[8];
        for (int i = 0; i < hasAppointmentTimes.size(); i++) {
            if (hasAppointmentTimes.get(i).equals("8:00-9:00")) {
                count[0]++;
                if (count[0] >= 2) {
                    button1.setText("预约满");
                    button1.setEnabled(false);
                }
            } else if (hasAppointmentTimes.get(i).equals("9:00-10:00")) {
                count[1]++;
                if (count[1] >= 2) {
                    button2.setText("预约满");
                    button2.setEnabled(false);
                }
            } else if (hasAppointmentTimes.get(i).equals("10:00-11:00")) {
                count[2]++;
                if (count[2] >= 2) {
                    button3.setText("预约满");
                    button3.setEnabled(false);
                }
            } else if (hasAppointmentTimes.get(i).equals("11:00-12:00")) {
                count[3]++;
                if (count[3] >= 2) {
                    button4.setText("预约满");
                    button4.setEnabled(false);
                }
            } else if (hasAppointmentTimes.get(i).equals("14:30-15:30")) {
                count[4]++;
                if (count[4] >= 2) {
                    button5.setText("预约满");
                    button5.setEnabled(false);
                }
            } else if (hasAppointmentTimes.get(i).equals("15:30-16:30")) {
                count[5]++;
                if (count[5] >= 2) {
                    button6.setText("预约满");
                    button6.setEnabled(false);
                }
            } else if (hasAppointmentTimes.get(i).equals("16:30-17:30")) {
                count[6]++;
                if (count[6] >= 2) {
                    button7.setText("预约满");
                    button7.setEnabled(false);
                }
            } else if (hasAppointmentTimes.get(i).equals("17:30-18:30")) {
                count[7]++;
                if (count[7] >= 2) {
                    button8.setText("预约满");
                    button8.setEnabled(false);
                }
            }
        }
    }
}