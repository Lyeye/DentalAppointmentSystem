package com.lyeye.dentalappointmentsystem.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lyeye.dentalappointmentsystem.R;

public class AppointmentDetailActivity extends AppCompatActivity {

    private TextView textView_appointment, textView_hint, textView_level;
    private EditText editText_describe;
    private Button button_serious, button_medium, button_light, button_unimportant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        init();

        textView_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String symptom = editText_describe.getText().toString();
                if (symptom.length() == 0) {
                    textView_hint.setText("（*不能为空！！！）");
                } else {
                    Intent intent = new Intent(AppointmentDetailActivity.this, DateSelectionActivity.class);
                    intent.putExtra("symptom", symptom);
                    startActivity(intent);
                }
            }
        });
        button_serious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_level.setText("严重");
                textView_level.setBackground(getResources().getDrawable(R.color.red));
            }
        });
        button_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_level.setText("中度");
                textView_level.setBackground(getResources().getDrawable(R.color.orange));
            }
        });
        button_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_level.setText("轻度");
                textView_level.setBackground(getResources().getDrawable(R.color.yellow));
            }
        });
        button_unimportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_level.setText("轻微");
                textView_level.setBackground(getResources().getDrawable(R.color.greenyellow));
            }
        });
    }

    private void init() {
        editText_describe = findViewById(R.id.et_ad_describe);
        textView_hint = findViewById(R.id.tv_ad_hint);
        textView_appointment = findViewById(R.id.tv_ad_appointment);
        textView_level = findViewById(R.id.tv_ad_level);
        button_serious = findViewById(R.id.btn_ad_serious);
        button_medium = findViewById(R.id.btn_ad_medium);
        button_light = findViewById(R.id.btn_ad_light);
        button_unimportant = findViewById(R.id.btn_ad_unimportant);
    }
}
