package com.example.mad1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements com.example.mad1.Base {
    private EditText email, password;
    private Button signbtn,loginbtn;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    private Intent mIntent;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener();
    }

    @Override
    public void init() {
        mIntent=new Intent();
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        signbtn=(Button)findViewById(R.id.signupbtn);
        loginbtn=(Button)findViewById(R.id.loginbtn);
    }

    @Override
    public void listener() {
        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmailId =email.getText().toString();
                String Password = password.getText().toString();
                if(TextUtils.isEmpty(EmailId) || TextUtils.isEmpty(Password)) {
                    Toast.makeText(MainActivity.this, "Enter All fields", Toast.LENGTH_SHORT).show();
                } else {
                    if(PASSWORD_PATTERN.matcher(Password).matches()) {
                        Auth.getInstance().createUserWithEmailAndPassword(EmailId,Password)
                                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                    mIntent.setClass(MainActivity.this, com.example.mad1.LoginActivity.class);
                                    startActivity(mIntent);
                                } else {
                                    // Registration failed
                                    Toast.makeText(getApplicationContext(), "Registration failed!!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        password.setError("Invalid Password Pattern");
                    }
                }
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent.setClass(MainActivity.this, com.example.mad1.LoginActivity.class);
                startActivity(mIntent);
        }
    });
    }

}