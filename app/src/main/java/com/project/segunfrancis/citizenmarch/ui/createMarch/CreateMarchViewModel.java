package com.project.segunfrancis.citizenmarch.ui.createMarch;

import com.google.firebase.auth.FirebaseUser;
import com.project.segunfrancis.citizenmarch.networkutils.AddMarchToDatabase;
import com.project.segunfrancis.citizenmarch.networkutils.CurrentUser;
import com.project.segunfrancis.citizenmarch.pojo.March;
import com.project.segunfrancis.citizenmarch.utility.States;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by SegunFrancis
 */
public class CreateMarchViewModel extends ViewModel {
    private NetworkCall mNetworkCall;
    private MutableLiveData<States> _createMarchProgress = new MutableLiveData<>();
    private MutableLiveData<String> _createMarchMessage = new MutableLiveData<>();
    private MutableLiveData<String> _currentUserName = new MutableLiveData<>();

    LiveData<States> createMarchProgress() {
        return _createMarchProgress;
    }

    LiveData<String> createMarchMessage() {
        return _createMarchMessage;
    }

    LiveData<String> currentUserName() {
        return _currentUserName;
    }

    public CreateMarchViewModel() {
        mNetworkCall = new NetworkCall();
        getCurrentUserName();
    }

    void createMarch(March march) {
        _createMarchProgress.setValue(States.LOADING);
        mNetworkCall.createMarch(march, new AddMarchToDatabase() {
            @Override
            public void onSuccess(String message) {
                _createMarchProgress.setValue(States.SUCCESS);
                _createMarchMessage.setValue(message);
            }

            @Override
            public void onError(Exception e) {
                _createMarchProgress.setValue(States.ERROR);
                _createMarchMessage.setValue(e.getLocalizedMessage());
            }
        });
    }

    private void getCurrentUserName() {
        mNetworkCall.currentUser(user -> _currentUserName.setValue(user.getDisplayName()));
    }
}
