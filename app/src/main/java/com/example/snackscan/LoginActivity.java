package com.example.snackscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginButton;
    TextView mRegisterButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.editTextEmailAddressLogin);
        mPassword = findViewById(R.id.editTextPasswordLogin);
        mLoginButton = findViewById(R.id.createLoginButton);
        mRegisterButton = findViewById(R.id.registerChoiceText);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.loginProgressBar);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email Address Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password Required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password must be >=6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                // Authenticate the user using Firebase

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Determine if the login is successful or not
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }

        });
    }
}