package com.lyeye.dentalappointmentsystem.appointment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;
import com.maning.calendarlibrary.MNCalendar;
import com.maning.calendarlibrary.listeners.OnCalendarItemClickListener;
import com.maning.calendarlibrary.model.MNCalendarConfig;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DateSelectionActivity extends AppCompatActivity {

    private TextView textView_month, textView_year;
    private CalendarView calendarView_calendar;
    private MNCalendar mnCalendar;

    ArrayList<String> dateList = new ArrayList<>();
    private List<String> hasAppointmentTimes = new ArrayList<>();
    private List<Calendar> calendars = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    private String symptom, level, remote;
    private int hospitalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);

        init();
        getAppointmentList();

        calendarView_calendar.setOnDateLongClickListener(new CalendarView.OnDateLongClickListener() {
            @Override
            public void onDateLongClick(Calendar calendar) {
                String dateSelected = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay();
                Intent intent = new Intent(DateSelectionActivity.this, TimeSelectActivity.class);
                intent.putExtra("symptom", symptom);
                intent.putExtra("level", level);
                intent.putExtra("remote", remote);
                intent.putExtra("dateSelected", dateSelected);
                startActivity(intent);
            }
        });
        calendarView_calendar.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                textView_year.setText(year + "年");
                textView_month.setText(month + "月");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DateSelectionActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("是否取消本次预约").setConfirmText("是的").setCancelText("我再想想")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(DateSelectionActivity.this, MainActivity.class);
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

    private void init() {
        textView_month = findViewById(R.id.tv_ds_month);
        textView_year = findViewById(R.id.tv_ds_year);
        calendarView_calendar = findViewById(R.id.cv_ds_calender);

        textView_year.setText(calendarView_calendar.getCurYear() + "年");
        textView_month.setText(calendarView_calendar.getCurMonth() + "月");

        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        hospitalId = sharedPreferences.getInt("hospitalId", 999999999);

        Intent intent = getIntent();
        symptom = intent.getStringExtra("detail_symptom");
        level = intent.getStringExtra("detail_level");
        remote = intent.getStringExtra("detail_remote");

        hasAppointmentTimes.clear();
        hasAppointmentTimes.add("");

    }

    private void getAppointmentList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(UrlUtil.getURL("AppointmentListByHospitalId?hospitalId=" + hospitalId))
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
                                dateList.add(jsonArray.getJSONObject(i).getString("date"));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initCalendar();
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

    private void initCalendar() {
        Map<String, Integer> map = new HashMap<>();
        for (String string : dateList) {
            if (map.containsKey(string)) {
                map.put(string, map.get(string).intValue() + 1);
            } else {
                map.put(string, new Integer(1));
            }
        }
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String date = iter.next();
            Log.d(null, "map: " + date + "-" + map.get(date));
            if (map.get(date) >= 16) {
                String[] dateStrings = date.split("-");
                Calendar calendar = new Calendar();
                calendar.setYear(Integer.parseInt(dateStrings[0]));
                calendar.setMonth(Integer.parseInt(dateStrings[1]));
                calendar.setDay(Integer.parseInt(dateStrings[2]));
                calendars.add(calendar);
                calendarView_calendar.setSchemeDate(calendars);
                calendarView_calendar.update();
            }
        }
    }
}
