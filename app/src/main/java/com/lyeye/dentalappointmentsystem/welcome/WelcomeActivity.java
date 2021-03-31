package com.lyeye.dentalappointmentsystem.welcome;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobTableSchema;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class WelcomeActivity extends AppCompatActivity {

    private boolean isReady = false;

    private WelcomeFrament welcomeFrament;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Bmob.initialize(this, "0d7ffa7b53c0d9e51d06084302c5368c");
        start();
    }

    private void start() {
        welcomeFrament = new WelcomeFrament();
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .add(R.id.fl_swl_container, welcomeFrament)
                .setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out)
                .commitAllowingStateLoss();
    }
}
