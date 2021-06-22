package com.example.crimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class showAlaramActivity extends AppCompatActivity {

    TextInputEditText rem, descr, dates;
    Button okay, cancel;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_alaram);
        rem = (TextInputEditText) findViewById(R.id.rem);
        descr = (TextInputEditText) findViewById(R.id.descr);
        dates = (TextInputEditText) findViewById(R.id.dates);
        img = (ImageView) findViewById(R.id.img);
        okay = (Button) findViewById(R.id.okay);
        cancel = (Button) findViewById(R.id.cancel);
        SharedPreferences sh
                = getSharedPreferences("reminder", MODE_PRIVATE);
        String rems = sh.getString("rem", "Reminder");
        databasequeries db = new databasequeries(showAlaramActivity.this);
        Cursor cr = db.getAlarms(rems);
        if(cr.moveToNext()){
            String remin = cr.getString(cr.getColumnIndex("rem"));
            String de = cr.getString(cr.getColumnIndex("descr"));
            String dt = cr.getString(cr.getColumnIndex("times"));
            String im = cr.getString(cr.getColumnIndex("img"));
            rem.setText(remin);
            descr.setText(de);
            dates.setText(dt);
            if(im.equals("")){

            } else{
                byte[] decodedString = Base64.decode(im, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                img.setImageBitmap(decodedByte);
            }
        }
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(showAlaramActivity.this, UserActivity.class));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh
                        = getSharedPreferences("user", MODE_PRIVATE);
                String user = sh.getString("email", "email");
                databasequeries b2 = new databasequeries(showAlaramActivity.this);
                Cursor c2 = b2.getnum(user);
                try {
                    c2.moveToFirst();
                    String phn = c2.getString(c2.getColumnIndex("mobile"));
                    Log.e("Num: ", phn);
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phn, null, "Patient has declined to take the medicine", null, null);
                    Log.e("Status: ", "SMS Sent");
                    startActivity(new Intent(showAlaramActivity.this, UserActivity.class));
                } catch(Exception ex){
                    Log.e("Ex: ", ex.toString());
                }
            }
        });
    }
}

