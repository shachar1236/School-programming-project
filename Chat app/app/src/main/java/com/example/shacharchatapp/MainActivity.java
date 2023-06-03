package com.example.shacharchatapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The main activity of the application responsible for checking the user's authentication status
 * and navigating to the appropriate screen.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this);

        // Get the instance of FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Get the currently logged-in user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Check if the user is already logged in
        if (currentUser != null) {
            // User is already logged in, navigate to the chat activity
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is not logged in, show the login screen
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
