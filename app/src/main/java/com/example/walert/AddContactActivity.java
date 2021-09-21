package com.example.walert;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddContactActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int maxid=0;
    Toolbar toolbar;
    EditText edt9, edt10;
    Button btn7;
    Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt9 = (EditText)findViewById(R.id.edt9);
        edt10 = (EditText)findViewById(R.id.edt10);
        btn7 = (Button) findViewById(R.id.btn7);

        contacts = new Contacts();
        databaseReference = firebaseDatabase.getInstance().getReference().child("Contacts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    maxid = (int)snapshot.getChildrenCount();
                }
                else
                {
                    System.out.println("Invalid");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts.setName(edt9.getText().toString());
                contacts.setMobile_no(edt10.getText().toString());
                databaseReference.child(String.valueOf(maxid+1)).setValue(contacts);

                Toast.makeText(AddContactActivity.this,"Sucessfully Saved", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AddContactActivity.this, EmergencyContactsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_contact) {
            Intent i = new Intent(AddContactActivity.this, AddContactActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.profile) {
            startActivity(new Intent(AddContactActivity.this,ProfileActivity.class));
            return true;
        } else if (id == R.id.about) {
            return true;
        } else if (id == R.id.logout_button){
            user_logout();
        }
        return true;
    }
    private void user_logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(AddContactActivity.this,LoginActivity.class));
        finish();
    }
}
