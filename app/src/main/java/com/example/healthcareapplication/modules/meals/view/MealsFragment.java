package com.example.healthcareapplication.modules.meals.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.dto.MealListDto;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;
import com.example.healthcareapplication.modules.meals.presenter.MealsPresenter;

import org.w3c.dom.Text;

import java.util.List;


public class MealsFragment extends Fragment implements MealsIview {
    MealsPresenter mealsPresenter;
    RecyclerView recyclerView;

TextView message;
    public MealsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        recyclerView.setHasFixedSize(true);

        // Use GridLayoutManager instead of LinearLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // 2 columns, adjust as needed
        recyclerView.setLayoutManager(gridLayoutManager);

        mealsPresenter = new MealsPresenter(this, AppRepo.getInstance(AppRemoteDataSourseImp.getInstance()));
        Bundle args = getArguments();
        if (args != null) {
            String type = args.getString("category");
            mealsPresenter.getMealsByCategory(type);
        }

        return view;
    }



    @Override
    public void ShowError(String error) {
        Log.e("meals error", "ShowError: "+error );
    }

    @Override
    public void showMeals(List<MealListDto.MealListItemDto> meals) {
        Log.i("meals", "showMeals: "+meals);
        MealsGridAdapter mealsGridAdapter = new MealsGridAdapter(getContext(), meals, new OnMealClickListener() {
            @Override
            public void onMealClick(MealListDto.MealListItemDto meal) {

            }
        });
        recyclerView.setAdapter(mealsGridAdapter);
        mealsGridAdapter.notifyDataSetChanged();
    }
}