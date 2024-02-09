package com.example.healthcareapplication.modules.home.view;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;

public interface OnHomeClickListener {
    void onCategoryClick(MealCategoryList.MealCategory mealCategory);

    void onAreaClick(MealAreaList.MealArea mealCategory);
}
