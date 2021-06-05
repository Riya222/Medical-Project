package com.example.crimeapp.RecyclerAdaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.crimeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RecyclerHospitals extends RecyclerView.Adapter<RecyclerHospitals.MyViewHolder> {
    private static final String TAG = "RecyclerNews";
    private final Context context;
    private final JSONArray array;
    Activity act;



    public RecyclerHospitals(Context applicationContext, JSONArray jsonArray, Activity a) {
        this.context = applicationContext;
        this.array = jsonArray;
        this.act = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.choosedr, null);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            JSONObject jsonObject = array.getJSONObject(position);
            holder.newsHead.setText(jsonObject.getString("email"));
            holder.idv.setText(jsonObject.getString("desig"));
            holder.usrn.setText(jsonObject.getString("mobile"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       // ImageView image;
        TextView newsHead, idv, usrn,dateTime;
        Button apr, btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.itemImage);
            newsHead = (TextView) itemView.findViewById(R.id.name);
            idv = (TextView) itemView.findViewById(R.id.idval);
            usrn = (TextView) itemView.findViewById(R.id.usrn);
            newsHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    act.startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + usrn.getText().toString())));
                    Toast.makeText(context, newsHead.getText().toString()+" Doctor", Toast.LENGTH_LONG).show();
                }
            });
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Toast.makeText(context, newsHead.getText().toString(), Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }

}
