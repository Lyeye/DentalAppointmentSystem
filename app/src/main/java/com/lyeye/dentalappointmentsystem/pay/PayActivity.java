package com.lyeye.dentalappointmentsystem.pay;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PayActivity extends AppCompatActivity {

    public static final int CAMERA_REQ_CODE = 111;
    public static final int DECODE = 1;
    public static final int GENERATE = 2;
    private static final int REQUEST_CODE_SCAN_ONE = 0X01;

    private ImageView imageView_QRCode;
    private TextView textView_payee;

    private SharedPreferences sharedPreferences;
    private String userId, userName, hospitalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pay);
        init();

        imageView_QRCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) imageView_QRCode.getDrawable()).getBitmap();
                Result result = parsePic(bitmap);
                if (null == result) {
                    ToastUtil.showMsg(getApplicationContext(), "解析结果：null");
                } else {
                    uploadData(result.getText());
                }
                return false;
            }
        });

    }

    private void init() {
        textView_payee = findViewById(R.id.tv_pay_payee);
        imageView_QRCode = findViewById(R.id.iv_pay_QRCode);
        Glide.with(getApplicationContext()).load(UrlUtil.getURL("image/" + "付款码.png")).into(imageView_QRCode);
        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        userId = String.valueOf(sharedPreferences.getInt("userId", 999999999));
        userName = sharedPreferences.getString("userName", "");
        hospitalName = sharedPreferences.getString("hospitalName", "");
        textView_payee.setText(hospitalName);
    }

    public Result parsePic(Bitmap bitmap) {
        // 解析转换类型UTF-8
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        // 新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        int lWidth = bitmap.getWidth();
        int lHeight = bitmap.getHeight();
        int[] lPixels = new int[lWidth * lHeight];
        bitmap.getPixels(lPixels, 0, lWidth, 0, 0, lWidth, lHeight);
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(lWidth,
                lHeight, lPixels);
        // 将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                rgbLuminanceSource));
        // 初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        // 开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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

    private void uploadData(String result) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(PayActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog
                .setTitleText("确认支付？")
                .setCustomImage(R.mipmap.ic_paypay)
                .setContentText("您需要支付" + result + "元")
                .setConfirmText("确认")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pay(result);
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
}
