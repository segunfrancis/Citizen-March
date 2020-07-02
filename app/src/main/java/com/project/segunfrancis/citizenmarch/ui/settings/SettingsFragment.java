package com.project.segunfrancis.citizenmarch.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.project.segunfrancis.citizenmarch.R;
import com.project.segunfrancis.citizenmarch.databinding.FragmentSettingsBinding;
import com.project.segunfrancis.citizenmarch.pojo.User;

public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentSettingsBinding binding = FragmentSettingsBinding.inflate(inflater);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preference_fragment, new MyPreferenceFragment())
                .commit();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        User user = new User();
        user.setName(auth.getCurrentUser().getDisplayName());
        user.setProfilePhotoUrl(auth.getCurrentUser().getPhotoUrl().toString());
        user.setUserId(auth.getCurrentUser().getUid());
        user.setEmail(auth.getCurrentUser().getEmail());
        binding.setUser(user);
        return binding.getRoot();
    }
}
