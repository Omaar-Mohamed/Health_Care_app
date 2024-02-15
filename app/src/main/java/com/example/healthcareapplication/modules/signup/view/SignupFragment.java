package com.example.healthcareapplication.modules.signup.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcareapplication.R;
import com.example.healthcareapplication.modules.login.view.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;


public class SignupFragment extends Fragment {
    public static final Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private static final Pattern  Password_Pattern = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");
FirebaseAuth mAuth ;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // Navigate to home fragment
            NavHostFragment.findNavController(SignupFragment.this).navigate(R.id.action_signupFragment_to_HomeFragment);
        }
    }

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

        mAuth = FirebaseAuth.getInstance();

//        EditText etName = rootView.findViewById(R.id.editTextUsername);
        EditText etEmail = rootView.findViewById(R.id.editTextEmail);
        EditText etPassword = rootView.findViewById(R.id.editTextPassword);
        Button btnSignUp = rootView.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click, navigate to home fragment
//        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
//        if (TextUtils.isEmpty(name)) {
//            Toast.makeText(getContext(), "Name is required", Toast.LENGTH_SHORT).show();
//        }
         if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Email is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Password is required", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
             etEmail.setError("Please enter a valid email address");
         } else if (!Password_Pattern.matcher(password).matches()) {
             etPassword.setError("Password must contain at least 1 digit, 1 lower case letter, 1 upper case letter, 1 special character, no white spaces, and at least 4 characters");
         } else {
            // Navigate to home fragment
            mAuth.createUserWithEmailAndPassword(email, password )
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                             Toast.makeText(getContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                                // Navigate to home fragment
                                NavHostFragment.findNavController(SignupFragment.this).navigate(R.id.action_signupFragment_to_login);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
        }
            }
        });

        TextView tvNextToSignUp = rootView.findViewById(R.id.tvNextToSignIn);

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