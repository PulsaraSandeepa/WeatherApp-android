package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button rstPwdButton;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = (EditText) findViewById(R.id.emailRST);
        rstPwdButton = (Button) findViewById(R.id.resetPwd);


        auth = FirebaseAuth.getInstance();

rstPwdButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        resetPassword();
        
    }

    private void resetPassword() {
        final String email =  emailEditText.getText().toString().trim();

        if (email.isEmpty()) {
             emailEditText.setError("Email is required");
             emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
             emailEditText.setError("Please provide a valid email");
             emailEditText.requestFocus();
            return;
        }
auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
            Toast.makeText(ForgotPassword.this,"Check your email to reset the password",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(ForgotPassword.this,"Try again! Something went Wrong.",Toast.LENGTH_LONG).show();

        }
    }
});


    }
});

    }
}
