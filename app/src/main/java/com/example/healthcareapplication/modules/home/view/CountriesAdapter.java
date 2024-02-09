package com.example.healthcareapplication.modules.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>{

    private Context context;
    private List<MealAreaList.MealArea> areas;
    private OnHomeClickListener listener;

    public CountriesAdapter(Context context, List<MealAreaList.MealArea> areas, OnHomeClickListener listener) {
        this.context = context;
        this.areas = areas;
        this.listener = listener;
    }
    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries_list_item, parent, false);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        MealAreaList.MealArea area = areas.get(position);
        holder.categoryTitle.setText(area.getArea());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAreaClick(area);
            }
        });

    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public class CountriesViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle;
        public CountriesViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.textViewFoodCategory);
        }
    }
}
