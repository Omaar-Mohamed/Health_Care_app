package com.example.healthcareapplication.model;


import io.reactivex.rxjava3.core.Observable;

public interface AppRepoOperations {
    Observable getCategories();

    Observable getRandomMeal();
    Observable getAreas();

    Observable getMealsByCategory(String category);

    Observable getMealsByArea(String area);

    Observable getMealById(String id);




}
