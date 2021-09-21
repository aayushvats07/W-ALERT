package com.example.walert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference databaseReference;
    Toolbar toolbar;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView emailid = (TextView) findViewById(R.id.emailid);
        final TextView nameProfile = (TextView) findViewById(R.id.nameProfile);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null){
                    String email = userprofile.email;
                    String name = userprofile.name;

                    emailid.setText(email);
                    nameProfile.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Something wrong happened",Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_contact) {
            Intent i = new Intent(ProfileActivity.this, AddContactActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.profile) {
            startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
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
        startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        finish();
    }

}

