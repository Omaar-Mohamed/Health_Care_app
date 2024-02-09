package com.example.healthcareapplication.model.network;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;

import java.util.List;

public interface NetWorkCallback {
    void onGetCategoriesSuccess(List<MealCategoryList.MealCategory> categories);
    void onGetCategoriesError(String error);

    void onGetRandomMealSuccess(List<MealDTO.Meal> meal);

    void onGetRandomMealError(String error);

    void onGetAreasSuccess(List<MealAreaList.MealArea> areas);

    void onGetAreasError(String error);
}
