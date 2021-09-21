package com.example.walert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AboutActivity extends AppCompatActivity {

    TextView title, titleversion,titlecopyright;
    ImageView aboutlogo;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        title = (TextView)findViewById(R.id.title);
        titleversion = (TextView)findViewById(R.id.titleversion);
        titlecopyright = (TextView)findViewById(R.id.titlecopyright);

        aboutlogo = (ImageView) findViewById(R.id.aboutlogo);

        toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            Intent i = new Intent(AboutActivity.this, AddContactActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.profile) {
            startActivity(new Intent(AboutActivity.this,ProfileActivity.class));
            return true;
        } else if (id == R.id.about) {
            startActivity(new Intent(AboutActivity.this, AboutActivity.class));
            return true;
        } else if (id == R.id.logout_button){
            user_logout();
        }
        return true;
    }

    private void user_logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(AboutActivity.this,LoginActivity.class));
        finish();
    }
}