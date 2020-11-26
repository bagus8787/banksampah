package com.sahitya.banksampahsahitya.user.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sahitya.banksampahsahitya.AboutActivity;
import com.sahitya.banksampahsahitya.PanduanActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.user.EditProfilActivity;
import com.sahitya.banksampahsahitya.user.PengaturanActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        RelativeLayout edit_profil = rootView.findViewById(R.id.rv_edit_profil);
        RelativeLayout pengaturan = rootView.findViewById(R.id.rv_pengaturan);
        RelativeLayout about = rootView.findViewById(R.id.rv_about);
        RelativeLayout panduan = rootView.findViewById(R.id.rv_panduan);

        edit_profil.setOnClickListener(this);
        pengaturan.setOnClickListener(this);
        about.setOnClickListener(this);
        panduan.setOnClickListener(this);

        return rootView;
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

        }
    }
}
