package com.example.healthcareapplication.modules.meals.presenter;

import static kotlinx.coroutines.flow.FlowKt.subscribeOn;

import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;
import com.example.healthcareapplication.modules.meals.view.MealsIview;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class MealsPresenter  {

    private MealsIview mealsIview;
    private AppRepo appRepo;

    public MealsPresenter(MealsIview mealsIview, AppRepo appRepo) {
        this.mealsIview = mealsIview;
        this.appRepo = appRepo;
    }






    public void getMealsByCategory(String type ,String category) {
        appRepo.getMealsByCategory( category)
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer <MealListDto>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MealListDto mealListDto) {
                        mealsIview.showMeals(mealListDto.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        mealsIview.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }



    public void getMealsByArea(String type ,String area) {
        appRepo.getMealsByArea( area)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealListDto>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MealListDto mealListDto) {
                        mealsIview.showMeals(mealListDto.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void getMealById(String id) {
        appRepo.getMealById( id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealDetailDTO>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MealDetailDTO mealDetailDTO) {
                        mealsIview.showMealDetail(mealDetailDTO.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void insertFavMeal(MealDetailDTO.MealItem meal) {
        appRepo.insertFavMeal(meal);
    }
    public void deleteFavMeal(MealDetailDTO.MealItem meal) {
        appRepo.deleteFavMeal(meal);
    }

    public Flowable<Boolean> checkMealExistInFav(MealDetailDTO.MealItem mealItem) {
        return Flowable.create(emitter -> {
            appRepo.getFavMeals(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meals -> {
                        for (MealDetailDTO.MealItem meal : meals) {
                            if (meal.getIdMeal().equals(mealItem.getIdMeal())) {
                                emitter.onNext(true);
                                emitter.onComplete();
                                return;
                            }
                        }
                        emitter.onNext(false);
                        emitter.onComplete();
                    }, emitter::onError);
        }, BackpressureStrategy.BUFFER);
    }



}
