package edu.miracostacollege.cs134.treasuredlogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "TreasuredLogin";

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    //DONE (1): Add Firebase member variables (auth and user)
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        mEmailEditText = findViewById(R.id.emailEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);


        //DONE (2): Initialize Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // DONE (3): Get the current user.  If not null (already signed in), go directly to TreasureActivity
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            goToTreasure();
        }
    }

    // DONE (4): Create a private void goToTreasure() method that finishes this activity
    // DONE (4): then creates a new Intent to the TreasureActivity.class and starts the intent.
    private void goToTreasure()
    {
        finish();   // don't want user to continue to login
        Intent intent = new Intent(this, TreasureActivity.class);
        startActivity(intent);
    }


    // DONE (5): Create a private boolean isValidInput() method that checks to see whether
    // DONE (5): the email address or password is empty.  Return false if either is empty, true otherwise.
    // could expand to check NIST password requirements
    private boolean isValidInput()
    {
        if(TextUtils.isEmpty(mEmailEditText.getText()) || TextUtils.isEmpty(mPasswordEditText.getText())) {
            Toast.makeText(LoginActivity.this, "Invalid email or password.\nPlease try again.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }


    // DONE (6): Create a private void createAccount(String email, String password) method
    // DONE (6): that checks for valid input, then uses Firebase authentication to create the user with email and password.
    private void createAccount(String email, String password)
    {
        if(!isValidInput())
            return;

        // Create a new account in Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication is a success.",
                                    Toast.LENGTH_LONG).show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            goToTreasure();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


    // DONE (7): Create a private void signIn(String email, String password) method
    // DONE (7): that checks for valid input, then uses Firebase authentication to sign in user with email and password entered.
    private void signIn(String email, String password)
    {
        if(!isValidInput())
        {
            return;
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                goToTreasure();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }


    // DONE (8): Create a public void handleLoginButtons(View v) that checks the id of the button clicked.
    // DONE (8): If the button is createAccountButton, call the createAccount() method, else if it's signInButton, call the signIn() method.
    public void handleLoginButtons(View v)
    {
        switch(v.getId())
        {
            case R.id.createAccountButton:
                createAccount(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
                break;
            case R.id.signInButton:
                signIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
                break;
        }
    }
}
