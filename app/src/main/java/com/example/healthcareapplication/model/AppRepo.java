package com.example.healthcareapplication.model;

import com.example.healthcareapplication.model.db.AppLocalDataSource;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.SearchMealByNameResponse;
import com.example.healthcareapplication.model.dto.WeekPlan;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class AppRepo implements AppRepoOperations{
    AppRemoteDataSourseImp appRemoteDataSourseImp;
    AppLocalDataSource appLocalDataSource;

    private static AppRepo instance;

    public static AppRepo getInstance(AppRemoteDataSourseImp appRemoteDataSourseImp , AppLocalDataSource appLocalDataSource) {
        if (instance == null) {
            instance = new AppRepo(appRemoteDataSourseImp , appLocalDataSource);
        }
        return instance;
    }
    private AppRepo(AppRemoteDataSourseImp appRemoteDataSourseImp , AppLocalDataSource appLocalDataSource) {
        this.appRemoteDataSourseImp = appRemoteDataSourseImp;
        this.appLocalDataSource = appLocalDataSource;
    }

    @Override
    public Observable getCategories() {
        return appRemoteDataSourseImp.getCategories();

    }

    @Override
    public Observable getRandomMeal() {
       return appRemoteDataSourseImp.getRandomMeal();
    }

    @Override
    public Observable getAreas() {
       return appRemoteDataSourseImp.getAreas();
    }

    @Override
    public Observable getMealsByCategory(String category ) {
        return appRemoteDataSourseImp.getMealsByCategory( category);
    }

    @Override
    public Observable getMealsByArea(String area ) {
        return appRemoteDataSourseImp.getMealsByArea(area);
    }

    @Override
    public Observable getMealById(String id) {
       return appRemoteDataSourseImp.getMealDetails(id);
    }

    @Override
    public Observable<SearchMealByNameResponse> getMealsByName(String name) {
        return appRemoteDataSourseImp.getMealByName(name);
    }

    @Override
    public Observable getIngredients() {
        return appRemoteDataSourseImp.getIngrediants();
    }

    @Override
    public Flowable<List<MealDetailDTO.MealItem>> getFavMeals(String email) {
        return appLocalDataSource.getAllFavMeals(email) ;
    }

    @Override
    public void insertFavMeal(MealDetailDTO.MealItem meal) {
            appLocalDataSource.insertFavMeal(meal);
    }

    @Override
    public void deleteFavMeal(MealDetailDTO.MealItem meal) {
        appLocalDataSource.deleteFavMeal(meal);
    }

    @Override
    public Flowable<List<WeekPlan>> getPlan() {
        return appLocalDataSource.getPlan();
    }

    @Override
    public void insertPlan(WeekPlan weekPlan) {
        appLocalDataSource.insertPlan(weekPlan);
    }

    @Override
    public void deletePlan(WeekPlan weekPlan) {
        appLocalDataSource.deletePlan(weekPlan);
    }


}
