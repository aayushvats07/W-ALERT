package com.example.walert;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class EmergencyContactsHolder extends RecyclerView.ViewHolder {
    View view;
    Button call;

    public EmergencyContactsHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        call = (Button) view.findViewById(R.id.call);
    }


    public void setView(Context context, String name, String mobile_no){
        TextView tvname, tvmobile_no;
        tvname = view.findViewById(R.id.name_tv);
        tvmobile_no = view.findViewById(R.id.mobile_tv);


        tvname.setText(name);
        tvmobile_no.setText(mobile_no);



    }
}
