package com.finalproject.movieticketbooking.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.appcompat.app.AppCompatActivity;
import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameRegister, phoneRegister, emailRegister, passwordRegister, birthDate, gender, regionRegister, districtRegister;
    private Button btnRegister;
    private ImageView eyeoffIcon;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        nameRegister = findViewById(R.id.nameRegister);
        phoneRegister = findViewById(R.id.phoneRegister);
        emailRegister = findViewById(R.id.emailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        birthDate = findViewById(R.id.birthDate);
        progressBar = findViewById(R.id.progressBarRegist);
        regionRegister = findViewById(R.id.editTextRegion);
        districtRegister = findViewById(R.id.editTextDistrict);
        btnRegister = findViewById(R.id.btnLogin);
        eyeoffIcon = findViewById(R.id.eyeoffIcon);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        eyeoffIcon.setOnClickListener(v -> togglePasswordVisibility());

        birthDate.setOnClickListener(v -> showDatePicker());

        btnRegister.setOnClickListener(v -> registerUser());

        regionRegister.setOnClickListener(v -> showRegionMenu());

        districtRegister.setOnClickListener(v -> {
            // Kiểm tra xem người dùng đã chọn tỉnh thành chưa
            if (TextUtils.isEmpty(regionRegister.getText())) {
                Toast.makeText(this, "Vui lòng chọn Tỉnh/Thành phố trước", Toast.LENGTH_SHORT).show();
            } else {
                String region = regionRegister.getText().toString();
                showDistrictMenu(region);
            }
        });
    }

    private void togglePasswordVisibility() {
        if (passwordRegister.getTransformationMethod() instanceof PasswordTransformationMethod) {
            passwordRegister.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            eyeoffIcon.setImageResource(R.drawable.ic_eye);
        } else {
            passwordRegister.setTransformationMethod(PasswordTransformationMethod.getInstance());
            eyeoffIcon.setImageResource(R.drawable.ic_eyeoff);
        }
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            birthDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);

        DatePicker datePicker = datePickerDialog.getDatePicker();

        int minYear = 1900;
        int maxYear = year;
        datePicker.setMinDate(new GregorianCalendar(minYear, month, day).getTimeInMillis());
        datePicker.setMaxDate(new GregorianCalendar(maxYear, month, day).getTimeInMillis());

        datePickerDialog.show();
    }

    private void showRegionMenu() {
        PopupMenu popupMenu = new PopupMenu(this, regionRegister);
        getMenuInflater().inflate(R.menu.region_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            String selectedRegion = item.getTitle().toString();
            regionRegister.setText(selectedRegion);
            return true;
        });

        popupMenu.show();
    }

    private void showDistrictMenu(String region) {
        PopupMenu popupMenu = new PopupMenu(this, districtRegister);
        switch (region) {
            case "Hà Nội":
                getMenuInflater().inflate(R.menu.district_hanoi, popupMenu.getMenu());
                break;
            case "TP. Hồ Chí Minh":
                getMenuInflater().inflate(R.menu.district_hcm, popupMenu.getMenu());
                break;
            case "Đà Nẵng":
                getMenuInflater().inflate(R.menu.district_danang, popupMenu.getMenu());
                break;
            case "Hải Phòng":
                getMenuInflater().inflate(R.menu.district_haiphong, popupMenu.getMenu());
                break;
            default:
                Toast.makeText(this, "Chưa có quận/huyện cho tỉnh thành này", Toast.LENGTH_SHORT).show();
                return;
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            districtRegister.setText(item.getTitle());
            return true;
        });

        popupMenu.show();
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void registerUser() {
        String name = nameRegister.getText().toString().trim();
        String phone = phoneRegister.getText().toString().trim();
        String email = emailRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();
        String birth = birthDate.getText().toString().trim();
        String region = regionRegister.getText().toString().trim();
        String district = districtRegister.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                        if (firebaseUser != null) {
                            progressBar.setVisibility(View.VISIBLE);
                            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                            progressBar.startAnimation(fadeIn);
                            String userId = firebaseUser.getUid();

                            String encryptedPassword = encryptPassword(password);

                            String address = district + ", " + region;

                            List<String> ticketHistory = new ArrayList<>();
                            ticketHistory.add("");

                            String avatar = "";

                            User user = new User(userId, email, name, phone, encryptedPassword, birth, address, avatar, ticketHistory);

                            databaseReference.child(userId).setValue(user)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Animation fadeOut = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.fade_out);
                                            progressBar.startAnimation(fadeOut);
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Lỗi khi lưu thông tin người dùng. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Animation fadeOut = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.fade_out);
                        progressBar.startAnimation(fadeOut);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}