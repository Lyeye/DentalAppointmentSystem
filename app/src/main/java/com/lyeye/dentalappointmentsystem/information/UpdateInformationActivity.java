package com.lyeye.dentalappointmentsystem.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.impl.UserImpl;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;

public class UpdateInformationActivity extends AppCompatActivity {

    private EditText editText_username, editText_phone, editText_email;
    private Button button_update;

    private SharedPreferences sharedPreferences;
    private Editor editor;
    private UserImpl userImpl;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);

        init();

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_username.getText().toString().length() == 0
                        && editText_phone.getText().toString().length() == 0
                        && editText_email.getText().toString().length() == 0) {
                    ToastUtil.showMsg(UpdateInformationActivity.this, "请输入内容");
                } else {
                    if (editText_username.getText().toString().length() != 0) {
                        user.setUserName(editText_username.getText().toString());
                        userImpl.updateUser(user);
                        editor.putString("username", editText_username.getText().toString()).apply();
                    }

                    if (editText_phone.getText().toString().length() != 0) {
                        if (isMobile(editText_phone.getText().toString())) {
                            user.setUserPhoneNumber(editText_phone.getText().toString());
                            userImpl.updateUser(user);
                            editor.putString("userPhone", editText_phone.getText().toString()).apply();
                        } else {
                            ToastUtil.showMsg(UpdateInformationActivity.this, "手机号格式不正确");
                        }

                    }

                    if (editText_email.getText().toString().length() != 0) {

                        if (isEmail(editText_email.getText().toString())) {
                            if (userImpl.findUserByEmail(editText_email.getText().toString()) == null) {
                                user.setUserEmail(editText_email.getText().toString());
                                userImpl.updateUser(user);
                                editor.putString("userEmail", editText_email.getText().toString()).apply();
                            } else {
                                ToastUtil.showMsg(UpdateInformationActivity.this, "邮箱已存在！");
                            }
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

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userImpl = new UserImpl(this);
        user = userImpl.findUserById(sharedPreferences.getLong("userId", 999999999));
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