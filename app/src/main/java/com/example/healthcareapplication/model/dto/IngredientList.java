package com.example.healthcareapplication.model.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class IngredientList {
    @SerializedName("meals")
    private List<IngredientDTO> ingredients;

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }


    public static class IngredientDTO {
        @SerializedName("idIngredient")
        private String idIngredient;

        @SerializedName("strIngredient")
        private String strIngredient;

        @SerializedName("strDescription")
        private String strDescription;

        @SerializedName("strType")
        private String strType;

        public String getIdIngredient() {
            return idIngredient;
        }

        public String getStrIngredient() {
            return strIngredient;
        }

        public String getStrDescription() {
            return strDescription;
        }

        public String getStrType() {
            return strType;
        }
    }

}