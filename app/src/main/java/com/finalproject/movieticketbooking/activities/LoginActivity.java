package com.finalproject.movieticketbooking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.movieticketbooking.R;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().equals("user1@gmail.com") && password.getText().toString().equals("123456")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}