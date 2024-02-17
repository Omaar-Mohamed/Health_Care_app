package com.example.healthcareapplication.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;
import com.example.healthcareapplication.model.dto.WeekPlan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Database(entities = {MealDetailDTO.MealItem.class , WeekPlan.class}, version =4 )
@TypeConverters({SqlDateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;
    public abstract FAvMealsDAO FAvMealsDAO();
    public abstract WeekPlanDAO weekPlanDAO();
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "meals")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
class SqlDateConverter {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @TypeConverter
    public static Date toDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return new Date(sdf.parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }
}