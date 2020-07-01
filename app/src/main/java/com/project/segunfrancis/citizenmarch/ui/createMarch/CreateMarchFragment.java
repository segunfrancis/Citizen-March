package com.project.segunfrancis.citizenmarch.ui.createMarch;

import android.content.Intent;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.project.segunfrancis.citizenmarch.databinding.FragmentCreateMarchBinding;
import com.project.segunfrancis.citizenmarch.pojo.March;
import com.project.segunfrancis.citizenmarch.utility.States;

import static android.app.Activity.RESULT_OK;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCH_IMAGE_REQUEST_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateMarchFragment extends Fragment {

    private FragmentCreateMarchBinding mBinding;
    private CreateMarchViewModel mViewModel;
    private String imagePath = "";

    public CreateMarchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CreateMarchViewModel.class);
        mBinding = FragmentCreateMarchBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Display selected image
        mViewModel.imageBitmap().observe(getViewLifecycleOwner(), bitmap -> {
            mBinding.marchImage.setImageBitmap(bitmap);
        });

        // Save March to database
        mBinding.buttonCreateMarch.setOnClickListener(v -> {
            if (!areTextFieldsEmpty()) {
                if (!areDateTimeTextEmpty()) {
                    if (hasImageBeenSelected()) {
                        mViewModel.imageBitmap().observe(getViewLifecycleOwner(), bitmap -> {
                            March march = new March();
                            march.setCreatedBy(mViewModel.currentUserName());
                            march.setDescription(mBinding.marchDescriptionET.getText().toString().trim());
                            march.setDate(mBinding.marchDate.getText().toString().trim());
                            march.setTime(mBinding.marchTime.getText().toString().trim());
                            march.setTitle(mBinding.marchTitleET.getText().toString().trim());
                            march.setHashTags(mBinding.marchHashtagET.getText().toString().trim());
                            march.setLocation(mBinding.marchLocationET.getText().toString().trim());

                            mViewModel.uploadImageToFirebaseStorage(bitmap, imagePath, march);
                        });
                    }
                }
            }
        });

        mBinding.addImage.setOnClickListener(v -> openGallery());

        mViewModel.createMarchProgress().observe(getViewLifecycleOwner(), states -> {
            switch (states) {
                case SUCCESS: {
                }
                case LOADING: {
                }
                case ERROR: {
                }
            }
        });
        mViewModel.createMarchMessage().observe(getViewLifecycleOwner(), this::displaySnackBar);

        mBinding.dateLinearLayout.setOnClickListener(v -> {
            DialogFragment fragment = new DatePickerFragment((year, month, day) -> {
                mBinding.marchDate.setText(day + "/" + month + "/" + year);
            });
            fragment.show(getChildFragmentManager(), "datePicker");
        });

        mBinding.timeLinearLayout.setOnClickListener(vi -> {
            DialogFragment fragment = new TimePickerFragment((hour, minute) -> {
                String hour_string;
                String minute_string;
                String timeMessage;
                if (hour > 12) {
                    hour_string = Integer.toString(hour - 12);
                    minute_string = Integer.toString(minute);
                    timeMessage = hour_string + ":" + minute_string + " pm";
                } else {
                    hour_string = Integer.toString(hour);
                    minute_string = Integer.toString(minute);
                    timeMessage = hour_string + ":" + minute_string + " am";
                }
                mBinding.marchTime.setText(timeMessage);
            });
            fragment.show(getChildFragmentManager(), "timePicker");
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, MARCH_IMAGE_REQUEST_CODE);
    }

    private void displaySnackBar(String message) {
        Snackbar.make(mBinding.createMarchScrollView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == MARCH_IMAGE_REQUEST_CODE) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    imagePath = imageUri.getLastPathSegment();
                    if (Build.VERSION.SDK_INT >= 29) {
                        ImageDecoder.Source source = ImageDecoder.createSource(requireActivity().getContentResolver(), imageUri);
                        mViewModel.setImageBitmap(ImageDecoder.decodeBitmap(source));
                    } else {
                        mViewModel.setImageBitmap(MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri));
                    }
                } catch (Exception e) {
                    Log.e("CreateMarchFragment", e.getLocalizedMessage());
                }
            } else {
                displaySnackBar("No Image Selected");
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /*
     * Validations
     * */
    private boolean areTextFieldsEmpty() {
        TextInputEditText[] views = {mBinding.marchTitleET, mBinding.marchDescriptionET, mBinding.marchHashtagET, mBinding.marchLocationET};
        for (TextInputEditText view : views) {
            if (view.getText().toString().trim().isEmpty()) {
                view.setError("This field is required");
                view.requestFocus();
                return true;
            } else {
                view.setError(null);
            }
        }
        return false;
    }

    private boolean areDateTimeTextEmpty() {
        TextView[] textViews = {mBinding.marchDate, mBinding.marchTime};
        for (TextView textView : textViews) {
            if (textView.getText().toString().isEmpty()) {
                displaySnackBar("Please set date and time");
                return true;
            }
        }
        return false;
    }

    private boolean hasImageBeenSelected() {
        if (mViewModel.imageBitmap().getValue() == null) {
            displaySnackBar("Please select an image");
            return false;
        }
        return true;
    }
}
