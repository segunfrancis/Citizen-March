package com.project.segunfrancis.citizenmarch.ui.main;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by SegunFrancis
 */
public class MainViewModel extends ViewModel {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private MutableLiveData<String> _name = new MutableLiveData<>();
    private MutableLiveData<String> _email = new MutableLiveData<>();
    private MutableLiveData<String> _profilePhotoUrl = new MutableLiveData<>();

    LiveData<String> name() {
        return _name;
    }

    LiveData<String> email() {
        return _email;
    }

    LiveData<String> profilePhotoUrl() {
        return _profilePhotoUrl;
    }

    public MainViewModel() {
        FirebaseUser user = mAuth.getCurrentUser();
        _name.setValue(user.getDisplayName());
        _email.setValue(user.getEmail());
        _profilePhotoUrl.setValue(user.getPhotoUrl().toString());
        Log.d("MainViewModel", "Name: " + user.getDisplayName());
        Log.d("MainViewModel", "Email: " + user.getEmail());
        Log.d("MainViewModel", "PhotoUrl: " + user.getPhotoUrl());
    }
}
