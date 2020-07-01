package com.project.segunfrancis.citizenmarch.ui.home;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.segunfrancis.citizenmarch.pojo.March;
import com.project.segunfrancis.citizenmarch.utility.States;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCHES_DATABASE_REFERENCE;

public class HomeViewModel extends ViewModel {

    private DatabaseReference mReference;
    private MutableLiveData<States> _loadState = new MutableLiveData<>();
    private MutableLiveData<List<March>> _marches = new MutableLiveData<>();

    public HomeViewModel() {
        mReference = FirebaseDatabase.getInstance().getReference(MARCHES_DATABASE_REFERENCE);
        getMarchesFromFirebase();
    }

    LiveData<States> loadState() {
        return _loadState;
    }

    LiveData<List<March>> marches() {
        return _marches;
    }

    private void getMarchesFromFirebase() {
        List<March> marches = new ArrayList<>();
        _loadState.setValue(States.LOADING);
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                marches.add(snapshot.getValue(March.class));
                _marches.setValue(marches);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}