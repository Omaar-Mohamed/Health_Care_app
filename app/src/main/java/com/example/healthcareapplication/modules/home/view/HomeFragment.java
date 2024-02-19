package com.example.healthcareapplication.modules.home.view;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
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
import com.example.healthcareapplication.model.dto.MealAreaList;
import com.example.healthcareapplication.model.dto.MealCategoryList;
import com.example.healthcareapplication.model.dto.MealDTO;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;
import com.example.healthcareapplication.modules.home.presenter.HomePresenter;
import com.example.healthcareapplication.shared.DialogUtils;
import com.example.healthcareapplication.shared.IngrediantAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HomeFragment extends Fragment implements HomeIview {

    FirebaseAuth auth;
    FirebaseUser user;
    HomePresenter homePresenter;
    RecyclerView categoriesRecyclerView;

    RecyclerView countriesRecyclerView;
    TextView randomMealName;
    ImageView randomMealImage;

    CardView cardView;

    MealDTO.Meal meal;
ProgressBar progressBar;
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

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
//        if (user != null) {
//           Navigation.findNavController(view).navigate(R.id.action_HomeFragment_to_login);
//        }
        categoriesRecyclerView = view.findViewById(R.id.categoriesRV);
        randomMealName = view.findViewById(R.id.textViewMealName);
        randomMealImage = view.findViewById(R.id.imageViewMeal);
        cardView = view.findViewById(R.id.cardDailyInspiration);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMealDetailsPopup(meal);
            }
        });
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
        homePresenter = new HomePresenter(this , AppRepo.getInstance(AppRemoteDataSourseImp.getInstance(), AppLocalDataSource.getInstance(getContext()))  );

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
               if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                   Bundle bundle = new Bundle();
                   bundle.putString("category", mealCategory.getStrCategory());
                   bundle.putString("type", "c");
                   NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_HomeFragment_to_mealsFragment, bundle);

               } else{
                   DialogUtils.showYesNoDialog(getContext(), "Confirmation", "Please sign in. Do you want to proceed?", new DialogUtils.OnYesClickListener() {
                       @Override
                       public void onYesClicked() {
                           // Handle Yes button click
                           // Add your sign-in logic here
                            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_HomeFragment_to_login);
                       }
                   }, new DialogUtils.OnNoClickListener() {
                       @Override
                       public void onNoClicked() {
                           // Handle No button click
                           // Add your logic for not signing in
                       }
                   });
               }

            }

            @Override
            public void onAreaClick(MealAreaList.MealArea mealCategory) {

            }
        });
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesAdapter.notifyDataSetChanged();

        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError(String error) {


    }

    @Override
    public void showRandomMeal(List<MealDTO.Meal> meal) {
        Log.i("randomMeal", "showRandomMeal: "+meal);
        randomMealName.setText(meal.get(0).getStrMeal());
        Glide.with(getContext()).load(meal.get(0).getStrMealThumb()).into(randomMealImage);
        this.meal = meal.get(0);
        progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);

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
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Toast.makeText(getContext(), mealCategory.getArea(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("area", mealCategory.getArea());
                    Log.i("getArea", "onAreaClick: " + mealCategory.getArea());

                    NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_HomeFragment_to_mealsFragment, bundle);
                } else {
                    DialogUtils.showYesNoDialog(getContext(), "Confirmation", "Please sign in. Do you want to proceed?", new DialogUtils.OnYesClickListener() {
                        @Override
                        public void onYesClicked() {
                            // Handle Yes button click
                            // Add your sign-in logic here
                            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_HomeFragment_to_login);
                        }
                    }, new DialogUtils.OnNoClickListener() {
                        @Override
                        public void onNoClicked() {
                            // Handle No button click
                            // Add your logic for not signing in
                        }
                    });
                }
            }
        });
        countriesRecyclerView.setAdapter(countriesAdapter);
        countriesAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);


    }


    @Override
    public void showAreasError(String error) {

    }

    @Override
    public void onRandomMealClick(MealDTO.Meal meal) {

    }

    private void showMealDetailsPopup(MealDTO.Meal meal) {

        ArrayList<String>
                ingredientsList=new ArrayList<String>();
        ingredientsList.add(meal.getStrMeasure1());
        ingredientsList.add(meal.getStrIngredient2());
        ingredientsList.add(meal.getStrIngredient3());
        ingredientsList.add(meal.getStrIngredient4());
        ingredientsList.add(meal.getStrIngredient5());
        ingredientsList.add(meal.getStrIngredient6());
        ingredientsList.add(meal.getStrIngredient7());
        ingredientsList.add(meal.getStrIngredient8());
        ingredientsList.add(meal.getStrIngredient9());
        ingredientsList.add(meal.getStrIngredient10());
        ingredientsList.add(meal.getStrIngredient11());
        ingredientsList.add(meal.getStrIngredient12());
        ingredientsList.add(meal.getStrIngredient13());
        ingredientsList.add(meal.getStrIngredient14());
        ingredientsList.add(meal.getStrIngredient15());
        ingredientsList.add(meal.getStrIngredient16());
        ingredientsList.add(meal.getStrIngredient17());
        ingredientsList.add(meal.getStrIngredient18());
        ingredientsList.add(meal.getStrIngredient19());
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
        popupWindow.showAtLocation(categoriesRecyclerView, Gravity.CENTER, 0, 0);

        // Set up button click listener
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            imageButtonFavorite.setVisibility(View.VISIBLE);
        } else {
            imageButtonFavorite.setVisibility(View.GONE);
        }
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