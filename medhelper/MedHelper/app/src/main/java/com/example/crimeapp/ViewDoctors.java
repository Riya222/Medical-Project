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

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewDoctors extends AppCompatActivity {

    Cursor cur;
    RecyclerView newsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctors);
        newsList=(RecyclerView) findViewById(R.id.newsList);
        try {
            databasequeries dd = new databasequeries(ViewDoctors.this);

            cur = dd.getDoctors();
            JSONArray array = new JSONArray();
            while (cur.moveToNext()) {
                JSONObject object = new JSONObject();
                try{
                    object.put("email", cur.getString(cur.getColumnIndex("email")));
                    object.put("mobile", cur.getString(cur.getColumnIndex("mobile")));
                    object.put("password", cur.getString(cur.getColumnIndex("password")));
                    object.put("hosp", cur.getString(cur.getColumnIndex("hosp")));
                    object.put("desig", cur.getString(cur.getColumnIndex("desig")));
                    array.put(object);
                }
                catch (Exception ex){

                }
                Log.e("Inside: ", "c");
            }
            RecyclerHospitals recyclerNews = new RecyclerHospitals(ViewDoctors.this, array, ViewDoctors.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewDoctors.this);
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
            Toast.makeText(ViewDoctors.this, "Err: "+e.toString(), Toast.LENGTH_LONG).show();

        }
    }
}