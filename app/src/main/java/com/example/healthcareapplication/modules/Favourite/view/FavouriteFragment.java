package com.example.healthcareapplication.modules.Favourite.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.db.FavMealsDataSource;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;
import com.example.healthcareapplication.modules.Favourite.presenter.FavMealsPresenter;
import com.example.healthcareapplication.modules.login.view.Login;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouriteFragment extends Fragment implements FavMealsIView{

FavMealsPresenter favMealsPresenter;
RecyclerView recyclerView;
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
                    FavMealsDataSource.getInstance(getContext() ,
                    FirebaseAuth.getInstance().getCurrentUser().getEmail())));

    recyclerView = view.findViewById(R.id.favRV);
    recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        favMealsPresenter.getFavMeals();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favMealsPresenter.getFavMeals();
    }

    @Override
    public void onStart() {
        super.onStart();
        favMealsPresenter.getFavMeals();
    }

    @Override
    public void showFavMeals(Flowable<List<MealDetailDTO.MealItem>> meals) {
        meals.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealItems -> {
                    FavouriteAdapter favouriteAdapter = new FavouriteAdapter(getContext(), meals, new OnFavMealClickListener() {
                        @Override
                        public void onFavMealClick(MealDetailDTO.MealItem mealItem) {

                        }

                        @Override
                        public void onFavMealDelete(MealDetailDTO.MealItem mealItem) {
                            favMealsPresenter.removeFavMeal(mealItem);
                        }
                    });
                    // Do something with the meal items
                    Log.i("showFavMeals", "showFavMeals: "+mealItems.size() + FirebaseAuth.getInstance().getCurrentUser().getEmail());

                    recyclerView.setAdapter(favouriteAdapter);
                    favouriteAdapter.notifyDataSetChanged();
                }

                );
    }

    @Override
    public void showError(String error) {

    }


}