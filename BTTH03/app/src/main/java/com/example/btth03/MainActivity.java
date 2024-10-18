package com.example.btth03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;
    private static final String STUDENTS_DATA_FILE = "students_data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentList = new ArrayList<>();

        // Kiểm tra xem đã có dữ liệu sinh viên lưu chưa
        if (loadStudentsFromStorage()) {
            // Nếu có dữ liệu, sử dụng dữ liệu đó
            studentAdapter = new StudentAdapter(studentList);
            recyclerView.setAdapter(studentAdapter);
        } else {
            // Nếu không có dữ liệu, đọc từ file JSON
            readStudentsFromJson();
        }

        // Thiết lập adapter
        studentAdapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(studentAdapter);

        // Xử lý sự kiện khi nhấn vào sinh viên
        studentAdapter.setOnItemClickListener(student -> {
            if (student != null) {
                Intent intent = new Intent(MainActivity.this, StudentDetailActivity.class);
                intent.putExtra("student", student);  // Sử dụng Serializable hoặc Parcelable để truyền đối tượng
                startActivity(intent);
            }
        });
    }

    private void readStudentsFromJson() {
        try {
            InputStream is = getAssets().open("students.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String id = obj.getString("id");
                JSONObject fullName = obj.getJSONObject("fullName");
                String firstName = fullName.getString("first");
                String middleName = fullName.getString("midd");
                String lastName = fullName.getString("last");
                String gender = obj.getString("gender");
                String birthDate = obj.getString("birth_date");
                String email = obj.getString("email");
                String address = obj.getString("address");
                String major = obj.getString("major");
                double gpa = obj.getDouble("gpa");
                int year = obj.getInt("year");

                studentList.add(new Student(id, firstName, middleName, lastName, gender, birthDate, email, address, major, gpa, year));
            }

            // Lưu dữ liệu vào bộ nhớ trong sau khi đọc từ JSON
            saveStudentsToStorage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean loadStudentsFromStorage() {
        try {
            FileInputStream fis = openFileInput(STUDENTS_DATA_FILE);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String id = obj.getString("id");
                JSONObject fullName = obj.getJSONObject("fullName");
                String firstName = fullName.getString("first");
                String middleName = fullName.getString("midd");
                String lastName = fullName.getString("last");
                String gender = obj.getString("gender");
                String birthDate = obj.getString("birth_date");
                String email = obj.getString("email");
                String address = obj.getString("address");
                String major = obj.getString("major");
                double gpa = obj.getDouble("gpa");
                int year = obj.getInt("year");

                studentList.add(new Student(id, firstName, middleName, lastName, gender, birthDate, email, address, major, gpa, year));
            }
            return true; // Đã tải dữ liệu thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Không tải được dữ liệu
        }
    }

    private void saveStudentsToStorage() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Student student : studentList) {
                JSONObject studentJson = new JSONObject();
                studentJson.put("id", student.getId());

                JSONObject fullName = new JSONObject();
                fullName.put("first", student.getFirstName());
                fullName.put("midd", student.getMiddleName());
                fullName.put("last", student.getLastName());
                studentJson.put("fullName", fullName);

                studentJson.put("gender", student.getGender());
                studentJson.put("birth_date", student.getBirthDate());
                studentJson.put("email", student.getEmail());
                studentJson.put("address", student.getAddress());
                studentJson.put("major", student.getMajor());
                studentJson.put("gpa", student.getGpa());
                studentJson.put("year", student.getYear());

                jsonArray.put(studentJson);
            }

            FileOutputStream fos = openFileOutput(STUDENTS_DATA_FILE, MODE_PRIVATE);
            fos.write(jsonArray.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
