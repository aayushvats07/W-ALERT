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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txt4, txt5, txt6, txt7, txt_name;
    private EditText email, password, confirm_password, name;
    Button btn2;
    private ProgressBar pb1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        txt6 = (TextView) findViewById(R.id.txt6);
        txt6.setOnClickListener(this);

        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        name = (EditText) findViewById(R.id.name);

        pb1 = (ProgressBar) findViewById(R.id.pb1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt6:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.btn2:
                btn2();
                break;

        }
    }

    private void btn2() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String ConfirmPassword = confirm_password.getText().toString().trim();
        String Name = name.getText().toString().trim();

        if (Email.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (Password.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (Password.length() < 6){
            password.setError("Password lenght should be min 6 characters");
            password.requestFocus();
            return;
        }
        if (ConfirmPassword.isEmpty()){
            confirm_password.setError("Confirm password is required");
            confirm_password.requestFocus();
            return;
        }
        if (Name.isEmpty()){
            name.setError("Name is required");
            name.requestFocus();
            return;
        }


        pb1.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new  OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    User user = new User(Email,Password,ConfirmPassword,Name);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Failed to registered", Toast.LENGTH_LONG).show();
                            }
                            pb1.setVisibility(View.GONE);
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "Failed to registered", Toast.LENGTH_LONG).show();
                    pb1.setVisibility(View.GONE);
                }
            }
        });

    }
}