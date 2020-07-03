package com.project.segunfrancis.citizenmarch.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.project.segunfrancis.citizenmarch.R;
import com.project.segunfrancis.citizenmarch.pojo.March;
import com.project.segunfrancis.citizenmarch.ui.marchdetails.MarchDetailsActivity;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT;

public class HomeFragment extends Fragment implements MarchRecyclerAdapter.OnItemClickListener {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.marches_recyclerView);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        if (getResources().getBoolean(R.bool.isTablet)) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(calculateNoOfColumns(requireContext()), RecyclerView.VERTICAL));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        }
        homeViewModel.loadState().observe(getViewLifecycleOwner(), states -> {
            switch (states) {
                case LOADING: {
                    showProgressBar(progressBar);
                }
                case SUCCESS: {
                    hideProgressBar(progressBar);
                }
                case ERROR: {
                    hideProgressBar(progressBar);
                }
            }
        });
        homeViewModel.marches().observe(getViewLifecycleOwner(), marches -> {
            MarchRecyclerAdapter adapter = new MarchRecyclerAdapter(marches, HomeFragment.this);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onItemClick(March march) {
        startActivity(new Intent(requireContext(), MarchDetailsActivity.class).putExtra(HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT, march));
    }

    private void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    private int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if (noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
}
