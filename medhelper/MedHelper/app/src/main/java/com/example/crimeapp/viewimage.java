package com.example.crimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.crimeapp.RecyclerAdaptor.RecyclerImages;

import org.json.JSONArray;
import org.json.JSONObject;

public class viewimage extends AppCompatActivity {
    RecyclerView rec1;
    Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimage);
        rec1 = (RecyclerView) findViewById(R.id.recyimg);
        try {
            SharedPreferences sh
                    = getSharedPreferences("user", MODE_PRIVATE);
            String username = sh.getString("email", "vvv@gmail.com");
            databasequeries dd = new databasequeries(viewimage.this);
            cur = dd.getimages(username);
            JSONArray array = new JSONArray();
            while (cur.moveToNext()) {
                JSONObject object = new JSONObject();
                try {
                    object.put("img", cur.getString(cur.getColumnIndex("img")));
                    object.put("dtm", cur.getString(cur.getColumnIndex("dtm")));

                    array.put(object);
                } catch (Exception ex) {
                }
                Log.e("Inside: ", "c");
            }
            RecyclerImages recyclerNews = new RecyclerImages(viewimage.this, array, viewimage.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(viewimage.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rec1.getContext(),
                    linearLayoutManager.getOrientation());
            rec1.addItemDecoration(dividerItemDecoration);
            rec1.setLayoutManager(linearLayoutManager);
            rec1.setAdapter(recyclerNews);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}