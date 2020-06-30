package com.project.segunfrancis.citizenmarch.ui.createMarch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.project.segunfrancis.citizenmarch.databinding.FragmentCreateMarchBinding;
import com.project.segunfrancis.citizenmarch.pojo.March;
import com.project.segunfrancis.citizenmarch.utility.States;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateMarchFragment extends Fragment {

    private FragmentCreateMarchBinding mBinding;
    private CreateMarchViewModel mViewModel;

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
        mBinding.buttonCreateMarch.setOnClickListener(v -> {
            mViewModel.currentUserName().observe(getViewLifecycleOwner(), displayName -> {
                March march = new March();
                march.setCreatedBy(displayName);
                march.setDescription(mBinding.marchDescriptionET.getText().toString());
                march.setDate(mBinding.marchDate.getText().toString());
                march.setTime(mBinding.marchTime.getText().toString());
                march.setTitle(mBinding.marchTitleET.getText().toString());
                march.setHashTags(mBinding.marchDescriptionET.getText().toString());
                mViewModel.createMarch(march);
            });
        });
        mViewModel.createMarchProgress().observe(getViewLifecycleOwner(), states -> {
            switch (states) {
                case SUCCESS: {}
                case LOADING: {}
                case ERROR: {}
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
                mBinding.marchTime.setText(hour + " : " + minute);
            });
            fragment.show(getChildFragmentManager(), "timePicker");
        });
    }



    private void displaySnackBar(String message) {
        Snackbar.make(mBinding.createMarchScrollView, message, Snackbar.LENGTH_LONG).show();
    }
}
