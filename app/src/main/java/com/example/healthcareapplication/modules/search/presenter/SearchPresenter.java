package com.example.healthcareapplication.modules.search.presenter;

import android.util.Log;

import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.dto.IngredientList;
import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.SearchMealByNameResponse;
import com.example.healthcareapplication.model.dto.SearchResult;
import com.example.healthcareapplication.modules.search.view.SearchIview;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {
    private AppRepo appRepo;
    private SearchIview searchIview;
    Observable<IngredientList> ingredientsObservable;
    Observable<MealCategoryList> categoriesObservable;
    Observable<MealAreaList> countriesObservable;
    Observable<SearchResult> mealsNamesObservable;



    public SearchPresenter(SearchIview searchIview, AppRepo appRepo) {
        this.searchIview = searchIview;
        this.appRepo = appRepo;
    }
    public void loadData()
    {
        ingredientsObservable=appRepo.getIngredients();
        countriesObservable=appRepo.getAreas();
        categoriesObservable=appRepo.getCategories();

    }

    public void getSearch(String query)
    {
        Observable<SearchResult> obs1=ingredientsObservable
                .flatMapIterable(ingredientList -> ingredientList.getIngredients())
            .map(s -> new SearchResult(s.getStrIngredient(),"ingredient",s.getIdIngredient()))
                .subscribeOn(Schedulers.io());
        Observable<SearchResult> obs2=countriesObservable
                .flatMapIterable(mealAreaList -> mealAreaList.getMealAreas())
                .map(mealArea -> new SearchResult(mealArea.getArea(),"area" ,"0"))
                .subscribeOn(Schedulers.io());
        Observable<SearchResult> obs3=categoriesObservable
                .flatMapIterable(mealCategoryList -> mealCategoryList.getMeals())
                .map(mealCategory -> new SearchResult(mealCategory.getStrCategory(),"category" ,"0"))
                .subscribeOn(Schedulers.io());


       Observable<SearchResult> mealsObservable= appRepo.getMealsByName(query)
                .flatMapIterable(searchMealByNameResponse -> searchMealByNameResponse.getMeals())
                .map(meal -> new SearchResult(meal.getStrMeal(),"meal" , meal.getIdMeal()))
                .subscribeOn(Schedulers.io());

        Observable<SearchResult> bigObservable= Observable.merge(obs1,obs2,obs3,mealsObservable);
        bigObservable
                .filter(s -> s.getResult().toLowerCase().contains(query))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<SearchResult>>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<SearchResult> searchResults) {
                        Log.d("show search result i presenter search", "onSuccess: "+searchResults);
                        searchIview.showsearchResult(searchResults);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }




    public void insertFavMeal(MealDetailDTO.MealItem meal) {
        appRepo.insertFavMeal(meal);
    }








//    public SearchMealByNameResponse getNames(String query) {
//      appRepo.getMealsByName(query)
//              .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SearchMealByNameResponse>() {
//                  @Override
//                  public void onSubscribe(@NonNull Disposable d) {
//
//                  }
//
//                  @Override
//                  public void onNext(@NonNull SearchMealByNameResponse searchMealByNameResponse) {
//                      Log.i("get meals by checkins", "onNext: SearchMealByNameResponse"+searchMealByNameResponse.getMeals());
//                  }
//
//                  @Override
//                  public void onError(@NonNull Throwable e) {
//
//                  }
//
//                  @Override
//                  public void onComplete() {
//
//                  }
//              });
//    }
//
//    public void getAreas() {
//        appRepo.getAreas()
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MealAreaList>() {
//
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull MealAreaList mealAreaList) {
//                        Log.i("get Areas from search presenter", "onNext: getAreas"+mealAreaList.getMealAreas());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
//
//    public void getIngredints() {
//        appRepo.getIngredients()
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<IngredientList>() {
//
//
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull IngredientList ingredientList) {
//                        Log.i("get Ingredients from search presenter", "onNext: getIngredients"+ingredientList.getIngredients());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
//
//    public void getCategories() {
//        appRepo.getCategories()
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MealCategoryList>() {
//
//
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull MealCategoryList mealCategoryList) {
//                        Log.i("get Categories from search presenter", "onNext: getCategories"+mealCategoryList.getMeals());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
}
