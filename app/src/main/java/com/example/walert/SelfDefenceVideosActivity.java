package com.example.walert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelfDefenceVideosActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView Mrecyclerview;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_defence_videos);
        toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Mrecyclerview = findViewById(R.id.recyclerview_video);
        Mrecyclerview.setHasFixedSize(true);
        Mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("self-defence video");
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
            Intent i = new Intent(SelfDefenceVideosActivity.this, AddContactActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.profile) {
            startActivity(new Intent(SelfDefenceVideosActivity.this,ProfileActivity.class));
            return true;
        } else if (id == R.id.about) {
            return true;
        }else if (id == R.id.logout_button){
            user_logout();
        }
        return true;
    }
    private void user_logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(SelfDefenceVideosActivity.this,LoginActivity.class));
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<member> options = new FirebaseRecyclerOptions.Builder<member>()
                .setQuery(reference, member.class)
                .build();
        FirebaseRecyclerAdapter<member, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<member, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull member model) {

                        holder.setVideo(getApplication(), model.getTitle(), model.getUrl());

                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.rowvideo, parent, false);


                        return new ViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        Mrecyclerview.setAdapter(firebaseRecyclerAdapter);


    }
}