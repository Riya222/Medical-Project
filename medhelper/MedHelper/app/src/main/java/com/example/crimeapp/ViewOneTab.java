package com.example.crimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ViewOneTab extends AppCompatActivity {

    String idval;
    private Button editTab;
    TextInputEditText name, descr, medtype, medclr, intake, medtime, dosage, interval, balance, dosagetype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_one_tab);

        name = (TextInputEditText) findViewById(R.id.name);
        descr = (TextInputEditText) findViewById(R.id.descr);
//        medtype = (TextInputEditText) findViewById(R.id.medtype);
        medclr = (TextInputEditText) findViewById(R.id.medclr);
        intake = (TextInputEditText) findViewById(R.id.intake);
        medtime = (TextInputEditText) findViewById(R.id.medtime);
        dosagetype = (TextInputEditText) findViewById(R.id.dosagetype);
        dosage = (TextInputEditText) findViewById(R.id.dosage);
        interval = (TextInputEditText) findViewById(R.id.interval);
        balance = (TextInputEditText) findViewById(R.id.balance);

        SharedPreferences sh
                = getSharedPreferences("user", MODE_PRIVATE);
        idval = sh.getString("id", "1");
        String nm = sh.getString("name", "Tab");
        String ds = sh.getString("descrp", "Tab");
        String mc = sh.getString("medclr", "Tab");
        String inta = sh.getString("intake", "Tab");
        String mt = sh.getString("medtime", "Tab");
        String dt = sh.getString("dosagetype", "Tab");
        String dos = sh.getString("dosage", "Tab");
        String intr = sh.getString("interval", "Tab");
        String bl = sh.getString("balance", "Tab");
        name.setText(nm);
        descr.setText(ds);
        medclr.setText(mc);
        intake.setText(inta);
        medtime.setText(mt);
        dosagetype.setText(dt);
        dosage.setText(dos);
        interval.setText(intr);
        balance.setText(bl);
        editTab = (Button) findViewById(R.id.addTab);
        editTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh
                        = getSharedPreferences("user", MODE_PRIVATE);
                String username = sh.getString("email", "vvv@gmail.com");
                databasequeries dd = new databasequeries(ViewOneTab.this);
                dd.edittabletInsert(idval, name.getText().toString(), descr.getText().toString(), "medtype.getText().toString()",
                        medclr.getText().toString(), intake.getText().toString(), medtime.getText().toString(), dosagetype.getText().toString(),
                        dosage.getText().toString(), interval.getText().toString(), balance.getText().toString(), username);
                Toast.makeText(getApplicationContext(), "Tablet Updated", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ViewOneTab.this, TabletActivity.class));
                finish();
            }
        });
    }
}