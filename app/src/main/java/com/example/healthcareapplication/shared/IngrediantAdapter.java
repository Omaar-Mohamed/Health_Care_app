package com.example.healthcareapplication.shared;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcareapplication.R;

import java.util.ArrayList;
import java.util.List;

public class IngrediantAdapter extends RecyclerView.Adapter<IngrediantAdapter.IngrediantViewHolder>{

private ArrayList<String> ingrediants;
private Context context;

    public IngrediantAdapter(ArrayList<String> ingrediants, Context context) {
        this.ingrediants = ingrediants;
        this.context = context;
    }
    @NonNull
    @Override
    public IngrediantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.incrediant_item, null);
        return new IngrediantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngrediantViewHolder holder, int position) {

        holder.ingrediantName.setText(ingrediants.get(position));

          Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+ingrediants.get(position)+".png").into(holder.ingrediantImage);
          //  Glide.with(context).load("http://www.themealdb.com/images/ingredients/"+ingrediants.get(position)+"-Small"+".png").into(holder.ingrediantImage);
    }

    @Override
    public int getItemCount() {
        return ingrediants.size();
    }

    public class IngrediantViewHolder extends RecyclerView.ViewHolder{
        TextView ingrediantName;
        ImageView ingrediantImage;
        public IngrediantViewHolder(@NonNull View itemView) {
            super(itemView);
            ingrediantName = itemView.findViewById(R.id.textViewIngrediantName);
            ingrediantImage = itemView.findViewById(R.id.imageViewIngrediant);

        }
    }
}
