package com.example.healthcareapplication.model.network;

import android.util.Log;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppRemoteDataSourseImp {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static AppRemoteDataSourseImp INSTANCE;
    private ArrayList<MealCategoryList.MealCategory> categories = new ArrayList<>();
    private ArrayList<MealDTO.Meal> randomMeal = new ArrayList<>();

    private ArrayList<MealAreaList.MealArea> areas = new ArrayList<>();

    private ArrayList<MealListDto.MealListItemDto> Meal = new ArrayList<>();

    private ArrayList<MealDetailDTO.MealItem> mealDetails = new ArrayList<>();

    private AppRemoteDataSourseImp() {
    }

    public static AppRemoteDataSourseImp getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppRemoteDataSourseImp();
        }
        return INSTANCE;
    }

    public Observable getCategories() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        return network.getCategories()
                .subscribeOn(Schedulers.io());
//        network.getCategories().enqueue(new retrofit2.Callback<MealCategoryList>() {
//            @Override
//            public void onResponse(retrofit2.Call<MealCategoryList> call, retrofit2.Response<MealCategoryList> response) {
//                if (response.isSuccessful()) {
//                    categories.addAll(response.body().getMeals());
//                    callback.onGetCategoriesSuccess(categories);
//                } else {
//                    callback.onGetCategoriesError("Unsuccessful response");
//                }
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<MealCategoryList> call, Throwable t) {
//                callback.onGetCategoriesError(t.getMessage());
//            }
//        });
    }

    public Observable getRandomMeal() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        return network.getRandomMeal()
                .subscribeOn(Schedulers.io());
//        network.getRandomMeal().enqueue(new Callback<MealDTO>() {
//
//            @Override
//            public void onResponse(Call<MealDTO> call, Response<MealDTO> response) {
//                if (response.isSuccessful()) {
//                    randomMeal.addAll(response.body().getMeals());
//                    callback.onGetRandomMealSuccess(randomMeal);
//                } else {
//                    callback.onGetRandomMealError("Unsuccessful response");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MealDTO> call, Throwable t) {
//                callback.onGetRandomMealError(t.getMessage());
//            }
//        });
    }

    public Observable getAreas() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        return network.getAreas()
                .subscribeOn(Schedulers.io());
//        network.getAreas().enqueue(new Callback<MealAreaList>() {
//
//            @Override
//            public void onResponse(Call<MealAreaList> call, Response<MealAreaList> response) {
//                if (response.isSuccessful()) {
//                    areas.addAll(response.body().getMealAreas());
//                    callback.onGetAreasSuccess(areas);
//                } else {
//                    callback.onGetAreasError("Unsuccessful response");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MealAreaList> call, Throwable t) {
//                callback.onGetAreasError(t.getMessage());
//            }
//        });
    }

public Observable getMealsByCategory(String category) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
return network.getMealsByCategory(category)
                .subscribeOn(Schedulers.io());
//        network.getMealsByCategory(category).enqueue(new Callback<MealListDto>() {
//
//            @Override
//            public void onResponse(Call<MealListDto> call, Response<MealListDto> response) {
//                if (response.isSuccessful()) {
//                    Meal.addAll(response.body().getMeals());
//                    Log.i("meal from area", "onResponse: "+response.body().getMeals());
//                    callback.onGetMealsByAreaSuccess(Meal);
//                } else {
//                    callback.onGetMealsByCategoryError("Unsuccessful response");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MealListDto> call, Throwable t) {
//                callback.onGetMealsByCategoryError(t.getMessage());
//            }
//        });

}

    public Observable getMealsByArea(String area) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        return network.getMealsByArea(area)
                .subscribeOn(Schedulers.io());
//        network.getMealsByArea(area).enqueue(new Callback<MealListDto>() {
//
//            @Override
//            public void onResponse(Call<MealListDto> call, Response<MealListDto> response) {
//                if (response.isSuccessful()) {
//                    Meal.addAll(response.body().getMeals());
//                    callback.onGetMealsByAreaSuccess(Meal);
//                } else {
//                    callback.onGetMealsByCategoryError("Unsuccessful response");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MealListDto> call, Throwable t) {
//                callback.onGetMealsByAreaError(t.getMessage());
//            }
//        });

    }

    public Observable getMealDetails(String id) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        return network.getMealById(id)
                .subscribeOn(Schedulers.io());
//        network.getMealById(id).enqueue(new Callback<MealDetailDTO>() {
//
//            @Override
//            public void onResponse(Call<MealDetailDTO> call, Response<MealDetailDTO> response) {
//                if (response.isSuccessful()) {
//                    mealDetails.addAll(response.body().getMeals());
//                    callback.onGetMealByIdSuccess(mealDetails);
//                } else {
//                    callback.onGetMealByIdError("Unsuccessful response");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MealDetailDTO> call, Throwable t) {
//                callback.onGetMealByIdError(t.getMessage());
//            }
//
//        });

    }

    public Observable getMealByName(String name) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        return network.getMealsByName(name)
                .subscribeOn(Schedulers.io());
//        network.getMealById(id).enqueue(new Callback<MealDetailDTO>() {
//
//            @Override
//            public void onResponse(Call<MealDetailDTO> call, Response<MealDetailDTO> response) {
//                if (response.isSuccessful()) {
//                    mealDetails.addAll(response.body().getMeals());
//                    callback.onGetMealByIdSuccess(mealDetails);
//                } else {
//                    callback.onGetMealByIdError("Unsuccessful response");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MealDetailDTO> call, Throwable t) {
//                callback.onGetMealByIdError(t.getMessage());
//            }
//
//        });

    }

    public Observable getIngrediants() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        return network.getIngredients()
                .subscribeOn(Schedulers.io());
//        network.getAreas().enqueue(new Callback<MealAreaList>() {
//
//            @Override
//            public void onResponse(Call<MealAreaList> call, Response<MealAreaList> response) {
//                if (response.isSuccessful()) {
//                    areas.addAll(response.body().getMealAreas());
//                    callback.onGetAreasSuccess(areas);
//                } else {
//                    callback.onGetAreasError("Unsuccessful response");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MealAreaList> call, Throwable t) {
//                callback.onGetAreasError(t.getMessage());
//            }
//        });
    }

}
