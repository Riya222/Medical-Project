package com.example.crimeapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crimeapp.RecyclerAdaptor.RecyclerNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class SavePicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    Button b;
    TextView b1;
    EditText t;
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
        setContentView(R.layout.activity_save_pic);
        b=(Button) findViewById(R.id.button);
        b.setOnClickListener(this);
        i=(ImageView) findViewById(R.id.imageView);
        b1=(TextView)findViewById(R.id.vw);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(SavePicActivity.this, viewimage.class);
                startActivity(I);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.button:

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                break;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//                Uri selectedImage = data.getData();


                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String dtm=dtf.format(now);
                    Log.e("dtm",dtm+"");



            Bitmap photo = (Bitmap) data.getExtras().get("data");
            i.setImageBitmap(photo);
            i.setVisibility(View.VISIBLE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            by = baos.toByteArray();
            encodedImage = Base64.encodeToString(by, Base64.DEFAULT);
            Log.e(TAG, encodedImage);
            SharedPreferences sh
                    = getSharedPreferences("user", MODE_PRIVATE);
            String username = sh.getString("email", "vvv@gmail.com");
            databasequeries dd = new databasequeries(SavePicActivity.this);
            dd.saveImage(username, encodedImage,dtm);
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
                    Log.i(TAG, "Image Path : " + path);
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
                            Log.e(TAG, encodedImage);
                        }
                    });

                }
            }
        }


    }
}
