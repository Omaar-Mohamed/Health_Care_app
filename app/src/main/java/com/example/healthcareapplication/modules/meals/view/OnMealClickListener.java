package com.example.healthcareapplication.modules.meals.view;

import com.example.healthcareapplication.model.dto.MealListDto;

public interface OnMealClickListener {

    void onMealClick(MealListDto.MealListItemDto meal);
}
