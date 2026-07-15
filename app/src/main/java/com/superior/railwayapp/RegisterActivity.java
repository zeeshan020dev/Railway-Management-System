package com.superior.railwayapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    // Firebase Auth - user account banane ke liye
    private FirebaseAuth mAuth;
    // Firebase Database - user info save karne ke liye
    private DatabaseReference mDatabase;
    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Firebase initialize kiya
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Views connect kiye
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Validation - khaali fields check karo
                if (name.isEmpty() || email.isEmpty() ||
                        password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,
                            "Sari fields bharein!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Password match check
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this,
                            "Passwords match nahi kar rahe!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Password length check
                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this,
                            "Password kam az kam 6 characters ka ho!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Firebase mein account banao
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // User ka naam database mein save karo
                                String uid = mAuth.getCurrentUser().getUid();
                                mDatabase.child("users").child(uid).child("name").setValue(name);
                                mDatabase.child("users").child(uid).child("email").setValue(email);

                                Toast.makeText(RegisterActivity.this,
                                        "Account ban gaya!", Toast.LENGTH_SHORT).show();

                                // Login page pe bhejo
                                startActivity(new Intent(RegisterActivity.this,
                                        LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this,
                                        "Error: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Login pe wapas jao
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}