package com.example.healthcareapplication.modules.meals.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.dto.MealListDto;

import java.util.List;

public class MealsGridAdapter extends RecyclerView.Adapter<MealsGridAdapter.MealsViewHolder>{

private Context context;
private List<MealListDto.MealListItemDto> meals;
private OnMealClickListener listener;
RecyclerView recyclerView;

    public MealsGridAdapter(Context context, List<MealListDto.MealListItemDto> meals, OnMealClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }
    @NonNull
    @Override
    public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_card_item, parent, false);


        return new MealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsViewHolder holder, int position) {
        MealListDto.MealListItemDto meal = meals.get(position);
        holder.categoryTitle.setText(meal.getMealName());
        Glide.with(context).load(meal.getMealThumb()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMealClick(meal);
            }
        });

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MealsViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle;
        ImageView image;
        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.textViewFoodCategory);
            image = itemView.findViewById(R.id.imageViewFood);
        }
    }
}
