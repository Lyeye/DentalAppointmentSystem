package com.lyeye.dentalappointmentsystem.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.Administrator;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.impl.AdministratorImpl;
import com.lyeye.dentalappointmentsystem.impl.UserImpl;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;

import java.text.SimpleDateFormat;


public class LoginFragment extends Fragment {

    private Button button_signIn;
    private EditText editText_userEmail, editText_pwd;
    private TextView textView_signUp, textView_adminLogin;

    private RegisterFragment registerFragment;
    private AdminLoginFragment adminLoginFragment;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sp_editor;
    private UserImpl userImpl;

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
        textView_signUp = view.findViewById(R.id.tv_fl_sign_up);
        textView_adminLogin = view.findViewById(R.id.tv_fl_admin_login);


        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WelcomeActivity welcomeActivity = (WelcomeActivity) getActivity();
                userImpl = new UserImpl(welcomeActivity);
                ToastUtil.showMsg(welcomeActivity, "正在登录...");
                if (editText_userEmail.getText().toString().length() == 0 || editText_pwd.getText().toString().length() == 0) {
                    ToastUtil.showMsg(welcomeActivity, "用户邮箱或密码不能为空!");
                } else {
                    if (userImpl.findUserByEmail(editText_userEmail.getText().toString()) != null) {
                        User userByEmail = userImpl.findUserByEmail(editText_userEmail.getText().toString());
                        if (editText_pwd.getText().toString().equals(userByEmail.getUserPwd())) {
                            Log.d(null, "loginUser: " + userByEmail.toString());
                            Intent intent = new Intent(welcomeActivity, MainActivity.class);
                            sharedPreferences = welcomeActivity.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                            sp_editor = sharedPreferences.edit();
                            sp_editor.putString("userEmail", userByEmail.getUserEmail());
                            sp_editor.putString("userPhone", userByEmail.getUserPhoneNumber());
                            sp_editor.putLong("userId", userByEmail.getUserId());
                            sp_editor.putString("username", userByEmail.getUserName());
                            sp_editor.putString("password", userByEmail.getUserPwd());
                            sp_editor.putString("gender", userByEmail.getUserGender());
                            sp_editor.putString("affiliatedHospital", userByEmail.getAffiliatedHospital());
                            sp_editor.putString("diagnosisNumber", userByEmail.getDiagnosisNumber());
                            sp_editor.putString("birthday", new SimpleDateFormat("yyyy年MM月dd日").format(userByEmail.getUserBirthday()));
                            sp_editor.apply();
                            Log.d(null, "loginUser affiliatedHospital: " + sharedPreferences.getString("affiliatedHospital", ""));
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

        textView_adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLoginFragment = new AdminLoginFragment();
                WelcomeActivity welcomeActivity = (WelcomeActivity) getActivity();
                Administrator administrator = new Administrator();
                administrator.setAdministratorName("admin");
                administrator.setAdministratorEmail("admin@lyeye.com");
                administrator.setAdministratorPwd("123");
                new AdministratorImpl(welcomeActivity).addAdmin(administrator);
                FragmentManager fragmentManager = welcomeActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out)
                        .replace(R.id.fl_swl_container, adminLoginFragment)
                        .commitAllowingStateLoss();
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

