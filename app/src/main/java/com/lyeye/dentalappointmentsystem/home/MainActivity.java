package com.lyeye.dentalappointmentsystem.home;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.AppointmentInfoRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.appointment.AppointmentActivity;
import com.lyeye.dentalappointmentsystem.camera.CameraActivity;
import com.lyeye.dentalappointmentsystem.family.MyFamilyActivity;

import com.lyeye.dentalappointmentsystem.information.MyInformationActivity;
import com.lyeye.dentalappointmentsystem.notice.NoticeActivity;
import com.lyeye.dentalappointmentsystem.pay.PayActivity;
import com.lyeye.dentalappointmentsystem.register.RegisterActivity;
import com.lyeye.dentalappointmentsystem.remote.JoinRoomActivity;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;
import com.lyeye.dentalappointmentsystem.welcome.WelcomeActivity;

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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int IMAGE_REQUEST_CODE = 1;
    private Button button_register, button_appointment, button_myfamily, button_notice, button_camera, button_remote;
    private TextView textView_username, textView_gender, textView_hospital, textView_diagnosisNumber;
    private RecyclerView recyclerView_appointmentInfo;
    private ImageView imageView_userPhoto, imageView_userInfo, imageView_pay;
    private String paths, userName;
    private SharedPreferences sharedPreferences;
    private Editor editor;
    private int userId;
    private boolean isLogin;
    private boolean isExit;
    private String[] hospitalList = new String[3];
    private String[] permissions;
    private List<JSONObject> appointmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initRecyclerView();
        setOnClick();
        icon();
    }

    private void init() {

        permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        button_register = findViewById(R.id.btn_main_register);
        button_appointment = findViewById(R.id.btn_main_appointment);
        button_myfamily = findViewById(R.id.btn_main_myfamily);
        button_notice = findViewById(R.id.btn_main_notice);
        button_camera = findViewById(R.id.btn_main_camera);
        button_remote = findViewById(R.id.btn_main_remote);
        textView_username = findViewById(R.id.tv_main_username);
        textView_gender = findViewById(R.id.tv_main_gender);
        textView_hospital = findViewById(R.id.tv_main_hospital);
        textView_diagnosisNumber = findViewById(R.id.tv_main_diagnosisNumber);
        recyclerView_appointmentInfo = findViewById(R.id.rv_main_appointmentInfo);
        imageView_userPhoto = findViewById(R.id.iv_main_userPhoto);
        imageView_userInfo = findViewById(R.id.iv_main_userInfo);
        imageView_pay = findViewById(R.id.iv_main_pay);

        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getInt("userId", 999999999);
        userName = sharedPreferences.getString("userName", "");

        isLogin = userId == 999999999 || sharedPreferences.getString("hospitalName", "").equals("") ? false : true;
        textView_hospital.setText(sharedPreferences.getString("hospitalName", "").equals("") ? "请选择医院" : sharedPreferences.getString("hospitalName", ""));
        Glide.with(MainActivity.this).load("https://pic3.zhimg.com/v2-d479b9589ae3b2a873936bd8f189bd85_r.jpg?source=1940ef5c").into(imageView_userPhoto);
        setUserInfo();
        findAllHospitals();
    }

    private void setOnClick() {
        textView_username.setOnClickListener(this);
        textView_hospital.setOnClickListener(this);
        button_register.setOnClickListener(this);
        button_appointment.setOnClickListener(this);
        button_myfamily.setOnClickListener(this);
        button_notice.setOnClickListener(this);
        button_camera.setOnClickListener(this);
        button_remote.setOnClickListener(this);
        imageView_userPhoto.setOnClickListener(this);
        imageView_userInfo.setOnClickListener(this);
        imageView_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_main_userInfo:
                startActivity(new Intent(MainActivity.this, MyInformationActivity.class));
                break;
            case R.id.iv_main_userPhoto:
                if (Build.VERSION.SDK_INT >= 23) {
                    int check1 = ContextCompat.checkSelfPermission(MainActivity.this, permissions[0]);
                    /*权限是否已经授权 GRANTED---授权 DENIED---拒绝*/
                    if (check1 == PackageManager.PERMISSION_GRANTED) {
                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, IMAGE_REQUEST_CODE);
                        break;
                    } else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        break;
                    }
                } else {
                    intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE_REQUEST_CODE);
                    break;
                }
            case R.id.iv_main_pay:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, PayActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先选择医院");
                }
                break;
            case R.id.tv_main_username:
                intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_main_hospital:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("选择医院").setIcon(R.drawable.ic_hospital)
                        .setSingleChoiceItems(hospitalList, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                textView_hospital.setText(hospitalList[which]);
                                editor.putString("hospitalName", hospitalList[which]);
                                editor.putInt("hospitalId", which + 1).apply();
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                                ToastUtil.showMsg(MainActivity.this, "您选择了" + hospitalList[which]);
                            }
                        }).show();
                break;
            case R.id.btn_main_register:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先选择医院");
                }
                break;
            case R.id.btn_main_appointment:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, AppointmentActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先选择医院");
                }
                break;
            case R.id.btn_main_myfamily:
                intent = new Intent(MainActivity.this, MyFamilyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_notice:
                intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_camera:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先选择医院");
                }
                break;
            case R.id.btn_main_remote:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, JoinRoomActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先选择医院");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                /*从系统表中查询指定Uri对应的照片*/
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                /*获取照片路径*/
                paths = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(paths);
                Glide.with(this).load(bitmap).into(imageView_userPhoto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setUserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(UrlUtil.getURL("myInfo?userId=" + userId))
                            .get()
                            .build();
                    new OkHttpClient().newCall(request).enqueue(new Callback() {
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
                                            textView_username.setText(jsonObject.getString("name"));
                                            textView_gender.setText(jsonObject.getString("gender"));
                                            textView_diagnosisNumber.setText(jsonObject.getString("number"));
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

    private void findAllHospitals() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(UrlUtil.getURL("getAllHospital"))
                        .get()
                        .build();
                new OkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        Log.d(null, "获取医院失败: " + e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d(null, "responseBody: " + responseBody);
                        try {
                            JSONArray jsonArray = new JSONArray(responseBody);
                            Log.d(null, "hospitalList: " + jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String name = jsonArray.getJSONObject(i).getString("name");
                                hospitalList[i] = name;
                                Log.d(null, "id: " + i);
                                Log.d(null, "name: " + name);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    private void initRecyclerView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(UrlUtil.getURL("AppointmentListByUserId?userId=" + userId))
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
                                appointmentList.add(jsonArray.getJSONObject(i));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                                    recyclerView_appointmentInfo.setLayoutManager(linearLayoutManager);
                                    recyclerView_appointmentInfo.setAdapter(new AppointmentInfoRecyclerViewAdapter(MainActivity.this, appointmentList));
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

    /*
        调整图标大小
    */
    private void icon() {
        Drawable drawable_phone = getResources().getDrawable(R.mipmap.ic_phone);
        drawable_phone.setBounds(0, 0, 100, 100);
        button_register.setCompoundDrawables(null, drawable_phone, null, null);

        Drawable drawable_appointment = getResources().getDrawable(R.mipmap.ic_calendar);
        drawable_appointment.setBounds(0, 0, 100, 100);
        button_appointment.setCompoundDrawables(null, drawable_appointment, null, null);

        Drawable drawable_family = getResources().getDrawable(R.mipmap.ic_group);
        drawable_family.setBounds(0, 0, 100, 100);
        button_myfamily.setCompoundDrawables(null, drawable_family, null, null);

        Drawable drawable_notice = getResources().getDrawable(R.mipmap.ic_notice);
        drawable_notice.setBounds(0, 0, 100, 100);
        button_notice.setCompoundDrawables(null, drawable_notice, null, null);

        Drawable drawable_camera = getResources().getDrawable(R.mipmap.ic_camera);
        drawable_camera.setBounds(0, 0, 100, 100);
        button_camera.setCompoundDrawables(null, drawable_camera, null, null);

        Drawable drawable_remote = getResources().getDrawable(R.mipmap.ic_remote);
        drawable_remote.setBounds(0, 0, 100, 100);
        button_remote.setCompoundDrawables(null, drawable_remote, null, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.showMsg(MainActivity.this, "再按一下退出程序");
            new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    isExit = false;
                }
            }.sendEmptyMessageDelayed(0, 2000);
        } else {
            finishAffinity();
            ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(this.getPackageName());
            System.exit(0);
        }
    }
}
