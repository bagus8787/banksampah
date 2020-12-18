package com.sahitya.banksampahsahitya.user.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.BarcodeImage;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.user.EditProfilActivity;
import com.sahitya.banksampahsahitya.user.MainActivity;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.ProfileViewModel;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;

    ProfileViewModel profileViewModel;
    BarcodeImage barcode;

    String url_photo;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        mainActivity = (MainActivity)getActivity();
//        return inflater.inflate(R.layout.fragment_coba, container, false);

        View rootView = inflater.inflate(R.layout.fragment_coba, container, false);

        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView tv_name = view.findViewById(R.id.tv_name);
        final TextView tv_saldo = view.findViewById(R.id.tv_saldo);
        final ImageView image_barcode = view.findViewById(R.id.image_barcode);
        final ImageView image_btn = view.findViewById(R.id.buttonprofile);

        tv_name.setText(sharedPrefManager.getSPNama());

        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(getActivity(), EditProfilActivity.class);
                startActivity(p);
            }
        });

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();
        profileViewModel.getProfileResponseLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User userResponse) {
                if (userResponse != null) {
                    sharedPrefManager.setSpAvatar(userResponse.getAvatarLocation());
                    url_photo = sharedPrefManager.getSPAvatar();
                    Picasso.get().load(url_photo)
                            .error(R.drawable.ic_baseline_account_circle)
                            .into(image_btn);
                    if (userResponse.getWarga() != null) {
                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_POINT_TOTAL, userResponse.getWarga().getPointTotal());
                        tv_saldo.setText(Integer.toString(userResponse.getWarga().getPointTotal()));
                    }
                }
            }
        });

        url_photo = sharedPrefManager.getSPAvatar();
        Picasso.get().load(url_photo)
                .fit()
                .error(R.drawable.ic_baseline_account_circle)
                .into(image_btn);

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
