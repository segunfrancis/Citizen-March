package com.project.segunfrancis.citizenmarch.ui.marchdetails;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.segunfrancis.citizenmarch.pojo.March;
import com.project.segunfrancis.citizenmarch.pojo.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCHES_DATABASE_REFERENCE;

/**
 * Created by SegunFrancis
 */
public class MarchDetailsViewModel extends ViewModel {
    DatabaseReference mReference;
    private MutableLiveData<March> march = new MutableLiveData<>();

    public MarchDetailsViewModel() {
        mReference = FirebaseDatabase.getInstance().getReference(MARCHES_DATABASE_REFERENCE);
    }

    public void setMarch(March march) {
        this.march.setValue(march);
    }

    void attend() {

    }
}
