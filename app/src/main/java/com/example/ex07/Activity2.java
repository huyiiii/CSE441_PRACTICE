package com.example.ex07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    EditText edta, edtb;
    Button btnkq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        edta = findViewById(R.id.edta);
        edtb = findViewById(R.id.edtb);
        btnkq = findViewById(R.id.button2);
        btnkq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent myintent = new Intent(Activity2.this, KetQuaActivity.class);
                Bundle bundle1 = new Bundle();
                int a = Integer.parseInt(edta.getText() + "");
                int b = Integer.parseInt(edtb.getText() + "");
                bundle1.putInt("soa", a);
                bundle1.putInt("sob", b);
                myintent.putExtra("mybackage", bundle1);
                startActivity(myintent);
            }
        });
    }
}

