package com.example.walert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmergencyContactsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Contacts");


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<EmergencyContactsData> options = new FirebaseRecyclerOptions.Builder<EmergencyContactsData>()
                .setQuery(databaseReference, EmergencyContactsData.class)
                .build();
        FirebaseRecyclerAdapter<EmergencyContactsData, EmergencyContactsHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<EmergencyContactsData, EmergencyContactsHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull EmergencyContactsHolder holder, int position, @NonNull EmergencyContactsData model) {

                        holder.setView(getApplication(), model.getName(), model.getMobile_no());
                        holder.call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String mobileNo  = model.getMobile_no();
                                String call = "tel:" +mobileNo.trim();
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse(call));
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public EmergencyContactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.emergency_contact_data, parent, false);

                        return new EmergencyContactsHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
