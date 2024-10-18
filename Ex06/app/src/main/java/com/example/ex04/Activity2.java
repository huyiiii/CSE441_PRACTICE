package com.example.ex04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Activity2 extends AppCompatActivity {
    Button btnChandoan;
    EditText editTen,editChieucao,editCannang,editBMI,editChandoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        btnChandoan= findViewById(R.id.btnBmi);
        editTen=findViewById(R.id.edtName);
        editChieucao= findViewById(R.id.edtCao);
        editCannang= findViewById(R.id.edtNang);
        editBMI= findViewById(R.id.edtBmi);
        editChandoan= findViewById(R.id.edtCD);
        btnChandoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                double H=Double.parseDouble(editChieucao.getText()+"");
                double W=Double.parseDouble(editCannang.getText()+"");
                double BMI=W/Math.pow(H,2);
                String chandoan="";
                if(BMI<18)
                {
                    chandoan="Bạn gầy";

                }
                else if(BMI<=24.9)
                {
                    chandoan="Bạn bình thường";
                }
                else if(BMI<=29.9)
                {
                    chandoan="Bạn béo phì độ 1";
                }
                else if(BMI<=34.9)
                {
                    chandoan="Bạn béo phì độ 2";
                }
                else
                {
                    chandoan="Bạn béo phì độ 3";
                }
                DecimalFormat dcf=new DecimalFormat("#.0");
                editBMI.setText(dcf.format(BMI));
                editChandoan.setText(chandoan);
            }
        });
    }

}
