package com.project.segunfrancis.citizenmarch.ui.createMarch;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.segunfrancis.citizenmarch.networkutils.AddMarchToDatabase;
import com.project.segunfrancis.citizenmarch.networkutils.CurrentUser;
import com.project.segunfrancis.citizenmarch.networkutils.ImageUpload;
import com.project.segunfrancis.citizenmarch.pojo.March;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.FIREBASE_STORAGE_REFERENCE;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCHES_DATABASE_REFERENCE;

/**
 * Created by SegunFrancis
 */
final class FirebaseNetworkCall {

    private final DatabaseReference mReference;
    private final FirebaseAuth mAuth;

    FirebaseNetworkCall() {
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

    void uploadImageToFirebaseStorage(Bitmap bitmap, String imagePath, ImageUpload imageUpload) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
        byte[] data = outputStream.toByteArray();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference(FIREBASE_STORAGE_REFERENCE);
        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(urlTask -> {
                    if (urlTask.isSuccessful()) {
                        imageUpload.onSuccess(urlTask.getResult().toString());
                    } else {
                        imageUpload.onError(urlTask.getException());
                    }
                });
            } else {
                imageUpload.onError(task.getException());
            }
        });
    }
}