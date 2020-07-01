package com.project.segunfrancis.citizenmarch.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.segunfrancis.citizenmarch.R;
import com.project.segunfrancis.citizenmarch.pojo.March;
import com.project.segunfrancis.citizenmarch.utility.States;

import java.util.List;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        homeViewModel.loadState().observe(getViewLifecycleOwner(), states -> {
            switch (states) {
                case LOADING: {
                }
                case SUCCESS: {
                }
                case ERROR: {
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

    }
}
