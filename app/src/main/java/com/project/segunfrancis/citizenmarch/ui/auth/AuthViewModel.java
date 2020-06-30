package com.project.segunfrancis.citizenmarch.ui.auth;

import com.project.segunfrancis.citizenmarch.utility.States;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by SegunFrancis
 */

public class AuthViewModel extends ViewModel {
    private MutableLiveData<States> _signInProcess = new MutableLiveData<>();

    public LiveData<States> signInProcess() {
        return _signInProcess;
    }

    void signIn() {
        _signInProcess.setValue(States.LOADING);

    }
}
