package com.sahitya.banksampahsahitya.coordinator.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.model.BarcodeImage;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.BarangViewModel;
import com.sahitya.banksampahsahitya.viewmodels.ProfileViewModel;

import java.util.ArrayList;

public class HomeCoordinatorFragment extends Fragment {

    ProfileViewModel profileViewModel;
    SharedPrefManager sharedPrefManager;

    public HomeCoordinatorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_coordinator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView tv_name = view.findViewById(R.id.tv_koor_name);
        final TextView tv_saldo = view.findViewById(R.id.tv_koor_saldo);
        final ImageView image_barcode = view.findViewById(R.id.image_koor_barcode);

        sharedPrefManager = new SharedPrefManager(getActivity());

        tv_name.setText(sharedPrefManager.getSPNama());

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();
        profileViewModel.getProfileResponseLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User userResponse) {
                if (userResponse != null) {
                    if (userResponse.getWarga() != null) {
                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_POINT_TOTAL, userResponse.getWarga().getPointTotal());
                        tv_saldo.setText(Integer.toString(userResponse.getWarga().getPointTotal()));
                    }
                }
            }
        });

        profileViewModel.getBarcodeResponseLiveData().observe(this, new Observer<BarcodeImage>() {
            @Override
            public void onChanged(BarcodeImage barcodeResponse) {
                if (barcodeResponse != null) {
                    image_barcode.setImageBitmap(barcodeResponse.getImage());
                    image_barcode.setVisibility(View.VISIBLE);
                }
            }
        });

        profileViewModel.getProfile();
        profileViewModel.getBarcode();

    }
}