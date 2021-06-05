package com.example.crimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.crimeapp.RecyclerAdaptor.RecyclerHospitals;
import com.example.crimeapp.RecyclerAdaptor.RecyclerTablets;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewTablets extends AppCompatActivity {

    Cursor cur = null;
    RecyclerView newsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tablets);
        newsList=(RecyclerView) findViewById(R.id.newsList);
        try {
            databasequeries dd = new databasequeries(ViewTablets.this);

            cur = dd.getTablets();
            JSONArray array = new JSONArray();
            if(cur.moveToFirst()) {
                do {
                    JSONObject object = new JSONObject();
                    try {

                        object.put("ID", cur.getString(cur.getColumnIndex("ID")));
                        object.put("name", cur.getString(cur.getColumnIndex("name")));
                        object.put("descrp", cur.getString(cur.getColumnIndex("descr")));
                        object.put("medclr", cur.getString(cur.getColumnIndex("medclr")));
                        object.put("intake", cur.getString(cur.getColumnIndex("intake")));
                        object.put("medtime", cur.getString(cur.getColumnIndex("medtime")));
                        object.put("dosagetype", cur.getString(cur.getColumnIndex("dosagetype")));
                        object.put("dosage", cur.getString(cur.getColumnIndex("dosage")));
                        object.put("interval", cur.getString(cur.getColumnIndex("interval")));
                        object.put("balance", cur.getString(cur.getColumnIndex("balance")));
                        object.put("username", cur.getString(cur.getColumnIndex("username")));
                        array.put(object);
                    } catch (Exception ex) {

                    }
                    Log.e("Inside: ", "c");
                } while (cur.moveToNext());
            }
            RecyclerTablets recyclerNews = new RecyclerTablets(ViewTablets.this, array, ViewTablets.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewTablets.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newsList.getContext(),
                    linearLayoutManager.getOrientation());
            newsList.addItemDecoration(dividerItemDecoration);
            newsList.setLayoutManager(linearLayoutManager);
            newsList.setAdapter(recyclerNews);
        }
        catch (Exception e)
        {
            Log.e("1", e.toString());
            Toast.makeText(ViewTablets.this, "Err: "+e.toString(), Toast.LENGTH_LONG).show();

        }
    }
}