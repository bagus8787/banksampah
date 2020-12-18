package com.sahitya.banksampahsahitya.admin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.repositories.PointRepository;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.ProfileViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdminFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeAdminFragment extends Fragment {

    ProfileViewModel profileViewModel;
    SharedPrefManager sharedPrefManager;
    PointRepository pointRepository;

    ApiInterface apiInterface;

    public HomeAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home_admin, container, false);

        View rootView = inflater.inflate(R.layout.fragment_home_admin, container, false);

        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefManager = new SharedPrefManager(getActivity());
        pointRepository = new PointRepository();
        TextView txt_point_admin = view.findViewById(R.id.txt_point_admin);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().hide();
        }

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();

        profileViewModel.getProfileResponseLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User userResponse) {
                if (userResponse != null) {
                    sharedPrefManager.setSpAvatar(userResponse.getAvatarLocation());
                    if (userResponse.getWarga() != null) {
                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_POINT_TOTAL, userResponse.getWarga().getPointTotal());
//                        tv_saldo.setText(Integer.toString(userResponse.getWarga().getPointTotal()));
                    }
                }
            }
        });

        profileViewModel.getProfile();
        pointRepository.getAdminPoints();

        txt_point_admin.setText("Rp. " + sharedPrefManager.getAdminPointTotal().toString());

    }
}