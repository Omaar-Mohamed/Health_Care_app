package com.example.healthcareapplication.modules.Favourite.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.db.AppLocalDataSource;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.WeekPlan;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;
import com.example.healthcareapplication.modules.Favourite.presenter.FavMealsPresenter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouriteFragment extends Fragment implements FavMealsIView{

FavMealsPresenter favMealsPresenter;
RecyclerView recyclerView;
Date date;
String selectedType;

    TextView dateTextView ;
    public FavouriteFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favourite, container, false);
    favMealsPresenter = new FavMealsPresenter(this , AppRepo.getInstance(AppRemoteDataSourseImp.getInstance(),
                    AppLocalDataSource.getInstance(getContext() ,
                    FirebaseAuth.getInstance().getCurrentUser().getEmail())));

    recyclerView = view.findViewById(R.id.favRV);
    recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        favMealsPresenter.getFavMeals();

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        favMealsPresenter.getFavMeals();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        favMealsPresenter.getFavMeals();
//    }

    @Override
    public void showFavMeals(Flowable<List<MealDetailDTO.MealItem>> meals) {
        String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        meals.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealItems -> {
                    // Filter the list based on the current email
                    List<MealDetailDTO.MealItem> filteredList = new ArrayList<>();
                    for (MealDetailDTO.MealItem mealItem : mealItems) {
                        if (currentEmail.equals(mealItem.getEmail())) {
                            filteredList.add(mealItem);
                        }
                    }
                    return filteredList;
                })
                .subscribe(filteredMeals -> {
                    FavouriteAdapter favouriteAdapter = new FavouriteAdapter(getContext(), Flowable.just(filteredMeals), new OnFavMealClickListener() {
                        @Override
                        public void onFavMealClick(MealDetailDTO.MealItem mealItem) {
                            showMealTypePopup(getContext() , mealItem);
                        }

                        @Override
                        public void onFavMealDelete(MealDetailDTO.MealItem mealItem) {
                            favMealsPresenter.removeFavMeal(mealItem);
                        }
                    });

                    recyclerView.setAdapter(favouriteAdapter);
                    favouriteAdapter.notifyDataSetChanged();
                });
    }


    @Override
    public void showError(String error) {

    }

    private void showDatePickerDialog() {
        // Create a Calendar instance to get the current date
        Calendar calendar = Calendar.getInstance();

        // Create a DatePickerDialog and set the current date as the default date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    // Handle the selected date
                    String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                    // Save the selected date (you can store it wherever you need)
                    saveSelectedDate(selectedDate);
                    date = new Date(year, month, dayOfMonth);

                    // Show a toast with the selected date (you can remove this line)
                    Toast.makeText(getContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
                    changeDate(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    // Method to save the selected date (you can customize this based on your needs)
    private void saveSelectedDate(String selectedDate) {

        // Save the selected date logic goes here
    }

private String getSelectedDate(String selectedDate) {
    // Get the selected date logic goes here
    return selectedDate;
}

    public  void showMealTypePopup(Context context ,MealDetailDTO.MealItem mealItem) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_meal_type, null);

        ImageView calenderbutton = popupView.findViewById(R.id.CalenderimageButton);
         dateTextView = popupView.findViewById(R.id.selectedDateTextView);
//        dateTextView.setText(getSelectedDate(date.toString()));
        calenderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        Button addPlanButton = popupView.findViewById(R.id.confirmButton);

        // Set up the Spinner (dropdown menu)
        Spinner mealTypeSpinner = popupView.findViewById(R.id.mealTypeSpinner);
        List<String> mealTypes = new ArrayList<>();
        mealTypes.add("Dinner");
        mealTypes.add("Breakfast");
        mealTypes.add("Lunch");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, mealTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealTypeSpinner.setAdapter(adapter);
        mealTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item
                selectedType = mealTypes.get(position);

              Toast.makeText(getContext(), "Selected Meal Type: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        int popupWidth = (int) (screenWidth * 0.80); // 75% of screen width
        int popupHeight = (int) (screenHeight * 0.40); // 75% of screen height

        // Create PopupWindow with calculated dimensions
        PopupWindow popupWindow = new PopupWindow(popupView, popupWidth, popupHeight, true);
        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the selected meal to the plan
                // You can add the selected meal to the plan here
                // You can use the selectedType and date variables here
                // You can also use the selected meal item here
                // You can remove the toast below
                if (date != null) {
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    WeekPlan weekPlan = new WeekPlan(mealItem.getIdMeal(), email, mealItem.getStrMeal(), mealItem.getStrMealThumb(), date, selectedType);
                    Log.i("jojo", "onClick: "+weekPlan.getIdMeal()+" "+weekPlan.getEmail()+" "+weekPlan.getStrMeal()+" "+weekPlan.getStrMealThumb()+" "+weekPlan.getDate()+" "+weekPlan.getType());
                    favMealsPresenter.addToPlan(weekPlan);
                    Toast.makeText(getContext(), "Meal added to the plan", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }else {
                    Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
   void changeDate(String date){

       dateTextView.setText(date);
   }
}