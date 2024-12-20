package com.example.mybookstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends BaseActivity {

    // UI Components
    FirebaseAuth mAuth;
    EditText etEmail, etPassword;
    Button btnSignIn;
    TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide(); // Hide the action bar for cleaner UI

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is already logged in
        if (mAuth.getCurrentUser() != null) {
            navigateToHome();
        }

        // Initialize UI Components
        etEmail = findViewById(R.id.UserName); // Update ID for clarity in XML
        etPassword = findViewById(R.id.Password);
        btnSignIn = findViewById(R.id.signInBtn);
        tvSignUp = findViewById(R.id.SignUP);

        // Set up "Sign In" button click listener
        btnSignIn.setOnClickListener(view -> {
            btnSignIn.setEnabled(false); // Disable button to prevent double-click
            loginUser();
        });

        // Set up "Sign Up" text click listener
        tvSignUp.setOnClickListener(view -> navigateToSignUp());
    }

    // Navigate to the home activity (CategoryActivity)
    private void navigateToHome() {
        Intent intent = new Intent(SignInActivity.this, CategoryActivity.class);
        startActivity(intent);
        finish();
    }

    // Navigate to the Sign Up activity
    private void navigateToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    // User Login Method
    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
            btnSignIn.setEnabled(true);
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            btnSignIn.setEnabled(true);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            btnSignIn.setEnabled(true);
            return;
        }

        // Authenticate with Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        btnSignIn.setEnabled(true); // Re-enable button after completion

                        if (task.isSuccessful()) {
                            // Login successful
                            Toast.makeText(SignInActivity.this, "Sign In Successful!", Toast.LENGTH_SHORT).show();
                            navigateToHome();
                        } else {
                            // Login failed
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Login Failed";
                            Toast.makeText(SignInActivity.this, "Sign In Failed: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
