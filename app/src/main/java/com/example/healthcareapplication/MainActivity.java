package com.example.healthcareapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

import java.util.Objects;

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
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                if (destination.getId() == R.id.login || destination.getId() == R.id.signupFragment) {
                    // Hide and disable the bottom navigation view
                    bottomNavigationView.setVisibility(View.GONE);
                    bottomNavigationView.setEnabled(false);
                    appToolbar.getMenu().clear();

                    // Hide the Toolbar
                    appToolbar.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.mealsFragment) {
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
                            if (mAuth.getCurrentUser() != null) {
                                // Show the Toolbar
                                String userEmail = mAuth.getCurrentUser().getEmail();
                                String[] parts = userEmail.split("@");
                                appToolbar.setTitle("Hello,"+parts[0]);
                            }
                        }
                    });

                    // Remove menu items
                    appToolbar.getMenu().clear();
                } else {
                    appToolbar.getMenu().clear();

                    // Show and enable the bottom navigation view
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.setEnabled(true);
                    if (mAuth.getCurrentUser() != null) {
                        // Show the Toolbar
                        String userEmail = mAuth.getCurrentUser().getEmail();
                        String[] parts = userEmail.split("@");
                        appToolbar.setTitle("Hello,"+parts[0]);
                    }

                    // Show the Toolbar
                    getMenuInflater().inflate(R.menu.home_toolbar_menu, appToolbar.getMenu());
                    appToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.menu_logout) {
                                // Check if the current user signed in with Google
                                boolean isGoogleSignIn = false;

                                for (UserInfo userInfo : mAuth.getCurrentUser().getProviderData()) {
                                    if (GoogleAuthProvider.PROVIDER_ID.equals(userInfo.getProviderId())) {
                                        isGoogleSignIn = true;
                                        break;
                                    }
                                }

                                // Handle logout
                                mAuth.signOut();

                                if (isGoogleSignIn) {
                                    // Revoke access from Google Sign-In
                                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                            .requestIdToken(getString(R.string.client_id))
                                            .requestEmail()
                                            .build();

                                    GoogleSignIn.getClient(MainActivity.this, gso).revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Navigate to the login screen after successful logout
                                                navController.navigate(R.id.login);
                                                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // Handle the failure to revoke access
                                                Toast.makeText(MainActivity.this, "Failed to revoke access", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    // If not a Google sign-in user, simply navigate to the login screen
                                    navController.navigate(R.id.login);
                                    Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                                }

                                return true;
                            } else if (item.getItemId() == R.id.menu_search) {
                                // Handle settings
                                Toast.makeText(MainActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.searchFragment);
                                // Show and enable the bottom navigation view
                                bottomNavigationView.setVisibility(View.GONE);
                                bottomNavigationView.setEnabled(false);

                                // Show the Toolbar
                                appToolbar.setVisibility(View.VISIBLE);
                                appToolbar.setTitle("Search");
                                appToolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
                                appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        navController.popBackStack();
                                        appToolbar.setNavigationIcon(null);
                                        if (mAuth.getCurrentUser() != null) {
                                            // Show the Toolbar
                                            String userEmail = mAuth.getCurrentUser().getEmail();
                                            String[] parts = userEmail.split("@");
                                            appToolbar.setTitle("Hello,"+parts[0]);
                                        }
                                    }
                                });

                                // Remove menu items
                                appToolbar.getMenu().clear();
                                return true;
                            }
                            return false;
                        }

                        });
                    appToolbar.setVisibility(View.VISIBLE);
                }
            }

        });
    }


}
