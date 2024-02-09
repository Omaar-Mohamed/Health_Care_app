package com.example.healthcareapplication.modules.home.presenter;

import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.network.NetWorkCallback;
import com.example.healthcareapplication.modules.home.view.HomeIview;

import java.util.List;

public class HomePresenter implements NetWorkCallback {
    private AppRepo appRepo;
    private HomeIview homeIview;

    public HomePresenter(HomeIview homeIview, AppRepo appRepo) {
        this.homeIview = homeIview;
        this.appRepo = appRepo;
    }
    public void getCategories() {
        appRepo.getCategories(this);
    }
    @Override
    public void onGetCategoriesSuccess(List<MealCategoryList.MealCategory> categories) {
        homeIview.showCategories(categories);

    }

    @Override
    public void onGetCategoriesError(String error) {
        homeIview.showError(error);

    }

    @Override
    public void onGetRandomMealSuccess(List<MealDTO.Meal> meal) {
        homeIview.showRandomMeal(meal);
    }

    @Override
    public void onGetRandomMealError(String error) {
        homeIview.showRandomMealError(error);
    }

    @Override
    public void onGetAreasSuccess(List<MealAreaList.MealArea> areas) {
        homeIview.showAreas(areas);
    }

    @Override
    public void onGetAreasError(String error) {

    }

    public void getRandomMeal() {
        appRepo.getRandomMeal(this);

    }
    public void getAreas() {
        appRepo.getAreas(this);
    }
}
