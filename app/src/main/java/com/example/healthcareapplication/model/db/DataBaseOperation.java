package com.example.healthcareapplication.model.db;

import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;
import com.example.healthcareapplication.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface DataBaseOperation {
     void insertFavMeal(MealDetailDTO.MealItem meal);

     void deleteFavMeal(MealDetailDTO.MealItem meal);

     Flowable<List<MealDetailDTO.MealItem>> getAllFavMeals(String email);

     Flowable<List<WeekPlan>> getPlan();
     void insertPlan(WeekPlan weekPlan);
        void deletePlan(WeekPlan weekPlan);
}
