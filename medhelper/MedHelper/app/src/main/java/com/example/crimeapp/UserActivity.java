package com.example.crimeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    Button ch, doc, tab, pic, rem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ch = (Button) findViewById(R.id.checkup);
        doc = (Button) findViewById(R.id.doc);
        tab = (Button) findViewById(R.id.tab);
        pic = (Button) findViewById(R.id.pic);
        rem = (Button) findViewById(R.id.rem);

        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, CheckupActivity.class));
            }
        });
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, ViewDoctors.class));
            }
        });
        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, TabletActivity.class));
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, SavePicActivity.class));
            }
        });
        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, ReminderActivity.class));
            }
        });
    }
}