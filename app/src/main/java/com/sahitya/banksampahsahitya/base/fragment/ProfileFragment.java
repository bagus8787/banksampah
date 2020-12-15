package com.sahitya.banksampahsahitya.base.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.AboutActivity;
import com.sahitya.banksampahsahitya.Login.LoginActivity;
import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.PanduanActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.user.EditProfilActivity;
import com.sahitya.banksampahsahitya.user.MainActivity;
import com.sahitya.banksampahsahitya.user.PengaturanActivity;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.ProfileViewModel;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    ProfileViewModel profileViewModel;
    ProgressDialog progressDialog;

    String url_photo;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        RelativeLayout edit_profil = rootView.findViewById(R.id.rv_edit_profil);
        RelativeLayout pengaturan = rootView.findViewById(R.id.rv_pengaturan);
        RelativeLayout about = rootView.findViewById(R.id.rv_about);
        RelativeLayout panduan = rootView.findViewById(R.id.rv_panduan);
        Button logout = rootView.findViewById(R.id.btn_logout);

        edit_profil.setOnClickListener(this);
        pengaturan.setOnClickListener(this);
        about.setOnClickListener(this);
        panduan.setOnClickListener(this);
        logout.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView nama_profil = view.findViewById(R.id.nama_profil);
        final TextView no_tlp_profil = view.findViewById(R.id.no_tlp_profil);
        final TextView email_profil = view.findViewById(R.id.email_profil);

        final ImageView img_profil_user = view.findViewById(R.id.img_profil_user_fr);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().hide();
        }

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();
        profileViewModel.getProfileResponseLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User userResponse) {
                if (userResponse != null) {
                    sharedPrefManager.setSpAvatar(userResponse.getAvatarLocation());
                    url_photo = sharedPrefManager.getSPAvatar();
                    Picasso.get().load(url_photo)
                            .resize(150, 150)
                            .centerCrop()
                            .error(R.drawable.ic_baseline_account_circle)
                            .into(img_profil_user);
                    if (userResponse.getWarga() != null) {
                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_POINT_TOTAL, userResponse.getWarga().getPointTotal());
                    }
                }
            }
        });

        url_photo = sharedPrefManager.getSPAvatar();
        Picasso.get().load(url_photo)
                .fit()
                .error(R.drawable.ic_baseline_account_circle)
                .into(img_profil_user);

        nama_profil.setText(sharedPrefManager.getSPNama());
        no_tlp_profil.setText(sharedPrefManager.getSPMobile());
        email_profil.setText(sharedPrefManager.getSPEmail());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.rv_edit_profil:
                Intent intent = new Intent(getActivity(), EditProfilActivity.class);
                startActivity(intent);
                break;

            case R.id.rv_pengaturan:
                Intent p = new Intent(getActivity(), PengaturanActivity.class);
                startActivity(p);
                break;

            case R.id.rv_about:
                Intent a = new Intent(getActivity(), AboutActivity.class);
                startActivity(a);
                break;

            case R.id.rv_panduan:
                Intent pn = new Intent(getActivity(), PanduanActivity.class);
                startActivity(pn);
                break;

            case R.id.btn_logout:
                progressDialog.show();
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                Call<BaseResponse> logout = apiInterface.logout(sharedPrefManager.getSPToken());
                logout.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        progressDialog.dismiss();
                        if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                            startActivity(new Intent(getActivity(), LoginActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        profileViewModel.getProfile();
    }
}
