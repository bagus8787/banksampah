package com.sahitya.banksampahsahitya.base.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.base.activity.DetailBarangActivity;
import com.sahitya.banksampahsahitya.base.activity.FormBarangActivity;
import com.sahitya.banksampahsahitya.base.activity.ListSampahActivity;
import com.sahitya.banksampahsahitya.model.BarcodeImage;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.user.EditProfilActivity;
import com.sahitya.banksampahsahitya.user.MainActivity;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.ProfileViewModel;

public class ListKategoriFragment extends Fragment implements View.OnClickListener{
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;

    public ListKategoriFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        mainActivity = (MainActivity)getActivity();
        View rootView = inflater.inflate(R.layout.fragment_list_kategori, container, false);

        sharedPrefManager = new SharedPrefManager(MyApp.getContext());

        LinearLayout lv_kertas = rootView.findViewById(R.id.linearLayout_kertas);
        LinearLayout lv_kaca = rootView.findViewById(R.id.linearLayout_kaca);
        LinearLayout lv_logam = rootView.findViewById(R.id.linearLayout_logam);
        LinearLayout lv_plastik = rootView.findViewById(R.id.linearLayout_plastik);
        LinearLayout lv_lain = rootView.findViewById(R.id.linearLayout_lain);

        LinearLayout lv_tambah = rootView.findViewById(R.id.linearLayout_tambah);

        lv_kertas.setOnClickListener(this);
        lv_kaca.setOnClickListener(this);
        lv_logam.setOnClickListener(this);
        lv_plastik.setOnClickListener(this);
        lv_lain.setOnClickListener(this);

        lv_tambah.setOnClickListener(this);

        if (sharedPrefManager.getRole().endsWith(Role.ROLE_ADMIN) || sharedPrefManager.getRole().endsWith(Role.ROLE_COODINATOR)) {
            lv_tambah.setVisibility(View.VISIBLE);
        } else {
            lv_tambah.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.linearLayout_kaca:
                Intent intent = new Intent(getActivity(), ListSampahActivity.class);
                intent.putExtra("JENIS", "KACA");
                startActivity(intent);

                break;

            case R.id.linearLayout_kertas:
                Intent ke = new Intent(getActivity(), ListSampahActivity.class);
                ke.putExtra("JENIS", "KERTAS");
                startActivity(ke);

                break;

            case R.id.linearLayout_logam:
                Intent lo = new Intent(getActivity(), ListSampahActivity.class);
                lo.putExtra("JENIS", "LOGAM");
                startActivity(lo);

                break;

            case R.id.linearLayout_plastik:
                Intent pl = new Intent(getActivity(), ListSampahActivity.class);
                pl.putExtra("JENIS", "PLASTIK");
                startActivity(pl);

                break;

            case R.id.linearLayout_lain:
                Intent la = new Intent(getActivity(), ListSampahActivity.class);
                la.putExtra("JENIS", "LAIN-LAIN");
                startActivity(la);

                break;

            case R.id.linearLayout_tambah:
                Intent ta = new Intent(getActivity(), FormBarangActivity.class);
                ta.putExtra("ACTION", "CREATE");
                startActivity(ta);

                break;
        }
    }
}