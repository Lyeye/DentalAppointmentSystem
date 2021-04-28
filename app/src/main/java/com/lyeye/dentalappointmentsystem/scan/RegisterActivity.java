package com.lyeye.dentalappointmentsystem.scan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static final int CAMERA_REQ_CODE = 111;
    public static final int DECODE = 1;
    public static final int GENERATE = 2;
    private static final int REQUEST_CODE_SCAN_ONE = 0X01;

    private SharedPreferences sharedPreferences;
    private String userId, userName, hospitalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        userId = String.valueOf(sharedPreferences.getInt("userId", 999999999));
        userName = sharedPreferences.getString("userName", "");
    }

    public void Register_Scan(View view) {
        requestPermission(CAMERA_REQ_CODE, DECODE);
    }

    /**
     * 申请权限
     */
    private void requestPermission(int requestCode, int mode) {
        if (mode == DECODE) {
            decodePermission(requestCode);
        } else if (mode == GENERATE) {
            generatePermission(requestCode);
        }
    }

    /**
     * 申请权限
     */
    private void decodePermission(int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
    }

    private void generatePermission(int requestCode) {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                requestCode);
    }

    /**
     * Call back the permission application result. If the permission application is successful, the barcode scanning view will be displayed.
     * 回调权限应用程序结果。如果权限申请成功，将显示条形码扫描视图。
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //默认扫码格式
        if (requestCode == CAMERA_REQ_CODE) {
            ScanUtil.startScan(this, REQUEST_CODE_SCAN_ONE, new HmsScanAnalyzerOptions.Creator().create());
        }
    }

    /**
     * 扫码结果的回调
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            System.out.println("obj.showResult: " + obj.showResult);
            if (obj.showResult.contains("诊所")) {
                register(obj.showResult);
            } else {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setContentText("请扫描医院提供的登记码").setCancelText("重新扫描").setConfirmText("返回")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                requestPermission(CAMERA_REQ_CODE, DECODE);
                            }
                        }).show();
            }
        }
    }

    private void register(String result) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    String json = "{\n" +
                            "\t\"userId\":\"" + userId + "\",\n" +
                            "\t\"userName\":\"" + userName + "\",\n" +
                            "\t\"hospitalName\":\"" + result + "\",\n" +
                            "\t\"date\":\"" + date + "\",\n" +
                            "\t\"time\":\"" + time + "\"\n" +
                            "}";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(UrlUtil.getURL("checkIn?userId=" + userId))
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showMsg(RegisterActivity.this, "网络请求失败");
                                }
                            });
                            System.out.println("error: " + e);
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String responseData = response.body().string();
                            if (responseData.equals("SUCCESS")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                        sweetAlertDialog.setTitleText("签到成功")
                                                .setContentText("目前所在的诊所为" + result)
                                                .setConfirmText("返回首页")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }).setCancelText("重新扫描")
                                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismiss();
                                                    }
                                                })
                                                .show();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                                        startActivity(intent);
                                        ToastUtil.showMsg(RegisterActivity.this, "登记失败");
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {
                    System.out.println("异常：" + e);
                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
