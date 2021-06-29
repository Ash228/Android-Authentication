package com.example.mad1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class LoginActivity extends AppCompatActivity implements com.example.mad1.Base {
    private EditText email, password;
    private Button mButton, sButton;
    private String EmailId, Password;
    private Intent mIntent;
    int count=0;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        listener();
    }

        @Override
        public void init() {
            email= findViewById(R.id.email);
            password= findViewById(R.id.password);
            mButton=(Button)findViewById(R.id.loginButton);
            sButton=(Button)findViewById(R.id.login_to_signup);
        }

    @Override
    public void listener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < 3) {
                    EmailId = email.getText().toString();
                    Password = password.getText().toString();

                    Auth.getInstance().signInWithEmailAndPassword(EmailId, Password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();

                                // if sign-in is successful
                                // intent to home activity
                                Intent intent = new Intent(LoginActivity.this, com.example.mad1.LoggedActivity.class);
                                startActivity(intent);
                            }

                            else {

                                // sign-in failed
                                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                                count++;
                            }
                        }
                    });

                } else {
                    mButton.setEnabled(false);
                }
            }
        });
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        });
    }
}


