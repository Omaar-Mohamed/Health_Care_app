package com.example.healthcareapplication.modules.signup.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthcareapplication.R;


public class SignupFragment extends Fragment {



    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);


        TextView tvNextToSignUp = rootView.findViewById(R.id.tvNextToSignIn);

        // Set click listener on the TextView

        tvNextToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click, navigate to sign-up fragment
                navigateToSignUpFragment();
            }
        });

        return rootView;
    }
    private void navigateToSignUpFragment() {
        // Use Navigation component to navigate to the sign-up fragment
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_signupFragment_to_login);
    }
}