package com.example.healthcareapplication.modules.Favourite.view;

import com.example.healthcareapplication.model.dto.MealDetailDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavMealsIView {

    void showFavMeals(Flowable<List<MealDetailDTO.MealItem>> meals);
    void showError(String error);
}
