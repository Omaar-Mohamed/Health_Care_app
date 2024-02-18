package com.example.healthcareapplication.modules.search.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.healthcareapplication.R;
import com.example.healthcareapplication.model.AppRepo;
import com.example.healthcareapplication.model.db.AppLocalDataSource;
import com.example.healthcareapplication.model.dto.MealDetailDTO;
import com.example.healthcareapplication.model.dto.SearchResult;
import com.example.healthcareapplication.model.network.AppRemoteDataSourseImp;
import com.example.healthcareapplication.modules.search.presenter.SearchPresenter;
import com.example.healthcareapplication.shared.IngrediantAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;


public class SearchFragment extends Fragment implements SearchIview , OnSearchClickListener {
SearchPresenter searchPresenter;
SearchView searchEditText;
RecyclerView searchRecyclerView;
    View view;
    public SearchFragment() {
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
         view = inflater.inflate(R.layout.fragment_search, container, false);
        searchEditText = view.findViewById(R.id.editTextSearch);
        searchRecyclerView = view.findViewById(R.id.recyclerViewSearchResults);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchPresenter = new SearchPresenter(this,  AppRepo.getInstance(AppRemoteDataSourseImp.getInstance(), AppLocalDataSource.getInstance(getContext())) );
        searchPresenter.loadData();
        search();
        return view;
    }

    public void search() {
//        searchPresenter.getSearch("egg");
        Observable<String> queryObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        emitter.onNext(newText);
                        return false;
                    }

                });
            }
        }).debounce(200, TimeUnit.MILLISECONDS);
        queryObservable.subscribe(s -> searchPresenter.getSearch(s));
    }
    @Override
    public void showsearchResult(List<SearchResult> resultsList) {
        SearchRecyclerAdapter adapter = new SearchRecyclerAdapter(getContext(),this ,resultsList);
        searchRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchClick(SearchResult searchResult) {
        Bundle bundle=new Bundle();

        if(searchResult.getType().equals("area"))
        {
            bundle.putString("type","area");
            bundle.putString("area", searchResult.getResult());
            Log.i("checking search result", "onSearchClick: "+searchResult.getResult());
            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_mealsFragment,bundle);

        }
        else if(searchResult.getType().equals("category"))
        {
            bundle.putString("type","category");
            bundle.putString("category", searchResult.getResult());
            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_mealsFragment,bundle);

        }
        else if (searchResult.getType().equals("ingredient")) {
            bundle.putString("type","ingredient");
            bundle.putString("ingredient", searchResult.getResult());
            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_mealsFragment,bundle);
        }
        else if (searchResult.getType().equals("meal"))
        {
            bundle.putString("mealId", searchResult.getResult());
//            showMealDetailsPopup(searchPresenter.getMeal(searchResult.getResult()));
//            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_mealsFragment,bundle);
        }
    }

    private void showMealDetailsPopup(MealDetailDTO.MealItem meal) {
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
        popupWindow.showAtLocation(searchRecyclerView, Gravity.CENTER, 0, 0);

        // Set up button click listener
        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Toggle the image resource between whiteheart and redheart
//                int newImageResource = (imageButtonFavorite.getTag() == null || (int) imageButtonFavorite.getTag() == R.drawable.whiteheart)
//                        ? R.drawable.redheart
//                        : R.drawable.whiteheart;
//
//                imageButtonFavorite.setBackgroundResource(newImageResource);
//                imageButtonFavorite.setTag(newImageResource); // Upda
                if (imageButtonFavorite.getTag() == null || (int) imageButtonFavorite.getTag() == R.drawable.whiteheart) {
                    Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        meal.setEmail(auth.getCurrentUser().getEmail());
                        searchPresenter.insertFavMeal(meal);
                        imageButtonFavorite.setBackgroundResource(R.drawable.redheart);
                        imageButtonFavorite.setTag(R.drawable.redheart);
                    } else {
                        Toast.makeText(getContext(), "Please login to add to favorites", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
                    imageButtonFavorite.setBackgroundResource(R.drawable.whiteheart);
                    imageButtonFavorite.setTag(R.drawable.whiteheart);
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