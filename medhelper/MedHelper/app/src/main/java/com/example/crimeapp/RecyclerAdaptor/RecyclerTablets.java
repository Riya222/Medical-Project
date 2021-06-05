package com.example.crimeapp.RecyclerAdaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.crimeapp.ViewDoctors;
import com.example.crimeapp.ViewOneTab;
import com.example.crimeapp.ViewTablets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerTablets extends RecyclerView.Adapter<RecyclerTablets.MyViewHolder> {
    private static final String TAG = "RecyclerNews";
    private final Context context;
    private final JSONArray array;
    Activity act;



    public RecyclerTablets(Context applicationContext, JSONArray jsonArray, Activity a) {
        this.context = applicationContext;
        this.array = jsonArray;
        this.act = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabdes, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            JSONObject jsonObject = array.getJSONObject(position);
            holder.idv.setText(jsonObject.getString("ID"));
            holder.name.setText(jsonObject.getString("name"));
            holder.descrp.setText(jsonObject.getString("descrp"));
            holder.medclr.setText(jsonObject.getString("medclr"));
            holder.intake.setText(jsonObject.getString("intake"));
            holder.medtime.setText(jsonObject.getString("medtime"));
            holder.dosagetype.setText(jsonObject.getString("dosagetype"));
            holder.dosage.setText(jsonObject.getString("dosage"));
            holder.interval.setText(jsonObject.getString("interval"));
            holder.balance.setText(jsonObject.getString("balance"));
            holder.username.setText(jsonObject.getString("username"));
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
        TextView idv, name, descrp, medclr, intake, medtime, dosagetype, dosage, interval, balance, username;
        Button viewBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.itemImage);
            idv = (TextView) itemView.findViewById(R.id.idv);
            name = (TextView) itemView.findViewById(R.id.name);
            descrp = (TextView) itemView.findViewById(R.id.descr);
            medclr = (TextView) itemView.findViewById(R.id.medclr);
            intake = (TextView) itemView.findViewById(R.id.intake);
            medtime = (TextView) itemView.findViewById(R.id.medtime);
            dosagetype = (TextView) itemView.findViewById(R.id.dosagetype);
            dosage = (TextView) itemView.findViewById(R.id.dosage);
            interval = (TextView) itemView.findViewById(R.id.interval);
            balance = (TextView) itemView.findViewById(R.id.balance);
            username = (TextView) itemView.findViewById(R.id.username);
            viewBtn = (Button) itemView.findViewById(R.id.viewBtn);
            viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sp = act.getSharedPreferences("user",
                            MODE_PRIVATE);
                    SharedPreferences.Editor myEdit
                            = sp.edit();
                    myEdit.putString("id", idv.getText().toString());
                    myEdit.putString("name", name.getText().toString());
                    myEdit.putString("descrp", descrp.getText().toString());
                    myEdit.putString("medclr", medclr.getText().toString());
                    myEdit.putString("intake", intake.getText().toString());
                    myEdit.putString("medtime", medtime.getText().toString());
                    myEdit.putString("dosagetype", dosagetype.getText().toString());
                    myEdit.putString("dosage", dosage.getText().toString());
                    myEdit.putString("interval", interval.getText().toString());
                    myEdit.putString("balance", balance.getText().toString());
                    myEdit.putString("username", username.getText().toString());
                    myEdit.commit();
                    act.startActivity(new Intent(act, ViewOneTab.class));
                }
            });
        }
    }

}
