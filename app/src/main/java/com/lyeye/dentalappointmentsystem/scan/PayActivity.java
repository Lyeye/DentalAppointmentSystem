package com.lyeye.dentalappointmentsystem.scan;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;
import com.lyeye.dentalappointmentsystem.welcome.LoginFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PayActivity extends AppCompatActivity {

    public static final int CAMERA_REQ_CODE = 111;
    public static final int DECODE = 1;
    public static final int GENERATE = 2;
    private static final int REQUEST_CODE_SCAN_ONE = 0X01;

    private SharedPreferences sharedPreferences;
    private String userId, userName, hospitalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        init();

    }

    private void init() {
        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        userId = String.valueOf(sharedPreferences.getInt("userId", 999999999));
        userName = sharedPreferences.getString("userName", "");
        hospitalName = sharedPreferences.getString("hospitalName", "");
    }

    public void Pay_Scan(View view) {
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
            if (isNumeric(obj.showResult)) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(PayActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                sweetAlertDialog
                        .setTitleText("确认支付？")
                        .setCustomImage(R.mipmap.ic_paypay)
                        .setContentText("您需要支付" + obj.showResult + "大洋")
                        .setConfirmText("确认")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                pay(obj.showResult);
                            }
                        })
                        .setCancelText("取消")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();
            } else {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(PayActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setContentText("请扫描正确的收款码").setCancelText("重新扫描").setConfirmText("返回")
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

    private void pay(String amount) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    String json = "{\n" +
                            "\t\"userId\":\"" + userId + "\",\n" +
                            "\t\"payer\":\"" + userName + "\",\n" +
                            "\t\"payee\":\"" + hospitalName + "\",\n" +
                            "\t\"amount\":\"" + amount + "\",\n" +
                            "\t\"date\":\"" + date + "\",\n" +
                            "\t\"time\":\"" + time + "\"\n" +
                            "}";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(UrlUtil.getURL("pay?userId=" + userId + "&amount=" + amount))
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    okHttpClient.newCall(request).execute();
                    Log.d(null, "付款成功");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showMsg(PayActivity.this, "正在付款");
                            Intent intent = new Intent(PayActivity.this, PayResultActivity.class);
                            startActivity(intent);
                            ToastUtil.showMsg(PayActivity.this, "付款成功");
                        }
                    });

                } catch (Exception e) {
                    Log.d(null, "付款失败：" + e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showMsg(PayActivity.this, "付款失败");
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(PayActivity.this, MainActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
