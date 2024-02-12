package com.example.healthcareapplication.modules.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;
import com.example.healthcareapplication.modules.home.presenter.HomePresenter;

import java.util.List;


public class HomeFragment extends Fragment implements HomeIview {
    HomePresenter homePresenter;
    RecyclerView categoriesRecyclerView;

    RecyclerView countriesRecyclerView;
    TextView randomMealName;
    ImageView randomMealImage;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRV);
        randomMealName = view.findViewById(R.id.textViewMealName);
        randomMealImage = view.findViewById(R.id.imageViewMeal);

        categoriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoriesRecyclerView.setLayoutManager(layoutManager);

        //country recycler veiw
        countriesRecyclerView = view.findViewById(R.id.countriesRV);
        countriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        countriesRecyclerView.setLayoutManager(layoutManager1);
        homePresenter = new HomePresenter(this , AppRepo.getInstance(AppRemoteDataSourseImp.getInstance()));

        homePresenter.getCategories();
        homePresenter.getRandomMeal();
        homePresenter.getAreas();

        return view;
    }

    @Override
    public void showCategories(List<MealCategoryList.MealCategory> categories) {
        Log.i("allCategories", "showCategories: "+categories);
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(), categories, new OnHomeClickListener() {

            @Override
            public void onCategoryClick(MealCategoryList.MealCategory mealCategory) {
//                HomeFragmentDirections.ActionHomeFragmentToCategoryFragment action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(mealCategory);
                Bundle bundle = new Bundle();
                bundle.putString("category", mealCategory.getStrCategory());
                bundle.putString("type", "c");
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_HomeFragment_to_mealsFragment, bundle);

            }

            @Override
            public void onAreaClick(MealAreaList.MealArea mealCategory) {

            }
        });
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String error) {


    }

    @Override
    public void showRandomMeal(List<MealDTO.Meal> meal) {
        Log.i("randomMeal", "showRandomMeal: "+meal);
        randomMealName.setText(meal.get(0).getStrMeal());
        Glide.with(getContext()).load(meal.get(0).getStrMealThumb()).into(randomMealImage);
    }

    @Override
    public void showRandomMealError(String error) {

    }

    @Override
    public void showAreas(List<MealAreaList.MealArea> areas) {
        Log.i("getAreas", "showAreas: "+areas);
        CountriesAdapter countriesAdapter = new CountriesAdapter(getContext(), areas, new OnHomeClickListener() {
            @Override
            public void onCategoryClick(MealCategoryList.MealCategory mealCategory) {

            }

            @Override
            public void onAreaClick(MealAreaList.MealArea mealCategory) {
                Toast.makeText(getContext(), mealCategory.getArea(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("area", mealCategory.getArea());
                Log.i("getArea", "onAreaClick: "+mealCategory.getArea());

                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_HomeFragment_to_mealsFragment, bundle);

            }
        });
        countriesRecyclerView.setAdapter(countriesAdapter);
        countriesAdapter.notifyDataSetChanged();

    }


    @Override
    public void showAreasError(String error) {

    }
}