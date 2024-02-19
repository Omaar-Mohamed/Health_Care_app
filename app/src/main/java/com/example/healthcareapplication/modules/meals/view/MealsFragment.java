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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.db.AppLocalDataSource;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.MealListDto;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;
import com.example.healthcareapplication.modules.meals.presenter.MealsPresenter;
import com.example.healthcareapplication.shared.IngrediantAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealsFragment extends Fragment implements MealsIview {
    MealsPresenter mealsPresenter;
    RecyclerView recyclerView;

    ProgressBar progressBar;
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
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);

        // Use GridLayoutManager instead of LinearLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // 2 columns, adjust as needed
        recyclerView.setLayoutManager(gridLayoutManager);

        mealsPresenter = new MealsPresenter(this, AppRepo.getInstance(AppRemoteDataSourseImp.getInstance() , AppLocalDataSource.getInstance(getContext() )));
        Bundle args = getArguments();
        if (args != null) {
            String type = args.getString("type");
            String category = args.getString("category");
            String area = args.getString("area");
            String mealId = args.getString("mealId");

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
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMealDetail(List<MealDetailDTO.MealItem> meal) {
        Log.i("mealDetail", "showMealDetail: "+meal);
        showMealDetailsPopup(meal.get(0));

    }


    private void showMealDetailsPopup(MealDetailDTO.MealItem meal) {
        ArrayList <String>
        ingredientsList=new ArrayList<String>();
        if(meal.getStrIngredient1()!=null)
            ingredientsList.add(meal.getStrMeasure1());
        if(meal.getStrIngredient2()!=null)
            ingredientsList.add(meal.getStrIngredient2());
        if(meal.getStrIngredient3()!=null)
            ingredientsList.add(meal.getStrIngredient3());
        if(meal.getStrIngredient4()!=null)
            ingredientsList.add(meal.getStrIngredient4());
        if(meal.getStrIngredient5()!=null)
            ingredientsList.add(meal.getStrIngredient5());
        if(meal.getStrIngredient6()!=null)
            ingredientsList.add(meal.getStrIngredient6());
        if(meal.getStrIngredient7()!=null)
            ingredientsList.add(meal.getStrIngredient7());
        if(meal.getStrIngredient8()!=null)
            ingredientsList.add(meal.getStrIngredient8());
        if(meal.getStrIngredient9()!=null)
            ingredientsList.add(meal.getStrIngredient9());
        if(meal.getStrIngredient10()!=null)
            ingredientsList.add(meal.getStrIngredient10());
        if(meal.getStrIngredient11()!=null)
            ingredientsList.add(meal.getStrIngredient11());
        if(meal.getStrIngredient12()!=null)
            ingredientsList.add(meal.getStrIngredient12());
        if(meal.getStrIngredient13()!=null)
            ingredientsList.add(meal.getStrIngredient13());
        if(meal.getStrIngredient14()!=null)
            ingredientsList.add(meal.getStrIngredient14());
        if(meal.getStrIngredient15()!=null)
            ingredientsList.add(meal.getStrIngredient15());
        if(meal.getStrIngredient16()!=null)
            ingredientsList.add(meal.getStrIngredient16());
        if(meal.getStrIngredient17()!=null)
            ingredientsList.add(meal.getStrIngredient17());
        if(meal.getStrIngredient18()!=null)
            ingredientsList.add(meal.getStrIngredient18());
        if(meal.getStrIngredient19()!=null)
            ingredientsList.add(meal.getStrIngredient19());
        if(meal.getStrIngredient20()!=null)
            ingredientsList.add(meal.getStrIngredient20());
        ingredientsList.removeIf(ing -> ing.equals(""));

        View popupView = getLayoutInflater().inflate(R.layout.popup_meal_details, null);

        TextView textViewMealName = popupView.findViewById(R.id.textViewMealName);
        TextView textViewMealCategory = popupView.findViewById(R.id.textViewMealCategory);
        TextView textViewMealArea = popupView.findViewById(R.id.textViewMealArea);
        TextView textViewMealDescription = popupView.findViewById(R.id.textViewMealDescription);
        ImageButton imageButtonFavorite = popupView.findViewById(R.id.imageButtonFavorite);
        WebView webView = popupView.findViewById(R.id.webView);

        // Set up the RecyclerView for the ingredients
        RecyclerView recyclerView1 = popupView.findViewById(R.id.recyclerViewIngredients);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setAdapter(new IngrediantAdapter(ingredientsList, getContext()));
        recyclerView1.getAdapter().notifyDataSetChanged();

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
        imageButtonFavorite.setBackground(null);

        // Set up button click listener
        if (meal!=null) {
            mealsPresenter.checkMealExistInFav(meal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(isExist -> {
                        if (isExist) {
                            imageButtonFavorite.setBackgroundResource(R.drawable.redheart);
                            imageButtonFavorite.setTag(R.drawable.redheart);
                        } else {
                            imageButtonFavorite.setBackgroundResource(R.drawable.whiteheart);
                            imageButtonFavorite.setTag(R.drawable.whiteheart);
                        }
                    });
        }
        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meal != null) {
                    mealsPresenter.checkMealExistInFav(meal)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(isExist -> {
                                if (isExist) {
                                    // Meal exists in favorites, remove it
                                    Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
                                    mealsPresenter.deleteFavMeal(meal);
                                    imageButtonFavorite.setBackgroundResource(R.drawable.whiteheart);
                                    imageButtonFavorite.setTag(R.drawable.whiteheart);
                                } else {
                                    // Meal doesn't exist in favorites, add it
                                    Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                        meal.setEmail(auth.getCurrentUser().getEmail());
                                        mealsPresenter.insertFavMeal(meal);
                                        imageButtonFavorite.setBackgroundResource(R.drawable.redheart);
                                        imageButtonFavorite.setTag(R.drawable.redheart);
                                    } else {
                                        Toast.makeText(getContext(), "Please login to add to favorites", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        Log.d("TAG", "setMealVideo: width " +webView.getX());
        String videoUrl = convertToEmbeddedUrl(meal.getStrYoutube()); // Replace VIDEO_ID with the actual video ID or embed URL
        String video="<iframe width='400' height=\"200\" src= '"+videoUrl+"' title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript (required for video playback)
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                adjustIframeWidth(view);
            }
        });
        webView.loadData(video, " text/html", "utf-8"); // Load the HTML content into the WebView
//        webView.loadUrl(url);

    }

    public static String convertToEmbeddedUrl(String youtubeUrl) {
        String videoId = extractVideoId(youtubeUrl);
        return "https://www.youtube.com/embed/" + videoId;
    }

    private static String extractVideoId(String youtubeUrl) {
        String videoId = null;
        String regex = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2Fvideos%2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(youtubeUrl);
        if (matcher.find()) {
            videoId = matcher.group();
        }

        return videoId;
    }

    private void adjustIframeWidth(WebView webView) {
        webView.evaluateJavascript("javascript:(function() { " +
                "var iframes = document.getElementsByTagName('iframe');" +
                "for (var i = 0; i < iframes.length; i++) {" +
                "    var iframe = iframes[i];" +
                "    iframe.style.width = '100%';" +
                "}})();", null);
    }



}