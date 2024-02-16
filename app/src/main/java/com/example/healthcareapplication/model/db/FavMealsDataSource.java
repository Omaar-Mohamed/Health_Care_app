package com.example.healthcareapplication.model.db;

import android.content.Context;

import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavMealsDataSource implements DataBaseOperation{

    private static FavMealsDataSource favMealsDataSource = null;
    private FAvMealsDAO fAvMealsDAO;
    private Flowable<List<MealDetailDTO.MealItem>> favMeals;

    private FavMealsDataSource(Context context , String email) {
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        fAvMealsDAO = appDatabase.FAvMealsDAO();
        favMeals = fAvMealsDAO.getFavMeals(email);
    }

    public static FavMealsDataSource getInstance(Context context , String email) {
        if (favMealsDataSource == null) {
            favMealsDataSource = new FavMealsDataSource(context , email);
        }
        return favMealsDataSource;
    }

    @Override
    public void insertFavMeal(MealDetailDTO.MealItem meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fAvMealsDAO.insertFavMeal(meal);
            }
        }).start();
    }

    @Override
    public void deleteFavMeal(MealDetailDTO.MealItem meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fAvMealsDAO.deleteFavMeal(meal);
            }
        }).start();

    }

    @Override
    public Flowable<List<MealDetailDTO.MealItem>> getAllFavMeals(String email) {
        return favMeals;
    }
}
