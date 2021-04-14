package com.lyeye.dentalappointmentsystem.appointment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class TimeSelectDialog extends Dialog {

    private TextView textView_cancel;

    private ArrayList<Button> buttons = new ArrayList<>();

    private Context context;
    private String dateSelected;
    private String symptom;
    private String affiliatedHospital;
    private Date currentTime;
    private long userId;
    private List<String> hasAppointmentTimes = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private AppointmentInfoImpl appointmentInfoImpl;

    public TimeSelectDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public TimeSelectDialog(@NonNull Context context, int themeResId, String dateSelected, String symptom, List<String> hasAppointmentTimes) {
        super(context, themeResId);
        this.context = context;
        this.dateSelected = dateSelected;
        this.symptom = symptom;
        this.hasAppointmentTimes = hasAppointmentTimes;
    }

    protected TimeSelectDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        for (int i = 0; i < 8; i++) {
            Button button = buttons.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context);
                    sweetAlertDialog.setTitleText(dateSelected)
                            .setContentText("确定预约" + button.getText().toString() + "吗？")
                            .setConfirmText("预约").setCancelText("我再想想")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    AppointmentInfo appointmentInfo = new AppointmentInfo();
                                    appointmentInfo.setUserId(userId);
                                    appointmentInfo.setAmiDate(dateSelected);
                                    appointmentInfo.setAmiTime(button.getText().toString());
                                    appointmentInfo.setAmiSymptoms(symptom);
                                    appointmentInfo.setAffiliatedHospital(affiliatedHospital);
                                    appointmentInfo.setCreateAt(currentTime);
                                    appointmentInfoImpl.insertAppointmentInfo(appointmentInfo);
                                    ToastUtil.showMsg(context, "添加成功,id:" + appointmentInfo.getAmiId());
                                    Log.d(null, "appointmentInfo: " + appointmentInfo.toString());
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.cancel();
                                    ToastUtil.showMsg(context, "已取消");
                                }
                            })
                            .show();
                }
            });
        }

        textView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppointmentDetailActivity.class);
                context.startActivity(intent);
                dismiss();
            }
        });
    }


    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_time_select, null);
        setContentView(view);

        Button button1, button2, button3, button4, button5, button6, button7, button8;

        button1 = view.findViewById(R.id.btn_td_am1);
        button2 = view.findViewById(R.id.btn_td_am2);
        button3 = view.findViewById(R.id.btn_td_am3);
        button4 = view.findViewById(R.id.btn_td_am4);
        button5 = view.findViewById(R.id.btn_td_pm1);
        button6 = view.findViewById(R.id.btn_td_pm2);
        button7 = view.findViewById(R.id.btn_td_pm3);
        button8 = view.findViewById(R.id.btn_td_pm4);

        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);

        textView_cancel = view.findViewById(R.id.tv_td_cancel);

        appointmentInfoImpl = new AppointmentInfoImpl(context);
        sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", 999999999);
        affiliatedHospital = sharedPreferences.getString("affiliatedHospital", "");
        currentTime = new Date();

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
