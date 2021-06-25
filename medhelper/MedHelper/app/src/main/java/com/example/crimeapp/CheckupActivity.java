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

public class CheckupActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    Button b,b1,b2,b3;
    EditText t;
    byte[] by;
    String username;
    private String encodedImage="";
    public static final int PICK_IMAGE = 1;
    private static final int SELECT_PICTURE = 100;
    ImageView i;
    RecyclerView newsList;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkup);
        SharedPreferences sh
                = getSharedPreferences("user", MODE_PRIVATE);
        username = sh.getString("email", "vvv@gmail.com");
        newsList=(RecyclerView) findViewById(R.id.newsList);
        t=(EditText) findViewById(R.id.editText);
        b=(Button) findViewById(R.id.button);
        b.setOnClickListener(this);
        b2=(Button) findViewById(R.id.button3);
        b2.setOnClickListener(this);
        i=(ImageView) findViewById(R.id.imageView);
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

            case R.id.button3:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
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
            Log.e(TAG, encodedImage);
            sendData(encodedImage);
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
                            sendData(encodedImage);
                            Log.e("select","selected image");
                        }
                    });

                }
            }
        }


    }


    public void sendData(final String incomingImageString)
    {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        Log.e("function","image");
        StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.1.3:8000/getimage/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("Response is: ", response.toString());
                try {
                    JSONObject object = new JSONObject(response.trim());
                    JSONArray jsonArray = object.getJSONArray("data");
                    RecyclerNews recyclerNews = new RecyclerNews(getApplicationContext(), jsonArray);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newsList.getContext(),
                            linearLayoutManager.getOrientation());
                    newsList.addItemDecoration(dividerItemDecoration);
                    newsList.setLayoutManager(linearLayoutManager);
                    newsList.setAdapter(recyclerNews);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG,error.getMessage());
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> m=new HashMap<>();
                m.put("user",username);
                m.put("uploaddf",incomingImageString);
                return m;
            }
        };
        requestQueue.add(requ);
    }

    public void sendData1(final String incomingImageString)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.1.3:8000/get_recipe/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response is: ", response.toString());
                try {
                    JSONObject object = new JSONObject(response.trim());
                    JSONArray jsonArray = object.getJSONArray("data");
                    RecyclerNews recyclerNews = new RecyclerNews(getApplicationContext(), jsonArray);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newsList.getContext(),
                            linearLayoutManager.getOrientation());
                    newsList.addItemDecoration(dividerItemDecoration);
                    newsList.setLayoutManager(linearLayoutManager);
                    newsList.setAdapter(recyclerNews);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG,error.getMessage());
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> m=new HashMap<>();
                m.put("user",username);
                m.put("uploaddf",incomingImageString);
                return m;
            }
        };
        requestQueue.add(requ);
    }
   /* public class sendData extends AsyncTask<Void, Void, String> {

        String MYURL = "http://192.168.1.2:8000/accept/";
        String resposnse="";
        int tmp;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }


        @Override
        protected String doInBackground(Void... requestParams) {
            resposnse = "";
            try {
                URL url = new URL(MYURL);
                String sd="uploaddf="+encodedImage;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(sd.getBytes());
                outputStream.flush();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                while ((tmp = inputStream.read()) != -1) {

                    resposnse += (char) tmp;

                }
                inputStream.close();
                httpURLConnection.disconnect();
              //  System.out.print("hai");

            } catch (Exception e) {

               e.printStackTrace();
            }
            return resposnse.trim();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
           *//* Toast.makeText(getApplicationContext(),encodedImage+"",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),s+"",Toast.LENGTH_SHORT).show();*//*
        }
    }*/
}
