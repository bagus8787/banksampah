package com.sahitya.banksampahsahitya.admin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.ProfileViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeAdminFragment extends Fragment {

    ProfileViewModel profileViewModel;
    SharedPrefManager sharedPrefManager;

    public HomeAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefManager = new SharedPrefManager(getActivity());

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();

        profileViewModel.getProfile();

    }
}