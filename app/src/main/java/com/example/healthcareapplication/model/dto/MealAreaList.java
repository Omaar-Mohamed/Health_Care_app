package com.example.healthcareapplication.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealAreaList {
    @SerializedName("meals")
    private List<MealArea> mealAreas;

    public List<MealArea> getMealAreas() {
        return mealAreas;
    }

    public static class MealArea {
        @SerializedName("strArea")
        private String area;

        public String getArea() {
            return area;
        }
    }
}
