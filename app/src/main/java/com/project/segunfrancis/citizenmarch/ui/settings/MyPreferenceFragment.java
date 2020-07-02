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
        Preference aboutAppPref = findPreference("about_app");
        Preference aboutDevPref = findPreference("about_developer");
        signOutPref.setOnPreferenceClickListener(preference -> {
            displaySignOutDialog();
            return false;
        });
        aboutAppPref.setOnPreferenceClickListener(preference -> {
            displayAboutDialog(getResources().getString(R.string.about_app), getResources().getString(R.string.about_app_message));
            return false;
        });
        aboutDevPref.setOnPreferenceClickListener(preference -> {
            displayAboutDialog(getResources().getString(R.string.about_developer), getResources().getString(R.string.about_developer_message));
            return false;
        });
    }

    private void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        requireContext().startActivity(new Intent(requireContext(), AuthActivity.class));
        requireActivity().finish();
    }

    private void displaySignOutDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getResources().getString(R.string.menu_sign_out))
                .setMessage(getResources().getString(R.string.sign_out_message))
                .setPositiveButton(getResources().getString(R.string.positive_dialog_title), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    signOut();
                })
                .setNegativeButton(getResources().getString(R.string.negative_dialog_title), (dialogInterface, i) -> dialogInterface.dismiss())
                .create()
                .show();
    }

    private void displayAboutDialog(String title, String message) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(getResources().getString(R.string.neutral_dialog_title), ((dialogInterface, i) -> dialogInterface.dismiss()))
                .create()
                .show();
    }
}
