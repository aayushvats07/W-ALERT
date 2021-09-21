package com.example.walert;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.transition.Hold;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.Tag;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int VIDEO_REQUEST = 101;
    private Uri videouri = null;
    Button btn6;
    ImageView imgbtn5, imgbtn2,imgbtn1;
    Toolbar toolbar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        imgbtn5 = (ImageView) findViewById(R.id.imgbtn5);
        imgbtn5.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SelfDefenceVideosActivity.class)));


        btn6 = (Button)findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
              startActivity(new Intent(HomeActivity.this, MapActivity.class));

          }
        });


        imgbtn2 = (ImageView) findViewById(R.id.imgbtn2);
        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EmergencyContactsActivity.class));
            }
        });

        imgbtn1 = (ImageView)findViewById(R.id.imgbtn1);
        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneno = "9561717950";
                String call = "tel:" +phoneno.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(call));
                startActivity(intent);
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
            Intent i = new Intent(HomeActivity.this, AddContactActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.profile) {
            startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            return true;
        } else if (id == R.id.about) {
            startActivity(new Intent(HomeActivity.this,AboutActivity.class));
            return true;
        } else if (id == R.id.logout_button){
            user_logout();
        }
        return true;
    }

    private void user_logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        finish();
    }

    public void takepicture(View view) {
        Intent imageTake = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (imageTake.resolveActivity(getPackageManager())!=null){
            startActivityForResult(imageTake,REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            videouri = data.getData();
        }
    }


    public void videocapture(View view) {

        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(videoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(videoIntent,VIDEO_REQUEST);
        }

        Intent playIntent = new Intent(this, PlayVideoActivity.class);
        playIntent.putExtra("videoUri", videouri.toString());
        startActivity(playIntent);
    }


}