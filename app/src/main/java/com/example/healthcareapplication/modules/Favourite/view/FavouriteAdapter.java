package com.example.healthcareapplication.modules.Favourite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;
import com.example.healthcareapplication.modules.meals.view.OnMealClickListener;

import org.w3c.dom.Text;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>{

    private Context context;
    private Flowable<List<MealDetailDTO.MealItem>> meals;
    private OnFavMealClickListener listener;

    public FavouriteAdapter(Context context, Flowable<List<MealDetailDTO.MealItem>> meals, OnFavMealClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_meal_item, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {

        MealDetailDTO.MealItem meal = meals.blockingFirst().get(position);
        holder.categoryTitle.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.image);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFavMealDelete(meal);
            }
        });

        holder.addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onFavMealAddPlan(meal);
            }
        });

    }

    @Override
    public int getItemCount() {
        return meals.blockingFirst().size();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle;
        ImageView image;

        Button delete;
        Button addPlan;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.FavMealNameTextView);
            image = itemView.findViewById(R.id.favMealImageView);
            delete = itemView.findViewById(R.id.removeFromFavButton);
            addPlan = itemView.findViewById(R.id.addPlanButton);
        }
    }
}
