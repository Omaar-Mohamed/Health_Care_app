package com.example.healthcareapplication.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface FAvMealsDAO {
    @Query("SELECT * FROM fav_meals")
    Flowable <List<MealDetailDTO.MealItem>> getFavMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavMeal(MealDetailDTO.MealItem meal);

    @Delete
    void deleteFavMeal(MealDetailDTO.MealItem meal);
}
