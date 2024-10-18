package com.example.btth03;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        TextView studentDetailTextView = findViewById(R.id.studentDetailTextView);


        Student student = (Student) getIntent().getSerializableExtra("student");

        if (student != null) {
            String detail = "ID: " + student.getId() + "\n" +
                    "Name: " + student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName() + "\n" +
                    "Gender: " + student.getGender() + "\n" +
                    "Birth Date: " + student.getBirthDate() + "\n" +
                    "Email: " + student.getEmail() + "\n" +
                    "Address: " + student.getAddress() + "\n" +
                    "Major: " + student.getMajor() + "\n" +
                    "GPA: " + student.getGpa() + "\n" +
                    "Year: " + student.getYear();
            studentDetailTextView.setText(detail);
        } else {
            // Xử lý nếu student bị null
            Intent intent = new Intent(StudentDetailActivity.this, MainActivity.class);
            startActivity(intent);
        }



    }
}

