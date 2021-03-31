package com.lyeye.dentalappointmentsystem.family;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.BmobUser;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.lyeye.dentalappointmentsystem.util.UserLoginMessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyFamilyActivity extends AppCompatActivity {

    private EditText editText_diagnosisNumber;
    private TextView textView_familyBirthday;
    private Button button_familyLogin;

    private Date select_birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_family);

        editText_diagnosisNumber = findViewById(R.id.et_mf_diagnosisNumber);
        textView_familyBirthday = findViewById(R.id.tv_mf_familyBirthday);
        button_familyLogin = findViewById(R.id.btn_mf_familyLogin);


        final Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(1950, 0, 1);
        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(2020, 0, 1);

        textView_familyBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView timePickerView_birthday = new TimePickerBuilder(MyFamilyActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("YYYY年MM月dd日");
                        String format_birthday = format.format(date);
                        textView_familyBirthday.setText(format_birthday);
                        select_birthday = date;
                    }
                }).setTitleText("选择您的生日").setCancelText("取消").setSubmitText("确定").isCyclic(true).setRangDate(startCalendar, endCalendar).build();
                timePickerView_birthday.show();
            }
        });

        button_familyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_diagnosisNumber.getText().toString().length() == 0 || textView_familyBirthday.getText().toString() == "选择亲属生日") {
                    ToastUtil.showMsg(MyFamilyActivity.this, "请输入亲属诊察号以及选择亲属生日!");
                } else {
                    BmobQuery<BmobUser> bmobUserBmobQuery = new BmobQuery<>();
                    bmobUserBmobQuery.addWhereEqualTo("diagnosisNumber", editText_diagnosisNumber.getText().toString());
                    bmobUserBmobQuery.findObjects(new FindListener<BmobUser>() {
                        @Override
                        public void done(List<BmobUser> list, BmobException e) {
                            if (list == null) {
                                ToastUtil.showMsg(MyFamilyActivity.this, "亲属诊察号错误!");
                            } else {
                                String date1 = select_birthday.getYear() + "年" + select_birthday.getMonth() + "月" + select_birthday.getDate() + "日";
                                Date userBirthday = list.get(0).getUserBirthday();
                                String date2 = userBirthday.getYear() + "年" + userBirthday.getMonth() + "月" + userBirthday.getDate() + "日";
                                if (date1.equals(date2)) {
                                    EventBus.getDefault().postSticky(new UserLoginMessageEvent(list.get(0)));
                                    Intent intent = new Intent(MyFamilyActivity.this, FamilyAppointmentInfoActivity.class);
                                    startActivity(intent);
                                } else {
                                    ToastUtil.showMsg(MyFamilyActivity.this, "生日错误!");
                                }
                            }
                        }
                    });
                }
            }
        });



        /*
        调整图标大小
        */
        Drawable drawable_add = getResources().getDrawable(R.mipmap.ic_add);
        drawable_add.setBounds(0, 0, 60, 60);
        button_familyLogin.setCompoundDrawables(drawable_add, null, null, null);
    }
}
