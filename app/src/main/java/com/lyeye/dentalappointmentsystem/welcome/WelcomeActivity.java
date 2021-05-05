package com.lyeye.dentalappointmentsystem.welcome;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lyeye.dentalappointmentsystem.R;

import com.lyeye.dentalappointmentsystem.home.MainActivity;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobTableSchema;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import okhttp3.OkHttpClient;

public class WelcomeActivity extends AppCompatActivity {

    private boolean isExit;

    private WelcomeFrament welcomeFrament;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        start();
        ZXingLibrary.initDisplayOpinion(this);
    }

    private void start() {
        welcomeFrament = new WelcomeFrament();
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .add(R.id.fl_swl_container, welcomeFrament)
                .setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out)
                .commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.showMsg(WelcomeActivity.this, "再按一下退出程序");
            new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    isExit = false;
                }
            }.sendEmptyMessageDelayed(0, 2000);
        } else {
            finishAffinity();
            ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(this.getPackageName());
            System.exit(0);
        }
    }
}
