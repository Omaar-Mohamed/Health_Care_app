package com.example.healthcareapplication.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealListDto {

    @SerializedName("meals")
    private List<MealListItemDto> meals;

    public List<MealListItemDto> getMeals() {
        return meals;
    }

    public static class MealListItemDto {
        @SerializedName("strMeal")
        private String mealName;

        @SerializedName("strMealThumb")
        private String mealThumb;

        @SerializedName("idMeal")
        private String mealId;

        public String getMealName() {
            return mealName;
        }

        public String getMealThumb() {
            return mealThumb;
        }

        public String getMealId() {
            return mealId;
        }
    }
}
