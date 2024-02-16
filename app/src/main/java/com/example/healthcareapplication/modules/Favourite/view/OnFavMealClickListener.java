package com.example.healthcareapplication.modules.Favourite.view;

import com.example.healthcareapplication.model.dto.MealDetailDTO;

public interface OnFavMealClickListener {
    void onFavMealClick(MealDetailDTO.MealItem mealItem);
    void onFavMealDelete(MealDetailDTO.MealItem mealItem);
}
