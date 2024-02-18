package com.example.healthcareapplication.modules.login.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcareapplication.MainActivity;
import com.example.healthcareapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class Login extends Fragment {
    MainActivity appActivity;
FirebaseAuth mAuth;

Button guestbtn;
private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == MainActivity.RESULT_OK) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "User logged in successfully", Toast.LENGTH_SHORT).show();
                            if (account != null) {
                                // Navigate to home fragment
                                NavHostFragment.findNavController(Login.this).navigate(R.id.action_login_to_homeFragment);
                            }
                        } else {
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
        }
    }

});
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // Navigate to home fragment
            NavHostFragment.findNavController(Login.this).navigate(R.id.action_login_to_homeFragment);
        }
    }

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
        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(getContext());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        GoogleSignIn.getClient(requireActivity(), gso);
        mAuth = FirebaseAuth.getInstance();
        EditText etEmail = rootView.findViewById(R.id.editTextUsername);
        EditText etPassword = rootView.findViewById(R.id.editTextPassword);
        TextView tvNextToSignUp = rootView.findViewById(R.id.tvNextToSignUp);
        Button btnLogin = rootView.findViewById(R.id.btnLogin);
        ImageView googleImage = rootView.findViewById(R.id.imageViewGoogle);

        guestbtn = rootView.findViewById(R.id.myButton);
        guestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Login.this).navigate(R.id.action_login_to_homeFragment);
            }
        });
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav);
//        bottomNavigationView.setVisibility(View.GONE);

            googleImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signInIntent = GoogleSignIn.getClient(requireActivity(), gso).getSignInIntent();
                    activityResultLauncher.launch(signInIntent);
                }
            });
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
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                 if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Email is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Password is required", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Navigate to home fragment
                     mAuth.signInWithEmailAndPassword(email, password)
                             .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {
                                         // Sign in success, update UI with the signed-in user's information
                                            Toast.makeText(getContext(), "User logged in successfully", Toast.LENGTH_SHORT).show();
                                         NavHostFragment.findNavController(Login.this).navigate(R.id.action_login_to_homeFragment);

                                     } else {
                                         // If sign in fails, display a message to the user.
                                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                }

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