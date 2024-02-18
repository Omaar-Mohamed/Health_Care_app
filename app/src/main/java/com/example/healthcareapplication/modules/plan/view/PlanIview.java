package com.example.healthcareapplication.modules.plan.view;

import com.example.healthcareapplication.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface PlanIview {
    void showPlan(Flowable<List<WeekPlan>> plan);
    void showError(String error);
}
