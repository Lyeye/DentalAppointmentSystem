package com.lyeye.dentalappointmentsystem.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.administration.UserListActivity;
import com.lyeye.dentalappointmentsystem.entity.Administrator;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.mapper.AdministratorImpl;
import com.lyeye.dentalappointmentsystem.mapper.UserImpl;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;


public class AdminLoginFragment extends Fragment {

    private Button button_signIn;
    private EditText editText_email, editText_pwd;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sp_editor;
    private AdministratorImpl administratorImpl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_signIn = view.findViewById(R.id.btn_fa_sign_in);
        editText_email = view.findViewById(R.id.et_fa_email);
        editText_pwd = view.findViewById(R.id.et_fa_password);


        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WelcomeActivity welcomeActivity = (WelcomeActivity) getActivity();
                administratorImpl = new AdministratorImpl(welcomeActivity);
                ToastUtil.showMsg(welcomeActivity, "正在登录...");
                if (editText_email.getText().toString().length() == 0 || editText_pwd.getText().toString().length() == 0) {
                    ToastUtil.showMsg(welcomeActivity, "用户邮箱或密码不能为空!");
                } else {
                    if (administratorImpl.findAdminByEmail(editText_email.getText().toString()) != null) {
                        Administrator adminByEmail = administratorImpl.findAdminByEmail(editText_email.getText().toString());
                        if (editText_pwd.getText().toString().equals(adminByEmail.getAdministratorPwd())) {
                            Log.d(null, "loginAdmin: " + adminByEmail.toString());
                            Intent intent = new Intent(welcomeActivity, UserListActivity.class);
                            sharedPreferences = welcomeActivity.getSharedPreferences("admin_info", Context.MODE_PRIVATE);
                            sp_editor = sharedPreferences.edit();
                            sp_editor.putString("adminEmail", adminByEmail.getAdministratorEmail());
                            sp_editor.putLong("adminId", adminByEmail.getAdministratorId());
                            sp_editor.putString("adminName", adminByEmail.getAdministratorName());
                            sp_editor.putString("password", adminByEmail.getAdministratorPwd());
                            sp_editor.apply();
                            startActivity(intent);
                            ToastUtil.showMsg(welcomeActivity, "登录成功");
                        } else {
                            ToastUtil.showMsg(welcomeActivity, "密码错误！");
                        }
                    } else {
                        ToastUtil.showMsg(welcomeActivity, "邮箱错误！");
                    }
                }
            }
        });

        /*
        调整图标大小
        */
        Drawable drawable_username = view.getResources().getDrawable(R.mipmap.ic_launcher_user);
        drawable_username.setBounds(0, 0, 100, 105);
        editText_email.setCompoundDrawables(drawable_username, null, null, null);

        Drawable drawable_pwd = view.getResources().getDrawable(R.mipmap.ic_launcher_pwd);
        drawable_pwd.setBounds(0, 0, 100, 105);
        editText_pwd.setCompoundDrawables(drawable_pwd, null, null, null);

    }
}

