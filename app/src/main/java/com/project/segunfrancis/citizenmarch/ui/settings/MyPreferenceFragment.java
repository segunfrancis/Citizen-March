package com.project.segunfrancis.citizenmarch.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.project.segunfrancis.citizenmarch.R;
import com.project.segunfrancis.citizenmarch.ui.auth.AuthActivity;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Created by SegunFrancis
 */
public class MyPreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
        Preference signOutPref = findPreference("sign_out");
        signOutPref.setOnPreferenceClickListener(preference -> {
            displayDialog();
            return false;
        });
    }

    private void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        requireContext().startActivity(new Intent(requireContext(), AuthActivity.class));
        requireActivity().finish();
    }

    private void displayDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Sign Out")
                .setMessage("Do you want to sign out?")
                .setPositiveButton("YES", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    signOut();
                })
                .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss())
                .create()
                .show();
    }
}
