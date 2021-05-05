package com.lyeye.dentalappointmentsystem.camera;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CameraActivity extends AppCompatActivity {

    private static int REQUEST_CAMERA = 1;
    private static int IMAGE_REQUEST_CODE = 2;

    private Button button_takePhotoFromAssets, button_takePhotoWithCamera;
    private TextView textView_upload;
    private ImageView imageView_photo;

    private SharedPreferences sharedPreferences;
    private File file;
    private String path, currentPath, userId, userName, hospitalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        init();

        currentPath = null;

        icon();

        button_takePhotoWithCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                if (Build.VERSION.SDK_INT >= 23) {
                    int check1 = ContextCompat.checkSelfPermission(CameraActivity.this, permissions[0]);
                    int check2 = ContextCompat.checkSelfPermission(CameraActivity.this, permissions[1]);
                    /*权限是否已经授权 GRANTED---授权 DENIED---拒绝*/
                    if (check2 == PackageManager.PERMISSION_GRANTED) {
                        if (check1 == PackageManager.PERMISSION_GRANTED) {
                            useCamera();
                        } else {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                    }
                } else {
                    useCamera();
                }
            }
        });

        button_takePhotoFromAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*在这里跳转到手机系统相册里面*/
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });

        textView_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPath != null) {
                    uploadImg(new File(currentPath));
                } else {
                    ToastUtil.showMsg(CameraActivity.this, "请先选择照片！！");
                }
            }
        });
    }

    private void init() {
        button_takePhotoWithCamera = findViewById(R.id.btn_camera_takephotoswithcamera);
        button_takePhotoFromAssets = findViewById(R.id.btn_camera_takePhotosFromAssets);
        imageView_photo = findViewById(R.id.iv_camera_takePhotos);
        textView_upload = findViewById(R.id.tv_camera_upload);

        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        userId = String.valueOf(sharedPreferences.getInt("userId", 999999999));
        userName = sharedPreferences.getString("userName", "");
        hospitalName = sharedPreferences.getString("hospitalName", "");
    }

    private void uploadImg(File uploadFile) {
        textView_upload.setText("正在上传...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MultipartBody.Builder mb = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    MediaType contentType;
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), uploadFile);
                    mb.addFormDataPart("file", uploadFile.getName(), fileBody);
                    Request request = new Request.Builder()
                            .url(UrlUtil.getURL("uploadImg?userId=" + userId + "&userName=" + userName + "&hospitalName=" + hospitalName))
                            .post(mb.build())
                            .build();
                    new OkHttpClient()
                            .newBuilder()
                            .readTimeout(10000, TimeUnit.MILLISECONDS)
                            .build().newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("网络请求失败" + e);
                                    ToastUtil.showMsg(CameraActivity.this, "网络请求失败" + e);
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String string = response.body().string();
                            if (string.equals("SUCCESS")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("上传成功");
                                        textView_upload.setText("上传成功");
                                        ToastUtil.showMsg(CameraActivity.this, "上传成功，正在返回首页");
                                        startActivity(new Intent(CameraActivity.this, MainActivity.class));
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("上传失败");
                                        textView_upload.setText("上传失败");
                                        ToastUtil.showMsg(CameraActivity.this, "上传失败，正在返回首页");
                                        startActivity(new Intent(CameraActivity.this, MainActivity.class));
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {

                }
            }
        }).start();
    }

    private void useCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(CameraActivity.this.getExternalFilesDir(null).getAbsolutePath()
                + "/test/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        /*注意和xml中的一致*/
        Uri uri = FileProvider.getUriForFile(this, "com.lyeye.dentalappointmentsystem.provider", file);
        /*添加权限*/
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CAMERA);
        currentPath = file.getAbsolutePath();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            useCamera();
        } else {
            /*没有获取到权限，重新请求，或者关闭app*/
            ToastUtil.showMsg(this, "需要存储权限");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Glide.with(this).load(BitmapFactory.decodeFile(file.getAbsolutePath())).into(imageView_photo);
            /*在手机相册中显示刚拍摄的图片*/
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);
        }
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                /*从系统表中查询指定Uri对应的照片*/
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                /*获取照片路径*/
                path = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                Glide.with(this).load(bitmap).into(imageView_photo);
                currentPath = path;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
    调整图标大小
    */
    private void icon() {
        Drawable drawable_takePhotoWithCamera = getResources().getDrawable(R.mipmap.ic_takephotos);
        drawable_takePhotoWithCamera.setBounds(0, 0, 100, 100);
        button_takePhotoWithCamera.setCompoundDrawables(null, drawable_takePhotoWithCamera, null, null);

        Drawable drawable_takePhotoFromAlbum = getResources().getDrawable(R.mipmap.ic_album);
        drawable_takePhotoFromAlbum.setBounds(0, 0, 100, 100);
        button_takePhotoFromAssets.setCompoundDrawables(null, drawable_takePhotoFromAlbum, null, null);

    }
}
