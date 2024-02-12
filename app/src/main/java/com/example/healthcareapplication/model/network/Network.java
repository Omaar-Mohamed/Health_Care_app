package com.example.healthcareapplication.model.network;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.dto.MealListDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Network {

    @GET("list.php?c=list")
    Call<MealCategoryList> getCategories();

    @GET("random.php")
    Call<MealDTO> getRandomMeal();

    @GET("list.php?a=list")
    Call<MealAreaList> getAreas();

    @GET("filter.php")
    Call<MealListDto> getMealsByCategory(@Query("c") String category);

}
