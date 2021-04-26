package com.lyeye.dentalappointmentsystem.welcome;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UrlUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterFragment extends Fragment {

    private Button button_signIn;
    private EditText editText_phone, editText_email, editText_username, editText_pwd, editText_confirmpwd;
    private RadioGroup radioGroup_gender;
    private RadioButton radioButton_male, radioButton_female;
    private Spinner spinner_userType;
    private TextView textView_select, textView_birthday;

    private LoginFragment loginFragment;
    private String gender;
    private String birthday;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final WelcomeActivity welcomeActivity = (WelcomeActivity) getActivity();

        final Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(1950, 0, 1);
        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(2020, 0, 1);

        button_signIn = view.findViewById(R.id.btn_fr_register);
        editText_username = view.findViewById(R.id.et_fr_username);
        editText_pwd = view.findViewById(R.id.et_fr_password);
        editText_phone = view.findViewById(R.id.et_fr_phone);
        editText_email = view.findViewById(R.id.et_fr_email);
        editText_confirmpwd = view.findViewById(R.id.et_fr_confirmpassword);
        spinner_userType = view.findViewById(R.id.spinner_fr_type);
        textView_select = view.findViewById(R.id.tv_fr_selecthospital);
        textView_birthday = view.findViewById(R.id.tv_fr_birthday);

        radioGroup_gender = view.findViewById(R.id.rg_fr_gender);
        radioButton_male = view.findViewById(R.id.rb_fr_man);
        radioButton_female = view.findViewById(R.id.rb_fr_woman);

        textView_birthday.setText("请选择生日");
        textView_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView timePickerView_birthday = new TimePickerBuilder(welcomeActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("YYYY年MM月dd日");
                        String format_birthday = format.format(date);
                        textView_birthday.setText(format_birthday);
                        birthday = format_birthday;
                    }
                }).setTitleText("选择您的生日").setCancelText("取消").setSubmitText("确定").isCyclic(true)
                        .setRangDate(startCalendar, endCalendar).build();
                timePickerView_birthday.show();
            }
        });


        radioGroup_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButton_male.getId() == checkedId) {
                    gender = radioButton_male.getText().toString();
                } else if (radioButton_female.getId() == checkedId) {
                    gender = radioButton_female.getText().toString();
                }
            }
        });

        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_username.getText().toString();
                String email = editText_email.getText().toString();
                String phone = editText_phone.getText().toString();
                String password = editText_pwd.getText().toString();
                String type = spinner_userType.getSelectedItem().toString();
                String number = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                String createAt = new SimpleDateFormat("YYYY年MM月dd日").format(new Date());


                boolean isEmail = isEmail(editText_email.getText().toString());
                boolean isMobile = isMobile(editText_phone.getText().toString());

                if (editText_email != null
                        && editText_phone != null
                        && isEmail == true
                        && isMobile == true
                        && editText_username != null
                        && editText_pwd != null
                        && editText_confirmpwd != null
                        && (editText_confirmpwd.getText().toString()).equals(editText_pwd.getText().toString())) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String json = "{\n" +
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
                                OkHttpClient okHttpClient = new OkHttpClient();
                                Request request = new Builder()
                                        .url(UrlUtil.getURL("signUp"))
                                        .post(RequestBody.create(MediaType.parse("application/json"), json))
                                        .build();
                                Response response = okHttpClient.newCall(request).execute();
                                welcomeActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showMsg(welcomeActivity, "正在注册...");
                                    }
                                });
                                Log.d(null, "注册成功");
                                welcomeActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showMsg(welcomeActivity, "注册成功");
                                        loginFragment = new LoginFragment();
                                        FragmentManager fragmentManager = welcomeActivity.getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction
                                                .addToBackStack(null)
                                                .setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out)
                                                .replace(R.id.fl_swl_container, loginFragment)
                                                .commitAllowingStateLoss();
                                    }
                                });

                            } catch (Exception e) {
                                Log.d(null, "注册失败：" + e);
                                welcomeActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showMsg(welcomeActivity, "注册失败：" + e);
                                    }
                                });
                            }
                        }
                    }).start();

                } else if (editText_email.getText().length() == 0) {
                    ToastUtil.showMsg(welcomeActivity, "邮箱不能为空！");
                } else if (isEmail == false) {
                    ToastUtil.showMsg(welcomeActivity, "邮箱格式不正确！");
                } else if (editText_phone.getText().length() == 0) {
                    ToastUtil.showMsg(welcomeActivity, "手机号不能为空！");
                } else if (isMobile == false) {
                    ToastUtil.showMsg(welcomeActivity, "手机号格式不正确！");
                } else if (editText_username.getText().length() == 0) {
                    ToastUtil.showMsg(welcomeActivity, "用户名不能为空！");
                } else if (editText_pwd.getText().length() == 0) {
                    ToastUtil.showMsg(welcomeActivity, "密码不能为空！");
                } else if (editText_confirmpwd.getText().length() == 0) {
                    ToastUtil.showMsg(welcomeActivity, "确认密码不能为空！");
                } else if (!(editText_confirmpwd.getText().toString()).equals(editText_pwd.getText().toString())) {
                    ToastUtil.showMsg(welcomeActivity, "密码和确认密码不一致！");
                }
            }
        });

        /*
        调整图标大小
        */
        Drawable drawable_hospital = view.getResources().getDrawable(R.drawable.ic_hospital);
        drawable_hospital.setBounds(0, 0, 50, 55);
        textView_select.setCompoundDrawables(drawable_hospital, null, null, null);

        Drawable drawable_phone = view.getResources().getDrawable(R.mipmap.ic_register_phone);
        drawable_phone.setBounds(0, 0, 100, 110);
        editText_phone.setCompoundDrawables(drawable_phone, null, null, null);

        Drawable drawable_email = view.getResources().getDrawable(R.mipmap.ic_register_email);
        drawable_email.setBounds(0, 0, 100, 110);
        editText_email.setCompoundDrawables(drawable_email, null, null, null);

        Drawable drawable_user = view.getResources().getDrawable(R.mipmap.ic_launcher_user);
        drawable_user.setBounds(0, 0, 100, 110);
        editText_username.setCompoundDrawables(drawable_user, null, null, null);

        Drawable drawable_password = view.getResources().getDrawable(R.mipmap.ic_launcher_pwd);
        drawable_password.setBounds(0, 0, 100, 110);
        editText_pwd.setCompoundDrawables(drawable_password, null, null, null);

        Drawable drawable_confirm_password = view.getResources().getDrawable(R.mipmap.ic_register_confirmpwd);
        drawable_confirm_password.setBounds(0, 0, 100, 110);
        editText_confirmpwd.setCompoundDrawables(drawable_confirm_password, null, null, null);

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

