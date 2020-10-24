package com.pocketcreations.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TransferActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    TextView currBal,currUs;
    Button transfer,cancel;
    int curr;
    String currUser;
    EditText amount;
    Spinner dropdown;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        dropdown = (Spinner) findViewById(R.id.spinner);
        currBal = (TextView) findViewById(R.id.currBal);
        currUs = (TextView) findViewById(R.id.currUs);
        amount = (EditText) findViewById(R.id.amt);
        transfer = (Button) findViewById(R.id.tranferfin);
        cancel = (Button) findViewById(R.id.cancel);
        Intent intent=getIntent();
        final int id = intent.getIntExtra("id",0);
        this.myDB = new DatabaseHelper(this);
        String[] items = new String[9];
        int t=0;
        for(int i=1;i<=10;i++){
            if(i!=id){
                Cursor res = myDB.getTable1(i);
                while(res.moveToNext()){
                    items[t++]=res.getString(1);
                }
            }
        }
        Cursor res = myDB.getTable1(id);
        while(res.moveToNext()){
            curr=Integer.parseInt(res.getString(4));
            currUser=res.getString(1);
        }
        currUs.setText("Hi "+currUser);
        currBal.setText("Your Current Balance : $"+curr);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().length()==0){
                    Toast.makeText(TransferActivity.this, "Enter a valid amount.", Toast.LENGTH_SHORT).show();
                }else {
                    int transferAmount = Integer.parseInt(String.valueOf(amount.getText()));
                    String recipient = dropdown.getSelectedItem().toString();
                    if (transferAmount > 0 && transferAmount <= curr) {
                        process(transferAmount, currUser, recipient);
                        Toast.makeText(TransferActivity.this, "Transaction Successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TransferActivity.this, DetailsActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    } else {
                        Toast.makeText(TransferActivity.this, "You have low balance.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransferActivity.this,DetailsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    public void process(int amt,String nowUs,String nowRec){
        myDB.updateTable1(nowUs,curr-amt);
        int recBal = 0;
        Cursor res = myDB.balance("'"+nowRec+"'");
        while (res.moveToNext()){
            recBal=Integer.parseInt(res.getString(4));
        }
        myDB.updateTable1(nowRec,recBal+amt);
        myDB.insertTable2(nowUs,nowRec,amt);
    }
}