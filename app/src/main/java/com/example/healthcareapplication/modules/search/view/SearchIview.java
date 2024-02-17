package com.example.healthcareapplication.modules.search.view;

import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.SearchResult;

import java.util.List;

public interface SearchIview {
    void showsearchResult(List<SearchResult> resultsList);

}
