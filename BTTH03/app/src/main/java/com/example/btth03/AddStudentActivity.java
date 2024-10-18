package com.example.btth03;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;

public class AddStudentActivity extends AppCompatActivity {

    private EditText editTextID, editTextFirstName, editTextMiddleName, editTextLastName,
            editTextBirthDate, editTextEmail, editTextAddress,
            editTextMajor, editTextGPA, editTextYear;
    private RadioGroup radioGroupGender;
    private TextView textViewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_activity);

        editTextID = findViewById(R.id.editTextID);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextMiddleName = findViewById(R.id.editTextMiddleName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextBirthDate = findViewById(R.id.editTextBirthDate);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextMajor = findViewById(R.id.editTextMajor);
        editTextGPA = findViewById(R.id.editTextGPA);
        editTextYear = findViewById(R.id.editTextYear);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        textViewError = findViewById(R.id.textViewError);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        // DatePicker cho Ngày sinh
        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmit();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Định dạng ngày là dd/MM/yyyy
                        String date = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        editTextBirthDate.setText(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void validateAndSubmit() {
        String id = editTextID.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String middleName = editTextMiddleName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String birthDate = editTextBirthDate.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String major = editTextMajor.getText().toString().trim();
        String gpaString = editTextGPA.getText().toString().trim();
        String yearString = editTextYear.getText().toString().trim();

        // Reset thông báo lỗi
        textViewError.setVisibility(View.GONE);
        textViewError.setText("");

        // Validate inputs
        if (TextUtils.isEmpty(id)) {
            textViewError.setText("Mã sinh viên không được để trống!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(firstName)) {
            textViewError.setText("Họ không được để trống!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            textViewError.setText("Tên không được để trống!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(birthDate)) {
            textViewError.setText("Ngày sinh không được để trống!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(email)) {
            textViewError.setText("Email không được để trống!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            textViewError.setText("Địa chỉ không được để trống!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(major)) {
            textViewError.setText("Chuyên ngành không được để trống!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(gpaString)) {
            textViewError.setText("GPA không được để trống!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(yearString)) {
            textViewError.setText("Năm học không được để trống!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }

        // Kiểm tra giới tính
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedId == -1) {
            textViewError.setText("Bạn phải chọn giới tính!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }

        // Validate GPA
        double gpa;
        try {
            gpa = Double.parseDouble(gpaString);
        } catch (NumberFormatException e) {
            textViewError.setText("GPA không hợp lệ!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }

        // Validate Year
        int year;
        try {
            year = Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            textViewError.setText("Năm học không hợp lệ!");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }
        String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        saveStudentDataToJson(id, firstName, middleName, lastName, gender, birthDate, email, address, major, gpa, year);
        Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Dữ liệu đã được gửi!", Toast.LENGTH_SHORT).show();
    }

    private void saveStudentDataToJson(String id, String firstName, String middleName, String lastName,
                                       String gender, String birthDate, String email,
                                       String address, String major, double gpa, int year) {
        // Tạo đối tượng JSON cho sinh viên mới
        JSONObject studentJson = new JSONObject();
        try {
            studentJson.put("id", id);

            JSONObject fullName = new JSONObject();
            fullName.put("first", firstName);
            fullName.put("midd", middleName);
            fullName.put("last", lastName);

            studentJson.put("fullName", fullName);
            studentJson.put("gender", gender);
            studentJson.put("birth_date", birthDate);
            studentJson.put("email", email);
            studentJson.put("address", address);
            studentJson.put("major", major);
            studentJson.put("gpa", gpa);
            studentJson.put("year", year);

            // Đọc file students.json hiện có từ assets
            JSONArray studentsArray = readStudentsJsonArray();

            // Thêm đối tượng sinh viên mới vào mảng
            studentsArray.put(studentJson);

            // Ghi lại dữ liệu vào file mới trong bộ nhớ trong
            writeStudentsJsonArray(studentsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray readStudentsJsonArray() {
        // Đọc dữ liệu từ file JSON trong thư mục assets và trả về một JSONArray
        JSONArray jsonArray = new JSONArray();
        try {
            InputStream is = getAssets().open("students.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private void writeStudentsJsonArray(JSONArray jsonArray) {
        // Ghi dữ liệu vào file JSON trong bộ nhớ trong
        try {
            FileOutputStream fos = openFileOutput("students_data.json", MODE_PRIVATE);
            fos.write(jsonArray.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
