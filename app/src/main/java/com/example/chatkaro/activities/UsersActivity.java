package com.example.chatkaro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatkaro.adapters.UsersAdapter;
import com.example.chatkaro.databinding.ActivityUsersBinding;
import com.example.chatkaro.listeners.UserListener;
import com.example.chatkaro.models.User;
import com.example.chatkaro.utilities.Constants;
import com.example.chatkaro.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends BaseActivity implements UserListener {
    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        getUsers();
        setListeners();
    }

    public void setListeners(){
        binding.imageBack.setOnClickListener(view -> onBackPressed());
    }
    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s","No user Available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void getUsers(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult()!=null){
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if (currentUserId.equals(queryDocumentSnapshot.getId())){ // if the listItem is equal to the current person you are logged in in as then that list item will not be showed
                                continue; // because you're name cannot be in that add button list
                            }
                            User userObj = new User();
                            userObj.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            userObj.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            userObj.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            userObj.id = queryDocumentSnapshot.getId();
                            userObj.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            users.add(userObj);
                        }
                        if (users.size()>0){
                            UsersAdapter usersAdapter = new UsersAdapter(users,this);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        }
                        else {
                            showErrorMessage();
                        }
                    }
                    else {
                        showErrorMessage();
                    }

                });
    }

    private void loading(Boolean isLoading){
        if (isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER,user);
        startActivity(intent);
        finish();
    }
}