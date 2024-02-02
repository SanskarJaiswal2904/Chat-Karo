package com.example.chatkaro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.chatkaro.databinding.ActivitySignInBinding;
import com.example.chatkaro.utilities.Constants;
import com.example.chatkaro.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding; //// creating object binding of activity_sign_in.xml

    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());

        //if user has not logged out then don't need to sign in
        automaticLogIn();

        //else need to sign in
        setContentView(binding.getRoot());
        setListeners();
    }

    private void automaticLogIn(){
        if (preferenceManager.getBoolean(Constants.KEY_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            showToast("Already SignIn");
            startActivity(intent);
            finish();
        }
    }

    private void setListeners() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(binding.textCreateNewAccount.getWindowToken(), 0);
        binding.textCreateNewAccount.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.buttonSignIn.setOnClickListener(v -> {
            if (isValidSignInDetails()){
                SignIn();
            }

        });
    }
    private void SignIn(){
         loading(true);
         FirebaseFirestore database = FirebaseFirestore.getInstance();
         database.collection(Constants.KEY_COLLECTION_USERS)
                 .whereEqualTo(Constants.KEY_EMAIL,binding.inputEmail.getText().toString())
                 .whereEqualTo(Constants.KEY_PASSWORD,binding.inputPassword.getText().toString())
                 .get()
                 .addOnCompleteListener(task -> {
                     if (task.isSuccessful() && task.getResult()!=null
                            && task.getResult().getDocuments().size() > 0 ){
                         DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                         preferenceManager.putBoolean(Constants.KEY_SIGNED_IN,true);
                         preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                         preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                         preferenceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));


                         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         showToast("SignIn Successful");
                         startActivity(intent);
                     }
                     else {
                         loading(false);
                         showToast("Unable to SignIn");
                     }
                 });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignInDetails() {
        if (binding.inputEmail.getText().toString().trim().isEmpty()) {
            showToast("Enter Your Email");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()) { // to check if email is valid
            showToast("Enter Valid Email Address");
            return false;
        }
        if (binding.inputPassword.getText().toString().trim().isEmpty()) {
            showToast("Enter the Password");
            return false;
        }
        return true;
    }
    private void loading(Boolean isLoading){
        if (isLoading){
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else{
            binding.buttonSignIn.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}

