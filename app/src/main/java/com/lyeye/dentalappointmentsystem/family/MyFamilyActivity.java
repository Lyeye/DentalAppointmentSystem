package com.lyeye.dentalappointmentsystem.family;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyFamilyActivity extends AppCompatActivity {

    private EditText editText_diagnosisNumber;
    private TextView textView_familyBirthday;
    private Button button_familyLogin;

    private Date select_birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_family);

        editText_diagnosisNumber = findViewById(R.id.et_mf_diagnosisNumber);
        textView_familyBirthday = findViewById(R.id.tv_mf_familyBirthday);
        button_familyLogin = findViewById(R.id.btn_mf_familyLogin);


        final Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(1950, 0, 1);
        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(2020, 0, 1);

        textView_familyBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView timePickerView_birthday = new TimePickerBuilder(MyFamilyActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("YYYY年MM月dd日");
                        String format_birthday = format.format(date);
                        textView_familyBirthday.setText(format_birthday);
                        select_birthday = date;
                    }
                }).setTitleText("选择您的生日").setCancelText("取消").setSubmitText("确定").isCyclic(true).setRangDate(startCalendar, endCalendar).build();
                timePickerView_birthday.show();
            }
        });

        button_familyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editText_diagnosisNumber.getText().toString();
                String birthday = textView_familyBirthday.getText().toString();
                if (number.length() == 0 || birthday == "选择亲属生日") {
                    ToastUtil.showMsg(MyFamilyActivity.this, "请输入亲属诊察号以及选择亲属生日!");
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient okHttpClient = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(UrlUtil.getURL("familyLogin?number=" + number + "&birthday=" + birthday))
                                        .get()
                                        .build();
                                okHttpClient.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtil.showMsg(MyFamilyActivity.this, "网络请求失败");
                                            }
                                        });
                                        Log.d(null, "error: " + e);
                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        String responseData = response.body().string();
                                        Log.d(null, "responseData: " + responseData);
                                        String[] strings = responseData.split("==>");
                                        if (strings[0].equals("SUCCESS")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(MyFamilyActivity.this, FamilyAppointmentInfoActivity.class);
                                                    intent.putExtra("userId", strings[1]);
                                                    intent.putExtra("userName", strings[2]);
                                                    intent.putExtra("userId", strings[1]);
                                                    startActivity(intent);
                                                    ToastUtil.showMsg(MyFamilyActivity.this, "登录成功");
                                                }
                                            });
                                        } else if (strings[0].equals("BirthdayError")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtil.showMsg(MyFamilyActivity.this, "生日错误");
                                                }
                                            });
                                        } else if (strings[0].equals("NoData")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtil.showMsg(MyFamilyActivity.this, "无该用户信息");
                                                }
                                            });
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d(null, "error: " + e);
                            }
                        }
                    }).start();
                }
            }
        });



        /*
        调整图标大小
        */
        Drawable drawable_add = getResources().getDrawable(R.mipmap.ic_add);
        drawable_add.setBounds(0, 0, 60, 60);
        button_familyLogin.setCompoundDrawables(drawable_add, null, null, null);
    }
}