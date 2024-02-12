package com.example.healthcareapplication.modules.meals.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        recyclerView.setHasFixedSize(true);

        // Use GridLayoutManager instead of LinearLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // 2 columns, adjust as needed
        recyclerView.setLayoutManager(gridLayoutManager);

        mealsPresenter = new MealsPresenter(this, AppRepo.getInstance(AppRemoteDataSourseImp.getInstance()));
        Bundle args = getArguments();
        if (args != null) {
            String type = args.getString("type");
            String category = args.getString("category");
            String area = args.getString("area");

            if (category != null) {
                mealsPresenter.getMealsByCategory(type, category);
            } else if (area != null) {
                mealsPresenter.getMealsByArea(type, area);
            }
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
                Toast.makeText(getContext(), "Meal Clicked", Toast.LENGTH_SHORT).show();
                mealsPresenter.getMealById(meal.getMealId());
            }
        });
        recyclerView.setAdapter(mealsGridAdapter);
        mealsGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMealDetail(List<MealDetailDTO.MealItem> meal) {
        Log.i("mealDetail", "showMealDetail: "+meal);
        showMealDetailsPopup(meal.get(0));

    }


    private void showMealDetailsPopup(MealDetailDTO.MealItem meal) {


        View popupView = getLayoutInflater().inflate(R.layout.popup_meal_details, null);

        TextView textViewMealName = popupView.findViewById(R.id.textViewMealName);
        TextView textViewMealCategory = popupView.findViewById(R.id.textViewMealCategory);
        TextView textViewMealArea = popupView.findViewById(R.id.textViewMealArea);
        TextView textViewMealDescription = popupView.findViewById(R.id.textViewMealDescription);
        ImageButton imageButtonFavorite = popupView.findViewById(R.id.imageButtonFavorite);
        VideoView videoView = popupView.findViewById(R.id.videoView);

        // Set meal details
        ImageView imageViewMeal = popupView.findViewById(R.id.imageViewMeal);
        Glide.with(getContext()).load(meal.getStrMealThumb()).into(imageViewMeal);

        textViewMealName.setText(meal.getStrMeal());
        textViewMealCategory.setText("Category: " + meal.getStrCategory());
        textViewMealArea.setText("Area: " + meal.getStrArea());
        textViewMealDescription.setText(meal.getStrInstructions());

        // Initialize your videoView (you may need to load a video URL)
        // videoView.setVideoURI(Uri.parse("your_video_url_here"));

        // Calculate the dimensions for the popup
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        int popupWidth = (int) (screenWidth * 0.85); // 75% of screen width
        int popupHeight = (int) (screenHeight * 0.95); // 75% of screen height

        // Create PopupWindow with calculated dimensions
        PopupWindow popupWindow = new PopupWindow(popupView, popupWidth, popupHeight, true);
        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);

        // Set up button click listener
        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();

                // Toggle the image resource between whiteheart and redheart
                int newImageResource = (imageButtonFavorite.getTag() == null || (int) imageButtonFavorite.getTag() == R.drawable.whiteheart)
                        ? R.drawable.redheart
                        : R.drawable.whiteheart;

                imageButtonFavorite.setBackgroundResource(newImageResource);
                imageButtonFavorite.setTag(newImageResource); // Upda
            }
        });
    }



}