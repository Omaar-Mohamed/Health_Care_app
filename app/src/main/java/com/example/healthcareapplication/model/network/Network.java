package com.example.healthcareapplication.model.network;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Network {

    @GET("list.php?c=list")
    Observable<MealCategoryList> getCategories();

    @GET("random.php")
    Observable<MealDTO> getRandomMeal();

    @GET("list.php?a=list")
    Observable<MealAreaList> getAreas();

    @GET("filter.php")
    Observable<MealListDto> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Observable<MealListDto> getMealsByArea(@Query("a") String area);

    @GET("lookup.php")
    Observable<MealDetailDTO> getMealById(@Query("i") String id);

}
