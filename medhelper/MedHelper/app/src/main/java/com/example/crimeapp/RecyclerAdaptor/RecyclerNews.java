package com.example.crimeapp.RecyclerAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RecyclerNews extends RecyclerView.Adapter<RecyclerNews.MyViewHolder> {
    private static final String TAG = "RecyclerNews";
    private final Context context;
    private final JSONArray array;



    public RecyclerNews(Context applicationContext, JSONArray jsonArray) {
        this.context = applicationContext;
        this.array = jsonArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkupres, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            JSONObject jsonObject = array.getJSONObject(position);
            holder.name.setText(jsonObject.getString("user"));
            holder.prev.setText(jsonObject.getString("prev"));
            holder.res.setText(jsonObject.getString("check"));
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
        TextView name, prev, res;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.itemImage);
            name = (TextView) itemView.findViewById(R.id.name);
            prev = (TextView) itemView.findViewById(R.id.prev);
            res = (TextView) itemView.findViewById(R.id.res);
        }
    }

}
