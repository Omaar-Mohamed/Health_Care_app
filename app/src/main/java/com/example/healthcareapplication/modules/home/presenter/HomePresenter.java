package com.example.healthcareapplication.modules.home.presenter;

import androidx.annotation.NonNull;

import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;

import com.example.healthcareapplication.modules.home.view.HomeIview;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter  {
    private AppRepo appRepo;
    private HomeIview homeIview;
    ArrayList<MealCategoryList.MealCategory> mealCategories = new ArrayList<>();
    private ArrayList<MealDTO.Meal> randomMeal = new ArrayList<>();

    public HomePresenter(HomeIview homeIview, AppRepo appRepo) {
        this.homeIview = homeIview;
        this.appRepo = appRepo;
    }
    public void getCategories() {
        appRepo.getCategories()
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<MealCategoryList>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull MealCategoryList mealCategoryList) {
                        mealCategories.addAll(mealCategoryList.getMeals());
                        homeIview.showCategories(mealCategories);
                    }





            @Override
            public void onError(@NonNull Throwable e) {
                homeIview.showError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void getRandomMeal() {
        appRepo.getRandomMeal().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealDTO>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull MealDTO mealDTO) {
                randomMeal.addAll(mealDTO.getMeals());
                homeIview.showRandomMeal(randomMeal);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                homeIview.showRandomMealError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }
    public void getAreas() {
        appRepo.getAreas()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealAreaList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull MealAreaList mealAreaList) {
                        homeIview.showAreas(mealAreaList.getMealAreas());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        homeIview.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
