package com.example.healthcareapplication.modules.plan.view;

import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder>{

    private Context context;
    private Flowable<List<WeekPlan>> plans;

    private OnPlanClickListener listener;
    public PlanAdapter(Context context, Flowable<List<WeekPlan>> plans , OnPlanClickListener listener) {
        this.context = context;
        this.plans = plans;
            this.listener = listener;
    }


    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plan_item, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        WeekPlan plan = plans.blockingFirst().get(position);
        holder.mealName.setText(plan.getStrMeal());
        holder.mealType.setText(plan.getType());
        holder.mealDate.setText(plan.getDate().toString());
        Glide.with(context).load(plan.getStrMealThumb()).into(holder.mealImage);
       holder.itemView.setOnDragListener(new View.OnDragListener() {
           @Override
           public boolean onDrag(View view, DragEvent dragEvent) {
               return false;
           }

         });
       }


    @Override
    public int getItemCount() {
        return plans.blockingFirst().size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        TextView mealType;
        TextView mealDate;
        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.imageViewMeal);
            mealName = itemView.findViewById(R.id.textViewMealName);
            mealType = itemView.findViewById(R.id.textViewMealType);
            mealDate = itemView.findViewById(R.id.textViewMealDate);

        }
    }
}
