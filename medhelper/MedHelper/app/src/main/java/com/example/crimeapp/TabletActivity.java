package com.example.crimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class TabletActivity extends AppCompatActivity {


    private Button addTab;
    TextInputEditText name, descr, medtype, medclr, intake, medtime, dosage, interval, balance;
    Spinner dosagetype;
    TextView tbview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        tbview = (TextView) findViewById(R.id.tbview);
        tbview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TabletActivity.this, ViewTablets.class));
            }
        });
        name = (TextInputEditText) findViewById(R.id.name);
        descr = (TextInputEditText) findViewById(R.id.descr);
//        medtype = (TextInputEditText) findViewById(R.id.medtype);
        medclr = (TextInputEditText) findViewById(R.id.medclr);
        intake = (TextInputEditText) findViewById(R.id.intake);
        medtime = (TextInputEditText) findViewById(R.id.medtime);
        dosagetype = (Spinner) findViewById(R.id.dosagetype);
        dosage = (TextInputEditText) findViewById(R.id.dosage);
        interval = (TextInputEditText) findViewById(R.id.interval);
        balance = (TextInputEditText) findViewById(R.id.balance);

        addTab = (Button) findViewById(R.id.addTab);
        addTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh
                        = getSharedPreferences("user", MODE_PRIVATE);
                String username = sh.getString("email", "vvv@gmail.com");
                databasequeries dd = new databasequeries(TabletActivity.this);
                dd.tabletInsert(name.getText().toString(), descr.getText().toString(), "medtype.getText().toString()",
                        medclr.getText().toString(), intake.getText().toString(), medtime.getText().toString(), dosagetype.getSelectedItem().toString(),
                        dosage.getText().toString(), interval.getText().toString(), balance.getText().toString(), username);
                Toast.makeText(getApplicationContext(), "Tablet Added", Toast.LENGTH_LONG).show();
                startActivity(new Intent(TabletActivity.this, TabletActivity.class));
                finish();
            }
        });
    }
}