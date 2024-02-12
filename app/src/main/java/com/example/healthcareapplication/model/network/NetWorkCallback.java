package com.example.healthcareapplication.model.network;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;

import java.util.List;

public interface NetWorkCallback {
    void onGetCategoriesSuccess(List<MealCategoryList.MealCategory> categories);
    void onGetCategoriesError(String error);

    void onGetRandomMealSuccess(List<MealDTO.Meal> meal);

    void onGetRandomMealError(String error);

    void onGetAreasSuccess(List<MealAreaList.MealArea> areas);

    void onGetAreasError(String error);

    void onGetMealsByCategorySuccess(List<MealListDto.MealListItemDto> meals);

    void onGetMealsByCategoryError(String error);

    void onGetMealsByAreaSuccess(List<MealListDto.MealListItemDto> meals);

    void onGetMealsByAreaError(String error);

    void onGetMealByIdSuccess(List<MealDetailDTO.MealItem> meals);

    void onGetMealByIdError(String error);
}
