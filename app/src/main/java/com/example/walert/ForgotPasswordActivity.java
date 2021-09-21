package com.example.walert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText reset_email;
    private Button reset_btn;
    private ProgressBar pbreset;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        reset_email = (EditText) findViewById(R.id.reset_email);
        reset_btn = (Button) findViewById(R.id.reset_btn);
        pbreset = (ProgressBar) findViewById(R.id.pbreset);

        auth = FirebaseAuth.getInstance();

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


    }
    private void resetPassword(){

        String email = reset_email.getText().toString().trim();

        if (email.isEmpty()){
            reset_email.setError("Emil is required");
            reset_email.requestFocus();
            return;
        }
        pbreset.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"Check your email to reset your password", Toast.LENGTH_LONG).show();
                    pbreset.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(ForgotPasswordActivity.this,"Try again", Toast.LENGTH_LONG).show();
                    pbreset.setVisibility(View.GONE);
                }
            }
        });
    }
}