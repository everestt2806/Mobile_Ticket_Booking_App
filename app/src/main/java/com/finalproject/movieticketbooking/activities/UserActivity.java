package com.finalproject.movieticketbooking.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.movieticketbooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    private TextView userName;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ProgressBar logoutProgressBar;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userName = findViewById(R.id.userName);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Lấy địa chỉ email của người dùng đã đăng nhập
            String userEmail = currentUser.getEmail();

            // Truy cập vào Firebase Realtime Database
            databaseReference = FirebaseDatabase.getInstance().getReference("users");

            // Lấy thông tin người dùng dựa vào email
            databaseReference.orderByChild("email").equalTo(userEmail)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String fullName = snapshot.child("fullName").getValue(String.class);
                                    userName.setText(fullName);
                                }
                            } else {
                                Toast.makeText(UserActivity.this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(UserActivity.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        logoutButton = findViewById(R.id.logoutButton);
        logoutProgressBar = findViewById(R.id.logoutProgressBar);
        // Xử lý sự kiện khi bấm nút "Đăng xuất"
        logoutButton.setOnClickListener(view -> logout());
    }
    private void logout() {
        logoutProgressBar.setVisibility(View.VISIBLE);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoutProgressBar.startAnimation(fadeIn);
        logoutButton.setEnabled(false);

        mAuth.signOut();
        clearSharedPreferences();

        Animation fadeOut = AnimationUtils.loadAnimation(UserActivity.this, R.anim.fade_out);
        logoutProgressBar.startAnimation(fadeOut);
        logoutProgressBar.setVisibility(View.GONE);
        logoutButton.setEnabled(true);

        Intent intent = new Intent(UserActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        Toast.makeText(UserActivity.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
    }

    private void clearSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
