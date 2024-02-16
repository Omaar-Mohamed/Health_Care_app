package com.example.healthcareapplication.model;


import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface AppRepoOperations {
    Observable getCategories();

    Observable getRandomMeal();
    Observable getAreas();

    Observable getMealsByCategory(String category);

    Observable getMealsByArea(String area);

    Observable getMealById(String id);

    Flowable<List<MealDetailDTO.MealItem>> getFavMeals(String email);
    void insertFavMeal(MealDetailDTO.MealItem meal);

    void deleteFavMeal(MealDetailDTO.MealItem meal);




}
