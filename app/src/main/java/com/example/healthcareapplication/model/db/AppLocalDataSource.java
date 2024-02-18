package com.example.healthcareapplication.model.db;

import android.content.Context;

import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class AppLocalDataSource implements DataBaseOperation{

    private static AppLocalDataSource appLocalDataSource = null;
    private FAvMealsDAO fAvMealsDAO;
    private WeekPlanDAO weekPlanDAO;
    private Flowable<List<MealDetailDTO.MealItem>> favMeals;

    private AppLocalDataSource(Context context ) {
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        fAvMealsDAO = appDatabase.FAvMealsDAO();
        weekPlanDAO = appDatabase.weekPlanDAO();
        favMeals = fAvMealsDAO.getFavMeals();
    }

    public static AppLocalDataSource getInstance(Context context ) {
        if (appLocalDataSource == null) {
            appLocalDataSource = new AppLocalDataSource(context );
        }
        return appLocalDataSource;
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

    @Override
    public Flowable<List<WeekPlan>> getPlan() {
        return weekPlanDAO.getAllWeekPlan();
    }

    @Override
    public void insertPlan(WeekPlan weekPlan) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                weekPlanDAO.insertWeekPlan(weekPlan);
            }
        }).start();
    }

    @Override
    public void deletePlan(WeekPlan weekPlan) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                weekPlanDAO.deleteWeekPlan(weekPlan);
            }
        }).start();

    }
}
