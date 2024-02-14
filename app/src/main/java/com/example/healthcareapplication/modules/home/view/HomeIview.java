package com.example.healthcareapplication.modules.home.view;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;

import java.util.List;

public interface HomeIview {

    void showCategories(List<MealCategoryList.MealCategory> categories);

    void showError(String error);

    void showRandomMeal(List<MealDTO.Meal> meal);
    void showRandomMealError(String error);

    void showAreas(List<MealAreaList.MealArea> areas);

    void showAreasError(String error);

    void onRandomMealClick(MealDTO.Meal meal);


}
