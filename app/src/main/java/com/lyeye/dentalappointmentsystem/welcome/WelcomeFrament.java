package com.lyeye.dentalappointmentsystem.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.home.MainActivity;


public class WelcomeFrament extends Fragment {

    private LoginFragment loginFragment;
    private SharedPreferences sharedPreferences;
    private String userEmail;

    private WelcomeActivity welcomeActivity;

    private Button button_welcome;
    private TextView textView_change_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        welcomeActivity = (WelcomeActivity) getActivity();

        button_welcome = view.findViewById(R.id.btn_fw_start);
        button_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_welcome.setText("正在登录，请稍等...");

                sharedPreferences = welcomeActivity.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                userEmail = sharedPreferences.getString("userEmail", "");
//                if (userEmail == "" || userImpl.findUserByEmail(userEmail) == null) {
//                    loginFragment = new LoginFragment();
//                    FragmentManager fragmentManager = welcomeActivity.getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction
//                            .addToBackStack(null)
//                            .setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out)
//                            .replace(R.id.fl_swl_container, loginFragment)
//                            .commitAllowingStateLoss();
//                } else {
//                    Intent intent = new Intent(welcomeActivity, MainActivity.class);
//                    startActivity(intent);
//                }
            }
        });
        textView_change_user = view.findViewById(R.id.tv_fw_change_user);
        textView_change_user.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView_change_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WelcomeActivity welcomeActivity = (WelcomeActivity) getActivity();
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
    }
}
