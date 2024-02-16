package com.example.healthcareapplication.model.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

        private String email;

        public String getEmail() {
            return email;
        }

        public void setMealName(String mealName) {
            this.mealName = mealName;
        }

        public void setMealThumb(String mealThumb) {
            this.mealThumb = mealThumb;
        }

        public void setMealId(String mealId) {
            this.mealId = mealId;
        }

        public void setEmail(String email) {
          this.email = email;
        }
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
