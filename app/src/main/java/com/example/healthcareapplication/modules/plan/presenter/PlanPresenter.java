package com.example.healthcareapplication.modules.plan.presenter;

import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.modules.plan.view.PlanIview;

public class PlanPresenter {
    private PlanIview planIview;
    private AppRepo appRepo;
    public PlanPresenter(PlanIview planIview, AppRepo appRepo) {
        this.planIview = planIview;
        this.appRepo = appRepo;
    }
    public void getPlan() {
        planIview.showPlan(appRepo.getPlan());
    }

}
