package com.lyeye.dentalappointmentsystem.administration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.adapter.UserRecyclerViewAdapter;
import com.lyeye.dentalappointmentsystem.appointment.XLinearLayoutManager;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.mapper.UserImpl;

import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textView_admin;

    private SharedPreferences sharedPreferences;
    private UserImpl userImpl;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        init();

        recyclerView.setLayoutManager(new XLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new UserRecyclerViewAdapter(this, userList));
    }

    private void init() {
        recyclerView = findViewById(R.id.rv_ul_user_list);
        textView_admin = findViewById(R.id.tv_ul_admin);
        sharedPreferences = getSharedPreferences("admin_info", MODE_PRIVATE);
        userImpl = new UserImpl(UserListActivity.this);

        textView_admin.setText("你好" + sharedPreferences.getString("adminName", ""));
        userList = userImpl.findAll();
    }
}