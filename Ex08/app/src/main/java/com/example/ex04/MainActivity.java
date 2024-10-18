package com.example.ex04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    Button call,sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        call.findViewById(R.id.btncallphone);
        sms.findViewById(R.id.btnsendsms);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
//Tạo mới một đối tượng intent
                Intent intent1 =new Intent(MainActivity.this,CallPhoneActivity.class);
//Thực thi Intent1
                startActivity(intent1);
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
//Tạo mới một đối tượng intent
                Intent intent2 =new Intent(MainActivity.this,SendSMSActivity.class);
//Thực thi Intent1
                startActivity(intent2);
            }
        });


    }
}
