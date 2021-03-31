package com.lyeye.dentalappointmentsystem.util;

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
import com.lyeye.dentalappointmentsystem.appointment.DateSelectionActivity;
import com.lyeye.dentalappointmentsystem.entity.BmobAppointmentInfo;
import com.lyeye.dentalappointmentsystem.home.MainActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class TimeSelectDialog extends Dialog {

    private TextView textView_cancel;

    private ArrayList<Button> buttons = new ArrayList<>();

    private Context context;
    private String dateSelected;
    private String symptom;
    private String userName;
    private List<String> hasAppointmentTimes = new ArrayList<>();
    private SharedPreferences sharedPreferences;

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
                                    BmobAppointmentInfo bmobAppointmentInfo = new BmobAppointmentInfo();
                                    bmobAppointmentInfo.setUserName(userName);
                                    bmobAppointmentInfo.setAmiDate(dateSelected);
                                    bmobAppointmentInfo.setAmiTime(button.getText().toString());
                                    bmobAppointmentInfo.setAmiSymptoms(symptom);
                                    bmobAppointmentInfo.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null) {
                                                ToastUtil.showMsg(context, "添加成功,id:" + bmobAppointmentInfo.getObjectId());
                                                Intent intent = new Intent(context, MainActivity.class);
                                                context.startActivity(intent);
                                            } else {
                                                ToastUtil.showMsg(context, "添加失败,异常:" + e.getMessage());
                                                sweetAlertDialog.cancel();
                                            }
                                        }
                                    });
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
                Intent intent = new Intent(context, DateSelectionActivity.class);
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

        sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("username", "");

        Log.d(null, "hasAppointments: " + hasAppointmentTimes.size());

        for (int i = 0; i < hasAppointmentTimes.size(); i++) {
            if (hasAppointmentTimes.get(i).equals("8:00-9:00")) {
                button1.setText("已被预约");
                button1.setEnabled(false);
            } else if (hasAppointmentTimes.get(i).equals("9:00-10:00")) {
                button2.setText("已被预约");
                button2.setEnabled(false);
            } else if (hasAppointmentTimes.get(i).equals("10:00-11:00")) {
                button3.setText("已被预约");
                button3.setEnabled(false);
            } else if (hasAppointmentTimes.get(i).equals("11:00-12:00")) {
                button4.setText("已被预约");
                button4.setEnabled(false);
            } else if (hasAppointmentTimes.get(i).equals("14:30-15:30")) {
                button5.setText("已被预约");
                button5.setEnabled(false);
            } else if (hasAppointmentTimes.get(i).equals("15:30-16:30")) {
                button6.setText("已被预约");
                button6.setEnabled(false);
            } else if (hasAppointmentTimes.get(i).equals("16:30-17:30")) {
                button7.setText("已被预约");
                button7.setEnabled(false);
            } else if (hasAppointmentTimes.get(i).equals("17:30-18:30")) {
                button8.setText("已被预约");
                button8.setEnabled(false);
            }
        }


    }
}
