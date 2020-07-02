package com.project.segunfrancis.citizenmarch.ui.marchdetails;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.segunfrancis.citizenmarch.pojo.March;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCHES_DATABASE_REFERENCE;

/**
 * Created by SegunFrancis
 */
public class MarchDetailsViewModel extends ViewModel {
    private MutableLiveData<March> march = new MutableLiveData<>();
    private MutableLiveData<Boolean> _isAttending = new MutableLiveData<>();

    public MarchDetailsViewModel() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(MARCHES_DATABASE_REFERENCE);
    }

    public void setMarch(March march) {
        this.march.setValue(march);
    }

    LiveData<Boolean> getIsAttending() {
        return _isAttending;
    }

    void setIsAttending(boolean isAttending) {
        _isAttending.setValue(isAttending);
    }
}
