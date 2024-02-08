package com.example.healthcareapplication.modules.login.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthcareapplication.MainActivity;
import com.example.healthcareapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Login extends Fragment {
    MainActivity appActivity;


    public Login() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        TextView tvNextToSignUp = rootView.findViewById(R.id.tvNextToSignUp);
        Button btnLogin = rootView.findViewById(R.id.btnLogin);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav);
//        bottomNavigationView.setVisibility(View.GONE);


        // Set click listener on the TextView
        tvNextToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click, navigate to sign-up fragment
                navigateToSignUpFragment();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Login.this).navigate(R.id.action_login_to_homeFragment);


            }
        });

        return rootView;
    }

    private void navigateToSignUpFragment() {
        // Use Navigation component to navigate to the sign-up fragment
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_login_to_signupFragment);
    }
}