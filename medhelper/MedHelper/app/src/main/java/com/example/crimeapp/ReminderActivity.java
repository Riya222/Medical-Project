package com.example.crimeapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReminderActivity extends AppCompatActivity {

    private Button btnAdd;
    TextInputEditText rem, descr, times, dates;
    TextToSpeech t1;
    Button btnimg;
    byte[] by;
    private String encodedImage="";
    public static final int PICK_IMAGE = 1;
    private static final int SELECT_PICTURE = 100;
    ImageView i;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        i = (ImageView) findViewById(R.id.imm);
        rem = (TextInputEditText) findViewById(R.id.rem);
        descr = (TextInputEditText) findViewById(R.id.descr);
        times = (TextInputEditText) findViewById(R.id.times);
        dates = (TextInputEditText) findViewById(R.id.dates);
        btnimg = (Button) findViewById(R.id.btnimg);
        btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        btnAdd= (Button) findViewById(R.id.addRem);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("reminder",
                        MODE_PRIVATE);
                SharedPreferences.Editor myEdit
                        = sp.edit();
                myEdit.putString("rem", rem.getText().toString());
                myEdit.commit();
                startAlert(times.getText().toString());
                databasequeries dd = new databasequeries(ReminderActivity.this);
                dd.reminderInsert(rem.getText().toString(), descr.getText().toString(), dates.getText().toString()+" "+times.getText().toString(), encodedImage);
                Toast.makeText(getApplicationContext(), "Reminder Added", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ReminderActivity.this, ReminderActivity.class));
                finish();
            }
        });
    }
    public void startAlert(String d){
        try {
            String tm = times.getText().toString();
            String dt = dates.getText().toString();
            String remn = rem.getText().toString();
            String dsc = descr.getText().toString();
            Log.e("dt : ", dt+" "+tm);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String start_date = formatter.format(date);
            String end_date = dt+" "+tm;
            long diff = findDifference(start_date, end_date);
            Log.e("Difference: ", diff+"");
            Intent intent = new Intent(this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (diff*1000 ), pendingIntent);
            Toast.makeText(this, "Alarm set ", Toast.LENGTH_LONG).show();
            Log.e("Alarm: ","Set "+diff*1000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    t1.speak(descr.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    Intent i=new Intent(ReminderActivity.this,showAlaramActivity.class);
                    startActivity(i);
                }
            }, diff*1000);
        }
        catch(Exception ex){
            Log.e("Error: ",ex+"");
        }
    }
    static long findDifference(String start_date, String end_date)
    {

        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss");

        // Try Block
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            // Calucalte time difference
            // in milliseconds
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            // Calucalte time difference in
            // seconds, minutes, hours, years,
            // and days
            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000)
                    % 60;

            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;

            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60))
                    % 24;

            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;

            // Print the date difference in
            // years, in days, in hours, in
            // minutes, and in seconds

            Log.e("Difference is: ",difference_In_Years +" years, " + difference_In_Days + " days, " + difference_In_Hours + " hours, " + difference_In_Minutes + " minutes, " + difference_In_Seconds + " seconds");
            return (difference_In_Years*60)+(difference_In_Days*60)+(difference_In_Hours*60)+(difference_In_Minutes*60)+difference_In_Seconds;
        }

        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//                Uri selectedImage = data.getData();


            Bitmap photo = (Bitmap) data.getExtras().get("data");
            i.setImageBitmap(photo);
            i.setVisibility(View.VISIBLE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            by = baos.toByteArray();
            encodedImage = Base64.encodeToString(by, Base64.DEFAULT);
            Log.e("TAG", encodedImage);
            /* if(Build.VERSION.SDK_INT >= 11*//*HONEYCOMB*//*) {
                    new sendData().execute();
                } else {
                    new sendData().execute();
                }*/
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                final Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("TAG", "Image Path : " + path);
//                // Set the image in ImageView
//                i.setImageBitmap(photo);

                    i.post(new Runnable() {
                        @Override
                        public void run() {
//                        ((ImageView) i).setImageURI(selectedImageUri);
                            Bitmap photo=null;
                            try {
                                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//                        i.setVisibility(View.VISIBLE);
//                        Bitmap photo = (Bitmap) data.getExtras().get(selectedImageUri.toString());
                            i.setImageBitmap(photo);
                            i.setVisibility(View.VISIBLE);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                            by = baos.toByteArray();
                            encodedImage = Base64.encodeToString(by, Base64.DEFAULT);
                            Log.e("TAG", encodedImage);
                        }
                    });

                }
            }
        }


    }


}