package com.pocketcreations.bankapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {

    TextView name,location,amount;
    CircleImageView profilePic;
    Button transfer,home;
    DatabaseHelper myDB;
    Integer amt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name = (TextView) findViewById(R.id.userName);
        location = (TextView) findViewById(R.id.location);
        amount = (TextView) findViewById(R.id.amount);
        profilePic = (CircleImageView) findViewById(R.id.userPhoto);
        transfer = (Button) findViewById(R.id.transferBTN);
        home = (Button) findViewById(R.id.homeBTN);
        Intent intent=getIntent();
        final Integer id = intent.getIntExtra("id",0);
        this.myDB = new DatabaseHelper(this);
        Cursor res = myDB.getTable1(id);
        while(res.moveToNext()) {
            name.setText(res.getString(1));
            location.setText(res.getString(2));
            profilePic.setImageResource(Integer.parseInt(res.getString(3)));
            amt = Integer.valueOf(res.getString(4));
            amount.setText("$"+amt);
        }
        res.close();
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this,TransferActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}