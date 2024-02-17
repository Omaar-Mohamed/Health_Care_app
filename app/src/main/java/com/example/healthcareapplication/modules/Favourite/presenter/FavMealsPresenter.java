package com.example.healthcareapplication.modules.Favourite.presenter;

import android.util.Log;

import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.WeekPlan;
import com.example.healthcareapplication.modules.Favourite.view.FavMealsIView;
import com.google.firebase.auth.FirebaseAuth;

public class FavMealsPresenter {
    private AppRepo appRepo;
    private FavMealsIView favMealsIView;

    public FavMealsPresenter(FavMealsIView favMealsIView, AppRepo appRepo) {
        this.favMealsIView = favMealsIView;
        this.appRepo = appRepo;
    }

    public void getFavMeals() {
        // Assuming appRepo.getFavMeals() returns LiveData<List<MealListDto.MealItem>>
        favMealsIView.showFavMeals(appRepo.getFavMeals(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        Log.i("check email in the presenter", "getFavMeals: " + FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    public void removeFavMeal(MealDetailDTO.MealItem meal) {
        appRepo.deleteFavMeal(meal);
    }

    public void addToPlan(WeekPlan weekPlan) {
        appRepo.insertPlan(weekPlan);
    }
}
