package com.example.crimeapp.RecyclerAdaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RecyclerImages extends RecyclerView.Adapter<RecyclerImages.MyViewHolder> {
    private static final String TAG = "RecyclerNews";
    private final Context context;
    private final JSONArray array;
    Activity act;



    public RecyclerImages(Context applicationContext, JSONArray jsonArray, Activity a) {
        this.context = applicationContext;
        this.array = jsonArray;
        this.act = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imview, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            JSONObject jsonObject = array.getJSONObject(position);

            holder.dte.setText(jsonObject.getString("dtm"));
            String encodedImage = jsonObject.getString("img");
            if(encodedImage.equals("")){

            }
            else{
                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                holder.imgvi.setImageBitmap(decodedByte);
            }
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
        TextView dte;
        ImageView imgvi;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.itemImage);
            dte = (TextView) itemView.findViewById(R.id.dates);
            imgvi=(ImageView)itemView.findViewById(R.id.imgv);

//            });
        }
    }

}
