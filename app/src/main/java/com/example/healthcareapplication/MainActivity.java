package com.example.healthcareapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar appToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        appToolbar = findViewById(R.id.app_toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    // Navigate to HomeFragment
                    navController.navigate(R.id.HomeFragment);
                    return true;
                } else if (item.getItemId() == R.id.navigation_favourite) {
                    // Check if the current destination is not Login or Signup fragment
                    if (navController.getCurrentDestination().getId() != R.id.login &&
                            navController.getCurrentDestination().getId() != R.id.signupFragment) {
                        // Navigate to FavouriteFragment
                        navController.navigate(R.id.FavouriteFragment);
                    }
                    return true;
                }
                // Add conditions for other menu items if needed
                return false;
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.login || destination.getId() == R.id.signupFragment ) {
                    // Hide and disable the bottom navigation view
                    bottomNavigationView.setVisibility(View.GONE);
                    bottomNavigationView.setEnabled(false);

                    // Hide the Toolbar
                    appToolbar.setVisibility(View.GONE);
                }else if (destination.getId() == R.id.mealsFragment) {
                    // Show and enable the bottom navigation view
                    bottomNavigationView.setVisibility(View.GONE);
                    bottomNavigationView.setEnabled(false);

                    // Show the Toolbar
                    appToolbar.setVisibility(View.VISIBLE);
                    appToolbar.setTitle("Meals");
                    appToolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
                    appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            navController.popBackStack();
                            appToolbar.setNavigationIcon(null);
                            appToolbar.setTitle("Hello, Jena!");
                        }
                    });
                }

                else {
                    // Show and enable the bottom navigation view
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.setEnabled(true);

                    // Show the Toolbar
                    appToolbar.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
