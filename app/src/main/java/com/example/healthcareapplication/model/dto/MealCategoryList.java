package com.example.healthcareapplication.model.dto;

import java.util.List;

public class MealCategoryList {
    private List<MealCategory> meals;

    public List<MealCategory> getMeals() {
        return meals;
    }

    public void setMeals(List<MealCategory> meals) {
        this.meals = meals;
    }

    public static class MealCategory {
        private String strCategory;

        public String getStrCategory() {
            return strCategory;
        }

        public void setStrCategory(String strCategory) {
            this.strCategory = strCategory;
        }
    }
}
