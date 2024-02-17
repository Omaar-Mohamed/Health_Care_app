package com.example.healthcareapplication.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.healthcareapplication.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface WeekPlanDAO {
   @Query("SELECT * FROM meal_plan")
    Flowable<List<WeekPlan>> getAllWeekPlan();
   @Insert
    void insertWeekPlan(WeekPlan weekPlan);

   @Delete
    void deleteWeekPlan(WeekPlan weekPlan);

}
