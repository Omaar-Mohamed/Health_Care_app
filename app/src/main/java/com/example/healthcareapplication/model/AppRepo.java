package com.example.healthcareapplication.model;

import com.example.healthcareapplication.model.db.FavMealsDataSource;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class AppRepo implements AppRepoOperations{
    AppRemoteDataSourseImp appRemoteDataSourseImp;
    FavMealsDataSource favMealsDataSource;

    private static AppRepo instance;

    public static AppRepo getInstance(AppRemoteDataSourseImp appRemoteDataSourseImp , FavMealsDataSource favMealsDataSource) {
        if (instance == null) {
            instance = new AppRepo(appRemoteDataSourseImp , favMealsDataSource);
        }
        return instance;
    }
    private AppRepo(AppRemoteDataSourseImp appRemoteDataSourseImp , FavMealsDataSource favMealsDataSource) {
        this.appRemoteDataSourseImp = appRemoteDataSourseImp;
        this.favMealsDataSource = favMealsDataSource;
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
    public Flowable<List<MealDetailDTO.MealItem>> getFavMeals(String email) {
        return favMealsDataSource.getAllFavMeals(email) ;
    }

    @Override
    public void insertFavMeal(MealDetailDTO.MealItem meal) {
            favMealsDataSource.insertFavMeal(meal);
    }

    @Override
    public void deleteFavMeal(MealDetailDTO.MealItem meal) {
        favMealsDataSource.deleteFavMeal(meal);
    }


}
