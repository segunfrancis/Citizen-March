package com.project.segunfrancis.citizenmarch.ui.createMarch;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.segunfrancis.citizenmarch.pojo.March;
import com.project.segunfrancis.citizenmarch.utility.States;

import java.io.ByteArrayOutputStream;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.FIREBASE_STORAGE_REFERENCE;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCHES_DATABASE_REFERENCE;

/**
 * Created by SegunFrancis
 */
public class CreateMarchViewModel extends ViewModel {
    private final DatabaseReference mReference;
    private final FirebaseAuth mAuth;
    private MutableLiveData<States> _createMarchProgress = new MutableLiveData<>();
    private MutableLiveData<String> _createMarchMessage = new MutableLiveData<>();
    private String _currentUserName = "";
    private MutableLiveData<Bitmap> _imageBitmap = new MutableLiveData<>();

    LiveData<States> createMarchProgress() {
        return _createMarchProgress;
    }

    LiveData<String> createMarchMessage() {
        return _createMarchMessage;
    }

    String currentUserName() {
        return _currentUserName;
    }

    LiveData<Bitmap> imageBitmap() {
        return _imageBitmap;
    }

    public CreateMarchViewModel() {
        mReference = FirebaseDatabase.getInstance().getReference(MARCHES_DATABASE_REFERENCE);
        mAuth = FirebaseAuth.getInstance();
        currentUser();
    }

    void setImageBitmap(Bitmap bitmap) {
        _imageBitmap.setValue(bitmap);
    }

    private void currentUser() {
        _currentUserName = mAuth.getCurrentUser().getDisplayName();
    }

    void uploadImageToFirebaseStorage(Bitmap bitmap, String imagePath, March march) {
        _createMarchMessage.setValue("Creating March...");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
        byte[] data = outputStream.toByteArray();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference(FIREBASE_STORAGE_REFERENCE + imagePath);
        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(urlTask -> {
                    if (urlTask.isSuccessful()) {
                        _createMarchMessage.setValue("Successfully uploaded image...");
                        _createMarchProgress.setValue(States.SUCCESS);
                        String imageUrl = urlTask.getResult().toString();
                        // Add image url to Firebase
                        march.setMarchPhotoUrl(imageUrl);
                        createMarch(march);
                    } else {
                        _createMarchProgress.setValue(States.ERROR);
                        _createMarchMessage.setValue(urlTask.getException().getLocalizedMessage());
                    }
                });
            } else {
                _createMarchProgress.setValue(States.ERROR);
                _createMarchMessage.setValue(task.getException().getLocalizedMessage());
            }
        });
    }

    private void createMarch(March march) {
        _createMarchProgress.setValue(States.LOADING);
        String key = mReference.push().getKey();
        march.setMarchId(key);
        mReference.push().setValue(march).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                _createMarchProgress.setValue(States.SUCCESS);
                _createMarchMessage.setValue("Successfully created");
            } else {
                _createMarchProgress.setValue(States.ERROR);
                _createMarchMessage.setValue(task.getException().getLocalizedMessage());
            }
        });
    }
}
