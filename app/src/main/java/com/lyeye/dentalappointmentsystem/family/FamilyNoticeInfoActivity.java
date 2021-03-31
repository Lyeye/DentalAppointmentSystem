package com.lyeye.dentalappointmentsystem.family;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.BmobAppointmentInfo;
import com.lyeye.dentalappointmentsystem.entity.BmobUser;
import com.lyeye.dentalappointmentsystem.greendao.DaoManager;
import com.lyeye.dentalappointmentsystem.util.UserLoginMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FamilyNoticeInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private BmobUser user;
    private String userName;
    private List<BmobAppointmentInfo> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_notice);

        recyclerView = findViewById(R.id.rv_fni_notice);

        EventBus.getDefault().register(this);

        BmobQuery<BmobAppointmentInfo> bmobAppointmentInfoBmobQuery = new BmobQuery<>();
        bmobAppointmentInfoBmobQuery.addWhereEqualTo("userName", userName);
        bmobAppointmentInfoBmobQuery.findObjects(new FindListener<BmobAppointmentInfo>() {
            @Override
            public void done(List<BmobAppointmentInfo> list, BmobException e) {
                if (list != null) {
                    noticeList = list;
                    recyclerView.setLayoutManager(new XLinearLayoutManager(FamilyNoticeInfoActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(new FamilyNoticeRecyclerViewAdapter(FamilyNoticeInfoActivity.this, noticeList));
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUserLoginMessageEvent(UserLoginMessageEvent userLoginMessageEvent) {
        user = userLoginMessageEvent.getUser();
        userName = user.getUserName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
