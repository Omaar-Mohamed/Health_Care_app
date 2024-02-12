package com.example.healthcareapplication.modules.meals.view;

import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;

import java.util.List;

public interface MealsIview {

     void ShowError(String error);
    void showMeals(List<MealListDto.MealListItemDto> meals);

    void showMealDetail(List<MealDetailDTO.MealItem> meal);
}
