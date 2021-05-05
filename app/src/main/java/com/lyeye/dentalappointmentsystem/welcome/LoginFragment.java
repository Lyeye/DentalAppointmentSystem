package com.lyeye.dentalappointmentsystem.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lyeye.dentalappointmentsystem.R;

import com.lyeye.dentalappointmentsystem.home.MainActivity;

import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginFragment extends Fragment {

    private Button button_signIn;
    private EditText editText_userEmail, editText_pwd;
    private TextView textView_signUp, textView_adminLogin;

    private RegisterFragment registerFragment;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sp_editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_signIn = view.findViewById(R.id.btn_fl_sign_in);
        editText_userEmail = view.findViewById(R.id.et_fl_useremail);
        editText_pwd = view.findViewById(R.id.et_fl_password);
        editText_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        textView_signUp = view.findViewById(R.id.tv_fl_sign_up);
        textView_adminLogin = view.findViewById(R.id.tv_fl_admin_login);

        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WelcomeActivity welcomeActivity = (WelcomeActivity) getActivity();
                String email = editText_userEmail.getText().toString();
                String password = editText_pwd.getText().toString();

                ToastUtil.showMsg(welcomeActivity, "正在登录...");
                if (email.length() == 0 || password.length() == 0) {
                    if (email.length() == 0) {
                        editText_userEmail.setHint("用户邮箱不能为空！");
                    }
                    if (password.length() == 0) {
                        editText_pwd.setHint("用户密码不能为空！");
                    }
                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient okHttpClient = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(UrlUtil.getURL("login?email=" + email + "&password=" + password))
                                        .get()
                                        .build();
                                okHttpClient.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                        welcomeActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtil.showMsg(welcomeActivity, "网络请求失败");
                                            }
                                        });
                                        Log.d(null, "error: " + e);
                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        String responseData = response.body().string();
                                        String[] strings = responseData.split("==>");
                                        Log.d(null, "responseData: " + responseData);
                                        if (strings[0].equals("SUCCESS")) {
                                            welcomeActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    sharedPreferences = welcomeActivity.getSharedPreferences("JsonInfo", Context.MODE_PRIVATE);
                                                    sp_editor = sharedPreferences.edit();
                                                    sp_editor.putInt("userId", Integer.valueOf(strings[1]));
                                                    sp_editor.putString("userName", strings[2]);
                                                    sp_editor.putString("userGender", strings[3]);
                                                    sp_editor.putString("hospitalName", "");
                                                    sp_editor.putInt("hospitalId", 999999999);
                                                    sp_editor.apply();
                                                    startActivity(new Intent(welcomeActivity, MainActivity.class));
                                                    ToastUtil.showMsg(welcomeActivity, "登录成功");
                                                }
                                            });
                                        } else if (strings[0].equals("PwdError")) {
                                            welcomeActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtil.showMsg(welcomeActivity, "密码错误");
                                                }
                                            });
                                        } else if (strings[0].equals("NoData")) {
                                            welcomeActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtil.showMsg(welcomeActivity, "请先注册");
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

        textView_adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMsg(getActivity(), "功能尚在开发中");
//                adminLoginFragment = new AdminLoginFragment();
//                WelcomeActivity welcomeActivity = (WelcomeActivity) getActivity();
//                Administrator administrator = new Administrator();
//                administrator.setAdministratorName("admin");
//                administrator.setAdministratorEmail("admin@lyeye.com");
//                administrator.setAdministratorPwd("123");
//                new AdministratorImpl(welcomeActivity).addAdmin(administrator);
//                FragmentManager fragmentManager = welcomeActivity.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction
//                        .addToBackStack(null)
//                        .setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out)
//                        .replace(R.id.fl_swl_container, adminLoginFragment)
//                        .commitAllowingStateLoss();
            }
        });

        /*
        给Sign In添加下划线
        */
        textView_signUp.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        textView_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFragment = new RegisterFragment();
                WelcomeActivity welcomeActivity = (WelcomeActivity) getActivity();
                FragmentManager fragmentManager = welcomeActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out)
                        .replace(R.id.fl_swl_container, registerFragment)
                        .commitAllowingStateLoss();

            }
        });

        /*
        调整图标大小
        */
        Drawable drawable_username = view.getResources().getDrawable(R.mipmap.ic_launcher_user);
        drawable_username.setBounds(0, 0, 100, 105);
        editText_userEmail.setCompoundDrawables(drawable_username, null, null, null);

        Drawable drawable_pwd = view.getResources().getDrawable(R.mipmap.ic_launcher_pwd);
        drawable_pwd.setBounds(0, 0, 100, 105);
        editText_pwd.setCompoundDrawables(drawable_pwd, null, null, null);

    }
}

