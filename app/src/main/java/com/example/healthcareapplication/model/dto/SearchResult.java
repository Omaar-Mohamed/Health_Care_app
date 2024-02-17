package com.example.healthcareapplication.model.dto;

public class SearchResult {
    String result;
    String type;
    String mealId;

    public SearchResult(String result, String type , String mealId) {
        this.result = result;
        this.type = type;
        this.mealId = mealId;
    }

    public String getResult() {
        return result;
    }

    public String getMealId() {
        return mealId;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
