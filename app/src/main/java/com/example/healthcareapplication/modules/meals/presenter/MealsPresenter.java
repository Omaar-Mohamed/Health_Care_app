package com.example.healthcareapplication.modules.meals.presenter;

import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;
import com.example.healthcareapplication.model.network.NetWorkCallback;
import com.example.healthcareapplication.modules.meals.view.MealsIview;

import java.util.List;

public class MealsPresenter implements NetWorkCallback {

    private MealsIview mealsIview;
    private AppRepo appRepo;

    public MealsPresenter(MealsIview mealsIview, AppRepo appRepo) {
        this.mealsIview = mealsIview;
        this.appRepo = appRepo;
    }



    @Override
    public void onGetCategoriesSuccess(List<MealCategoryList.MealCategory> categories) {

    }

    @Override
    public void onGetCategoriesError(String error) {

    }

    @Override
    public void onGetRandomMealSuccess(List<MealDTO.Meal> meal) {

    }

    @Override
    public void onGetRandomMealError(String error) {

    }

    @Override
    public void onGetAreasSuccess(List<MealAreaList.MealArea> areas) {

    }

    @Override
    public void onGetAreasError(String error) {

    }

    @Override
    public void onGetMealsByCategorySuccess(List<MealListDto.MealListItemDto> meals) {
        mealsIview.showMeals(meals);
    }
    public void getMealsByCategory(String type ,String category) {
        appRepo.getMealsByCategory( category, this);
    }
    @Override
    public void onGetMealsByCategoryError(String error) {

    }

    @Override
    public void onGetMealsByAreaSuccess(List<MealListDto.MealListItemDto> meals) {
        mealsIview.showMeals(meals);
    }
    public void getMealsByArea(String type ,String area) {
        appRepo.getMealsByArea( area, this);
    }

    @Override
    public void onGetMealsByAreaError(String error) {

    }

    @Override
    public void onGetMealByIdSuccess(List<MealDetailDTO.MealItem> meals) {
        mealsIview.showMealDetail(meals);
    }
    public void getMealById(String id) {
        appRepo.getMealById( id, this);
    }

    @Override
    public void onGetMealByIdError(String error) {

    }
}
