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
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.entity.Hospital;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.family.MyFamilyActivity;
import com.lyeye.dentalappointmentsystem.greendao.DaoManager;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.impl.HospitalImpl;
import com.lyeye.dentalappointmentsystem.impl.UserImpl;
import com.lyeye.dentalappointmentsystem.information.MyInformationActivity;
import com.lyeye.dentalappointmentsystem.notice.NoticeActivity;
import com.lyeye.dentalappointmentsystem.scan.ScanActivity;
import com.lyeye.dentalappointmentsystem.remote.JoinRoomActivity;
import com.lyeye.dentalappointmentsystem.util.AppoinmentInfoMessageEvent;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.welcome.WelcomeActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int IMAGE_REQUEST_CODE = 1;
    private Button button_register, button_appointment, button_myfamily, button_notice, button_camera, button_remote;
    private TextView textView_username, textView_gender, textView_affiliatedHospital, textView_diagnosisNumber;
    private RecyclerView recyclerView_appointmentInfo;
    private ImageView imageView_userPhoto, imageView_userInfo;
    private String paths;
    private SharedPreferences sharedPreferences;
    private Editor editor;
    private User user;
    private String userName;
    private long userId;
    private AppointmentInfoImpl appointmentInfoImpl;
    private HospitalImpl hospitalImpl;
    private UserImpl userImpl;
    private List<Hospital> hospitals;
    private List<AppointmentInfo> appointmentInfos;
    private boolean isLogin;
    private boolean isExit;
    private String[] permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setOnClick();
        icon();

        /*
        注册事件
        */
        EventBus.getDefault().register(this);

    }

    private void init() {

        permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        isExit = false;

        button_register = findViewById(R.id.btn_main_register);
        button_appointment = findViewById(R.id.btn_main_appointment);
        button_myfamily = findViewById(R.id.btn_main_myfamily);
        button_notice = findViewById(R.id.btn_main_notice);
        button_camera = findViewById(R.id.btn_main_camera);
        button_remote = findViewById(R.id.btn_main_remote);
        textView_username = findViewById(R.id.tv_main_username);
        textView_gender = findViewById(R.id.tv_main_gender);
        textView_affiliatedHospital = findViewById(R.id.tv_main_affiliatedHospital);
        textView_diagnosisNumber = findViewById(R.id.tv_main_diagnosisNumber);
        recyclerView_appointmentInfo = findViewById(R.id.rv_main_appointmentInfo);
        imageView_userPhoto = findViewById(R.id.iv_main_userPhoto);
        imageView_userInfo = findViewById(R.id.iv_main_userInfo);

        userImpl = new UserImpl(MainActivity.this);
        appointmentInfoImpl = new AppointmentInfoImpl(MainActivity.this);
        hospitalImpl = new HospitalImpl(MainActivity.this);
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userName = sharedPreferences.getString("username", "登录/注册");
        userId = sharedPreferences.getLong("userId", 999999999);
        textView_username.setText(userName);
        textView_gender.setText(sharedPreferences.getString("gender", ""));
        textView_affiliatedHospital.setText(sharedPreferences.getString("affiliatedHospital", "仁康医院"));
        textView_diagnosisNumber.setText(sharedPreferences.getString("diagnosisNumber", ""));

        if (sharedPreferences.getString("username", "登录/注册") != "登录/注册") {
            isLogin = true;
        }

        hospitals = hospitalImpl.findAll();
        user = userImpl.findUserById(userId);

        List<AppointmentInfo> appointmentInfosByUserId = appointmentInfoImpl.findAppointmentInfoByUserId(userId);
        appointmentInfos = appointmentInfosByUserId;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView_appointmentInfo.setLayoutManager(linearLayoutManager);
        recyclerView_appointmentInfo.setAdapter(new AppointmentInfoRecyclerViewAdapter(MainActivity.this, appointmentInfos));

        if (user.getHeadPortrait() == null) {
            Glide.with(MainActivity.this).load("https://pic3.zhimg.com/v2-d479b9589ae3b2a873936bd8f189bd85_r.jpg?source=1940ef5c").into(imageView_userPhoto);
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(user.getHeadPortrait());
            Glide.with(this).load(bitmap).into(imageView_userPhoto);
        }
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
            DaoManager.getInstance().closeConnection();
            finishAffinity();
            ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(this.getPackageName());
            System.exit(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAppointmentInfoMessageEvent(AppoinmentInfoMessageEvent messageEvent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setOnClick() {
        textView_username.setOnClickListener(this);
        textView_affiliatedHospital.setOnClickListener(this);
        button_register.setOnClickListener(this);
        button_appointment.setOnClickListener(this);
        button_myfamily.setOnClickListener(this);
        button_notice.setOnClickListener(this);
        button_camera.setOnClickListener(this);
        button_remote.setOnClickListener(this);
        imageView_userPhoto.setOnClickListener(this);
        imageView_userInfo.setOnClickListener(this);
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
            case R.id.tv_main_username:
                intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_main_affiliatedHospital:
                String[] hospitalList = new String[5];
                for (int i = 0; i < 5; i++) {
                    hospitalList[i] = hospitals.get(i).getHospitalName();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("选择医院").setIcon(R.drawable.ic_hospital)
                        .setSingleChoiceItems(hospitalList, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                user.setAffiliatedHospital(hospitalList[which]);
                                DaoManager.getInstance().getDaoSession()
                                        .getUserDao().update(user);
                                ToastUtil.showMsg(MainActivity.this, "您选择了" + hospitalList[which]);
                                textView_affiliatedHospital.setText(hospitalList[which]);
                                editor.putString("affiliatedHospital", hospitalList[which]).apply();
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                            }
                        }).show();
                break;
            case R.id.btn_main_register:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, ScanActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先登录");
                }
                break;
            case R.id.btn_main_appointment:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, AppointmentActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先登录");
                }
                break;
            case R.id.btn_main_myfamily:
                intent = new Intent(MainActivity.this, MyFamilyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_notice:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, NoticeActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先登录");
                }
                break;
            case R.id.btn_main_camera:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先登录");
                }
                break;
            case R.id.btn_main_remote:
                if (isLogin) {
                    intent = new Intent(MainActivity.this, JoinRoomActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showMsg(this, "请先登录");
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
                user.setHeadPortrait(paths);
                DaoManager.getInstance().getDaoSession()
                        .getUserDao().update(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
}
