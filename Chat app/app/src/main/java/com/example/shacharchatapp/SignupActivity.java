package com.example.shacharchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private TextView mSignUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Get references to UI elements
        mEmailField = findViewById(R.id.email_field);
        mPasswordField = findViewById(R.id.password_field);
        mConfirmPasswordField = findViewById(R.id.confirm_password_field);
        Button signUpButton = findViewById(R.id.signup_button);
        mSignUpTextView = findViewById(R.id.signup_textview);

        // Set click listener for sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailField.getText().toString();
                String password = mPasswordField.getText().toString();
                String confirmPassword = mConfirmPasswordField.getText().toString();

                signUp(email, password, confirmPassword);
            }
        });

        // Set click listener for "Already have an account?" text view
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the login activity
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Checks if the given password meets the required strength criteria.
     *
     * @param pass  The password to be checked.
     * @return      True if the password meets the criteria, false otherwise.
     */
    private Boolean checkPasswordStrength(String pass) {
        String regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$";
        return pass.matches(regexp);
    }

    /**
     * Attempts to sign up the user with the provided email and password.
     *
     * @param email             The user's email address.
     * @param password          The user's password.
     * @param confirmPassword  The confirmation of the user's password.
     */
    private void signUp(String email, String password, String confirmPassword) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        if (!checkPasswordStrength(password)) {
            Toast.makeText(getApplicationContext(), "Password is too weak, it must contain 1 letter, 1 digit " +
                    "and 1 capital letter, the password length must be between 6-20.", Toast.LENGTH_SHORT).show();
            return;
        }
        */

        // Attempt to create a new user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                        // Add code here to redirect the user to the main screen or perform any other necessary tasks
                    } else {
                        // Sign up failed
                        Toast.makeText(getApplicationContext(), "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
