package com.lyeye.dentalappointmentsystem.scan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lyeye.dentalappointmentsystem.R;

public class PayActivity extends AppCompatActivity {

    private TextView textView_name, textView_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        textView_name = findViewById(R.id.goodsname);
        textView_price = findViewById(R.id.price);


    }
}
