package com.example.healthcareapplication.model.network;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppRemoteDataSourseImp {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static AppRemoteDataSourseImp INSTANCE;
    private ArrayList<MealCategoryList.MealCategory> categories = new ArrayList<>();
    private ArrayList<MealDTO.Meal> Meal = new ArrayList<>();

    private ArrayList<MealAreaList.MealArea> areas = new ArrayList<>();

    private AppRemoteDataSourseImp() {
    }

    public static AppRemoteDataSourseImp getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppRemoteDataSourseImp();
        }
        return INSTANCE;
    }

    public void getCategories(NetWorkCallback callback) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        network.getCategories().enqueue(new retrofit2.Callback<MealCategoryList>() {
            @Override
            public void onResponse(retrofit2.Call<MealCategoryList> call, retrofit2.Response<MealCategoryList> response) {
                if (response.isSuccessful()) {
                    categories.addAll(response.body().getMeals());
                    callback.onGetCategoriesSuccess(categories);
                } else {
                    callback.onGetCategoriesError("Unsuccessful response");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<MealCategoryList> call, Throwable t) {
                callback.onGetCategoriesError(t.getMessage());
            }
        });
    }

    public void getRandomMeal(NetWorkCallback callback) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        network.getRandomMeal().enqueue(new Callback<MealDTO>() {

            @Override
            public void onResponse(Call<MealDTO> call, Response<MealDTO> response) {
                if (response.isSuccessful()) {
                    Meal.addAll(response.body().getMeals());
                    callback.onGetRandomMealSuccess(Meal);
                } else {
                    callback.onGetRandomMealError("Unsuccessful response");
                }
            }

            @Override
            public void onFailure(Call<MealDTO> call, Throwable t) {
                callback.onGetRandomMealError(t.getMessage());
            }
        });
    }

    public void getAreas(NetWorkCallback callback) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Network network = retrofit.create(Network.class);
        network.getAreas().enqueue(new Callback<MealAreaList>() {

            @Override
            public void onResponse(Call<MealAreaList> call, Response<MealAreaList> response) {
                if (response.isSuccessful()) {
                    areas.addAll(response.body().getMealAreas());
                    callback.onGetAreasSuccess(areas);
                } else {
                    callback.onGetAreasError("Unsuccessful response");
                }
            }

            @Override
            public void onFailure(Call<MealAreaList> call, Throwable t) {
                callback.onGetAreasError(t.getMessage());
            }
        });
    }
}
