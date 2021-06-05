package com.example.crimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReminderActivity extends AppCompatActivity {

    private Button btnAdd;
    TextInputEditText rem, descr, times, desig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        rem = (TextInputEditText) findViewById(R.id.rem);
        descr = (TextInputEditText) findViewById(R.id.descr);
        times = (TextInputEditText) findViewById(R.id.times);

        btnAdd= (Button) findViewById(R.id.addRem);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlert(times.getText().toString());
                databasequeries dd = new databasequeries(ReminderActivity.this);
                dd.reminderInsert(rem.getText().toString(), descr.getText().toString(), times.getText().toString());
                Toast.makeText(getApplicationContext(), "Reminder Added", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ReminderActivity.this, ReminderActivity.class));
                finish();
            }
        });
    }
    public void startAlert(String d){
        try {
            String tm = times.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String dateString;
            long aa = 0;
            String[] ll = tm.split(":");
            if(ll.length==2) {
                dateString = tm + ":00";
            }
            else{
                dateString = tm;
            }
            Log.e("datesrting",dateString+"");
            try{
                //formatting the dateString to convert it into a Date
                Date date = sdf.parse(dateString);

                Calendar calendar = Calendar.getInstance();
                //Setting the Calendar date and time to the given date and time
                calendar.setTime(date);
                aa = calendar.getTimeInMillis();
            }catch(ParseException e){
                e.printStackTrace();
            }

            Intent intent = new Intent(this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (2*1000 ), pendingIntent);
            Toast.makeText(this, "Alarm set ", Toast.LENGTH_LONG).show();
            Log.e("Alarm: ","Set "+aa/10000);
        }
        catch(Exception ex){
            Log.e("Error: ",ex+"");
        }
    }
}