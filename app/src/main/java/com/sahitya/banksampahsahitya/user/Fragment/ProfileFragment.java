package com.sahitya.banksampahsahitya.user.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.AboutActivity;
import com.sahitya.banksampahsahitya.Login.LoginActivity;
import com.sahitya.banksampahsahitya.PanduanActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.response.UserResponse;
import com.sahitya.banksampahsahitya.user.EditProfilActivity;
import com.sahitya.banksampahsahitya.user.MainActivity;
import com.sahitya.banksampahsahitya.user.PengaturanActivity;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    MainActivity mainActivity;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mainActivity = (MainActivity)getActivity();

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

        Call<User> getUser = mainActivity.apiInterface.getUser(mainActivity.sharedPrefManager.getSPToken());
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    User user = response.body();
//                    Toast.makeText(MahasiswaActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    nama_profil.setText(response.body().getName());
                    no_tlp_profil.setText(response.body().getMobile());
                    email_profil.setText(response.body().getEmail());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
                mainActivity.sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(mainActivity, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                mainActivity.finish();
                break;
        }
    }
}
