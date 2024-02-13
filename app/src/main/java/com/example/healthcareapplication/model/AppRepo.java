package com.example.healthcareapplication.model;

import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;

import io.reactivex.rxjava3.core.Observable;

public class AppRepo implements AppRepoOperations{
    AppRemoteDataSourseImp appRemoteDataSourseImp;

    private static AppRepo instance;

    public static AppRepo getInstance(AppRemoteDataSourseImp appRemoteDataSourseImp) {
        if (instance == null) {
            instance = new AppRepo(appRemoteDataSourseImp);
        }
        return instance;
    }
    private AppRepo(AppRemoteDataSourseImp appRemoteDataSourseImp) {
        this.appRemoteDataSourseImp = appRemoteDataSourseImp;
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


}
