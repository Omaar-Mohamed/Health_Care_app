package com.example.healthcareapplication.modules.plan.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.db.AppLocalDataSource;
import com.example.healthcareapplication.model.dto.WeekPlan;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;
import com.example.healthcareapplication.modules.plan.presenter.PlanPresenter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class PlanFragment extends Fragment implements PlanIview {
RecyclerView recyclerView;
PlanPresenter presenter;

String selectedType;

Date date;

    public PlanFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        recyclerView = view.findViewById(R.id.weekPlanRecyclerView);
        presenter = new PlanPresenter(this, AppRepo.getInstance(AppRemoteDataSourseImp.getInstance(),
                AppLocalDataSource.getInstance(getContext())));
         recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);



        Spinner mealTypeSpinner = view.findViewById(R.id.mealTypeSpinner1);
        List<String> mealTypes = new ArrayList<>();
        mealTypes.add("Dinner");
        mealTypes.add("Breakfast");
        mealTypes.add("Lunch");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mealTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealTypeSpinner.setAdapter(adapter);
        mealTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item
                selectedType = mealTypes.get(position);
                Toast.makeText(getContext(), "Selected Meal Type: " + selectedType, Toast.LENGTH_SHORT).show();

                // Call getPlan() after updating selectedType
                presenter.getPlan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });


//        ImageView dateButton = view.findViewById(R.id.datePickerButton);
//        dateButton.setOnClickListener(v -> {
//            // Implement the date picker logic here
//            showDatePickerDialog();
//        });





        presenter.getPlan();

        return view;
    }

    @Override
    public void showPlan(Flowable<List<WeekPlan>> plan) {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        plan.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(plans -> {
                    // Use filter to get the plans with matching email, type, and date
                    List<WeekPlan> filteredPlans = new ArrayList<>();
                    for (WeekPlan plan1 : plans) {
                        if (plan1.getEmail().equals(email) &&
                                (selectedType == null || plan1.getType().equals(selectedType)) &&
                                (date == null || plan1.getDate().equals(date))) {
                            filteredPlans.add(plan1);
                        }
                    }
                    return filteredPlans;
                })
                .subscribe(filteredPlans -> {
                    // Display the filtered plans
                    PlanAdapter adapter = new PlanAdapter(getContext(), Flowable.just(filteredPlans), new OnPlanClickListener() {
                        @Override
                        public void onPlanClick(WeekPlan weekPlan) {
                            presenter.deletePlan(weekPlan);
                        }

                        // Implement the click listener logic if needed
                    });
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
                    date = new Date(year - 1900, month, dayOfMonth);

                    // Show a toast with the selected date (you can remove this line)
                    Toast.makeText(getContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();

                    // Call getPlan() after updating the date
                    presenter.getPlan();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }


}