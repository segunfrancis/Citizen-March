package com.project.segunfrancis.citizenmarch.ui.createMarch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.segunfrancis.citizenmarch.networkutils.AddMarchToDatabase;
import com.project.segunfrancis.citizenmarch.networkutils.CurrentUser;
import com.project.segunfrancis.citizenmarch.pojo.March;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCHES_DATABASE_REFERENCE;

/**
 * Created by SegunFrancis
 */
final class NetworkCall {

    private final DatabaseReference mReference;
    private final FirebaseAuth mAuth;

    NetworkCall() {
        mReference = FirebaseDatabase.getInstance().getReference(MARCHES_DATABASE_REFERENCE);
        mAuth = FirebaseAuth.getInstance();
    }

    void createMarch(March march, AddMarchToDatabase toDatabase) {
        String key = mReference.push().getKey();
        march.setMarchId(key);
        mReference.push().setValue(march).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                toDatabase.onSuccess("Successfully created");
            } else {
                toDatabase.onError(task.getException());
            }
        });
    }

    void currentUser(CurrentUser user) {
        user.getCurrentUser(mAuth.getCurrentUser());
    }
}
