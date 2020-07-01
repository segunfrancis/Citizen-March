package com.project.segunfrancis.citizenmarch.ui.marchdetails;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.ViewModel;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCHES_DATABASE_REFERENCE;

/**
 * Created by SegunFrancis
 */
public class MarchDetailsViewModel extends ViewModel {
    DatabaseReference mReference;

    public MarchDetailsViewModel() {
        mReference = FirebaseDatabase.getInstance().getReference(MARCHES_DATABASE_REFERENCE);
    }

    void attend() {

    }
}
