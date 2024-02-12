package com.example.healthcareapplication.model;

import com.example.healthcareapplication.model.network.NetWorkCallback;

public interface AppRepoOperations {
    void getCategories(NetWorkCallback callback);

    void getRandomMeal(NetWorkCallback callback);
    void getAreas(NetWorkCallback callback);

    void getMealsByCategory(String category, NetWorkCallback callback);


}
