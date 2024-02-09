package com.example.healthcareapplication.model;

import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;
import com.example.healthcareapplication.model.network.NetWorkCallback;

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
    public void getCategories(NetWorkCallback callback) {
        appRemoteDataSourseImp.getCategories(callback);

    }

    @Override
    public void getRandomMeal(NetWorkCallback callback) {
        appRemoteDataSourseImp.getRandomMeal(callback);
    }

    @Override
    public void getAreas(NetWorkCallback callback) {
        appRemoteDataSourseImp.getAreas(callback);
    }
}
