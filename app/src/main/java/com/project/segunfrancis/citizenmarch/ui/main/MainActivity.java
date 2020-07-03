package com.project.segunfrancis.citizenmarch.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.project.segunfrancis.citizenmarch.R;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.hideSoftKeyboard;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (!getResources().getBoolean(R.bool.isTablet)) {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_create_march, R.id.nav_settings)
                    .setOpenableLayout(drawer)
                    .build();
            drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(@NonNull View drawerView) {
                    drawerView.bringToFront();
                    hideSoftKeyboard(MainActivity.this);
                }

                @Override
                public void onDrawerClosed(@NonNull View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        } else {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_create_march, R.id.nav_settings)
                    .build();
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Inflate navigation drawer header view
        View header = navigationView.getHeaderView(0);
        CircleImageView imageView = header.findViewById(R.id.profile_imageView);
        TextView name = header.findViewById(R.id.profile_name);
        TextView email = header.findViewById(R.id.profile_email);
        viewModel.name().observe(this, name::setText);
        viewModel.email().observe(this, email::setText);
        viewModel.profilePhotoUrl().observe(this, s ->
                Glide.with(getApplicationContext())
                        .load(s)
                        .placeholder(R.drawable.default_profile_image)
                        .error(R.drawable.default_profile_image)
                        .into(imageView));
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (mNavController.getCurrentDestination() != null)
            if (mNavController.getCurrentDestination().getId() == R.id.nav_home)
                displayDialog();
            else
                super.onBackPressed();
    }

    private void displayDialog() {
        new MaterialAlertDialogBuilder(this).setTitle(getResources().getString(R.string.exit_dialog_title))
                .setMessage(getResources().getString(R.string.exit_dialog_message))
                .setPositiveButton(getResources().getString(R.string.positive_dialog_title), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    System.exit(0);
                })
                .setNegativeButton(getResources().getString(R.string.negative_dialog_title), (dialogInterface, i) -> dialogInterface.dismiss())
                .create().show();
    }

    private void displaySnackBar(String message) {
        Snackbar.make(findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG).show();
    }
}
