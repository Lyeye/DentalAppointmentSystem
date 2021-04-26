package com.lyeye.dentalappointmentsystem.information;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MyInformationActivity extends AppCompatActivity {

    private TextView textView_username, textView_gender, textView_phone, textView_email, textView_dNumber, textView_birthday;
    private Button button_update;

    private SharedPreferences sharedPreferences;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        init();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(UrlUtil.getURL("myInfo?userId=" + userId))
                            .get()
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d(null, "onFailure: " + e);
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String responseBody = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(responseBody);
                                Log.d(null, "jsonObject: " + jsonObject.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            textView_username.setText("姓名：" + jsonObject.getString("name"));
                                            textView_gender.setText("性别：" + jsonObject.getString("gender"));
                                            textView_birthday.setText("生日：" + jsonObject.getString("birthday"));
                                            textView_phone.setText("手机：" + jsonObject.getString("phone"));
                                            textView_email.setText("邮箱：" + jsonObject.getString("email"));
                                            textView_dNumber.setText("诊察号：" + jsonObject.getString("number"));

                                            button_update.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    try {
                                                        Intent intent = new Intent(MyInformationActivity.this, UpdateInformationActivity.class);
                                                        intent.putExtra("name", jsonObject.getString("name"));
                                                        intent.putExtra("gender", jsonObject.getString("gender"));
                                                        intent.putExtra("email", jsonObject.getString("email"));
                                                        intent.putExtra("password", jsonObject.getString("password"));
                                                        intent.putExtra("phone", jsonObject.getString("phone"));
                                                        intent.putExtra("number", jsonObject.getString("number"));
                                                        intent.putExtra("birthday", jsonObject.getString("birthday"));
                                                        intent.putExtra("createAt", jsonObject.getString("createAt"));
                                                        intent.putExtra("type", jsonObject.getString("type"));
                                                        startActivity(intent);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            });

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void init() {
        textView_username = findViewById(R.id.tv_mi_name);
        textView_gender = findViewById(R.id.tv_mi_gender);
        textView_birthday = findViewById(R.id.tv_mi_birthday);
        textView_email = findViewById(R.id.tv_mi_email);
        textView_phone = findViewById(R.id.tv_mi_phone);
        textView_dNumber = findViewById(R.id.tv_mi_diagnosisNumber);
        button_update = findViewById(R.id.btn_mi_update);

        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 999999999);
        textView_username.setText("姓名：" + sharedPreferences.getString("username", ""));
        textView_gender.setText("性别：" + sharedPreferences.getString("gender", ""));
        textView_birthday.setText("生日：" + sharedPreferences.getString("birthday", ""));
        textView_phone.setText("手机：" + sharedPreferences.getString("userPhone", ""));
        textView_email.setText("邮箱：" + sharedPreferences.getString("userEmail", ""));
        textView_dNumber.setText("诊察号：" + sharedPreferences.getString("diagnosisNumber", ""));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyInformationActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("是否退回主页面").setConfirmText("是的").setCancelText("我再想想")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(MyInformationActivity.this, MainActivity.class);
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