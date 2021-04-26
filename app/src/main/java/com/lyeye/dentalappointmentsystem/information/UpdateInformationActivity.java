package com.lyeye.dentalappointmentsystem.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.impl.UserImpl;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateInformationActivity extends AppCompatActivity {

    private EditText editText_username, editText_phone, editText_email;
    private Button button_update;

    private SharedPreferences sharedPreferences;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);

        init();

        button_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = editText_username.getText().toString();
                String email = editText_email.getText().toString();
                String phone = editText_phone.getText().toString();
                Log.d(null, "name: " + name);

                Intent intent = getIntent();
                String oldName = intent.getStringExtra("name");
                String oldEmail = intent.getStringExtra("email");
                String oldPhone = intent.getStringExtra("phone");
                String gender = intent.getStringExtra("gender");
                String number = intent.getStringExtra("number");
                String password = intent.getStringExtra("password");
                String birthday = intent.getStringExtra("birthday");
                String createAt = intent.getStringExtra("createAt");
                String type = intent.getStringExtra("type");

                if (name.length() == 0 && phone.length() == 0 && email.length() == 0) {
                    ToastUtil.showMsg(UpdateInformationActivity.this, "请输入内容");
                } else {
                    if (name.length() != 0) {
                        update(name, gender, oldEmail, password, oldPhone, number, birthday, createAt, type);
                    }

                    if (phone.length() != 0) {
                        if (isMobile(phone)) {
                            update(oldName, gender, oldEmail, password, phone, number, birthday, createAt, type);
                        } else {
                            ToastUtil.showMsg(UpdateInformationActivity.this, "手机号格式不正确");
                        }

                    }

                    if (email.length() != 0) {
                        if (isEmail(email)) {
                            update(oldName, gender, email, password, oldPhone, number, birthday, createAt, type);
                        } else {
                            ToastUtil.showMsg(UpdateInformationActivity.this, "邮箱格式不正确");
                        }
                    }

                    startActivity(new Intent(UpdateInformationActivity.this, MyInformationActivity.class));
                }
            }
        });
    }

    private void init() {
        editText_username = findViewById(R.id.et_ui_name);
        editText_phone = findViewById(R.id.et_ui_phone);
        editText_email = findViewById(R.id.et_ui_email);
        button_update = findViewById(R.id.btn_ui_update);

        sharedPreferences = getSharedPreferences("JsonInfo", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 999999999);
        Log.d(null, "userId: " + userId);


    }

    private void update(String name, String gender, String email, String password, String phone, String number, String birthday, String createAt, String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = "{\n" +
                            "\t\"id\":\"" + userId + "\",\n" +
                            "\t\"name\":\"" + name + "\",\n" +
                            "\t\"gender\":\"" + gender + "\",\n" +
                            "\t\"email\":\"" + email + "\",\n" +
                            "\t\"password\":\"" + password + "\",\n" +
                            "\t\"phone\":\"" + phone + "\",\n" +
                            "\t\"number\":\"" + number + "\",\n" +
                            "\t\"birthday\":\"" + birthday + "\",\n" +
                            "\t\"createAt\":\"" + createAt + "\",\n" +
                            "\t\"type\":\"" + type + "\"\n" +
                            "}";
                    Log.d(null, "json: " + json);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(UrlUtil.getURL("updateMyInfo?userId=" + userId))
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    okHttpClient.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    验证邮箱格式
    * */
    private boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (strEmail.matches(strPattern)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    验证手机格式
    * */
    private boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）、177
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        //"[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String num = "[1][3578]\\d{9}";
        if (number.matches(num)) {
            return true;
        } else {
            return false;
        }
    }
}