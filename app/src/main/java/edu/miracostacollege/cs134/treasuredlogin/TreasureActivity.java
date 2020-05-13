package edu.miracostacollege.cs134.treasuredlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TreasureActivity extends AppCompatActivity {

    //DONE (1): Add Firebase member variables (auth and user)
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure);


        //DONE (2): Initialize Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //DONE (3): Initialize current user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        //DONE (4): Set the text view's text to "Welcome " + user's email address
        welcomeTextView.setText(getString(R.string.welcome_message, currentUser.getEmail()));
    }


    // DONE (5): Create a public void handleSignOut(View v) that signs out of Firebase authentication,
    // DONE (5): finishes this activity and starts a new Intent back to the LoginActivity.
    public void handleSignOut(View v)
    {
        mAuth.signOut();
        finish();
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
