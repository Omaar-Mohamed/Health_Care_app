package com.example.healthcareapplication.model.network;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Network {

    @GET("list.php?c=list")
    Call<MealCategoryList> getCategories();

    @GET("random.php")
    Call<MealDTO> getRandomMeal();

    @GET("list.php?a=list")
    Call<MealAreaList> getAreas();

}
