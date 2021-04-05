package com.lyeye.dentalappointmentsystem.appointment;

import android.content.Intent;
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
import com.lyeye.dentalappointmentsystem.mapper.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.util.TimeSelectDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class DateSelectionActivity extends AppCompatActivity {

    private TextView textView_month, textView_year;
    private CalendarView calendarView_calendar;

    private String symptom;
    private List<String> hasAppointmentTimes = new ArrayList<>();
    private List<Calendar> calendars = new ArrayList<>();
    private AppointmentInfoImpl appointmentInfoImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);

        init();

        calendarView_calendar.setOnDateLongClickListener(new CalendarView.OnDateLongClickListener() {
            @Override
            public void onDateLongClick(Calendar calendar) {
                String dateSelected = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay();
                List<AppointmentInfo> list = appointmentInfoImpl.findAppointmentInfoByAmiDate(dateSelected);
                if (list.isEmpty()) {
                    TimeSelectDialog timeSelectDialog = new TimeSelectDialog(DateSelectionActivity.this, R.style.Dialog, dateSelected, symptom, hasAppointmentTimes);
                    timeSelectDialog.show();
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        hasAppointmentTimes.add(list.get(i).getAmiTime());
                        TimeSelectDialog timeSelectDialog = new TimeSelectDialog(DateSelectionActivity.this, R.style.Dialog, dateSelected, symptom, hasAppointmentTimes);
                        timeSelectDialog.show();
                    }
                }
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

        Intent intent = getIntent();
        symptom = intent.getStringExtra("symptom");

        hasAppointmentTimes.clear();
        hasAppointmentTimes.add("");

        appointmentInfoImpl = new AppointmentInfoImpl(DateSelectionActivity.this);

        List<AppointmentInfo> appointmentInfoList = appointmentInfoImpl.findAll();
        Log.d(null, "listSize: " + appointmentInfoList.size());
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 0; i < appointmentInfoList.size(); i++) {
            dateList.add(appointmentInfoList.get(i).getAmiDate());
        }
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
            if (map.get(date) >= 8) {
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
