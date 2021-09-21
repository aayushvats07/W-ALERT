package com.example.walert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txt1, txt2, txt3, forgot_password;
    private EditText email_login , password_login;
    Button btn;

    private FirebaseAuth mAuth;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt3 = (TextView) findViewById(R.id.txt3);
        txt3.setOnClickListener(this);

        btn = findViewById(R.id.btn1);
        btn.setOnClickListener(this);

        email_login = (EditText) findViewById(R.id.email_login);
        password_login = (EditText) findViewById(R.id.password_login);

        pb = (ProgressBar) findViewById(R.id.pb);
        mAuth = FirebaseAuth.getInstance();

        forgot_password = (TextView) findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt3:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn1:
                userLogin();
                return;

            case R.id.forgot_password:
             startActivity(new Intent(this,ForgotPasswordActivity.class));
             break;
        }
    }

    private void userLogin() {

        String email = email_login.getText().toString().trim();
        String password = password_login.getText().toString().trim();

        if (email.isEmpty()){
            email_login.setError("Email is required");
            email_login.requestFocus();
            return;
        }
        if (password.isEmpty()){
            password_login.setError("Password is required");
            password_login.requestFocus();
            return;
        }
        if (password.length() < 6){
            password_login.setError("Password lenght should be min 6 characters");
            password_login.requestFocus();
            return;
        }

        pb.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this,"Check you email to verify you account", Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this,"Failed to login! Check your credentials", Toast.LENGTH_LONG).show();
                }
                pb.setVisibility(View.GONE);

            }
        });
    }
}