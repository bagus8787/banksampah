package com.sahitya.banksampahsahitya.user.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabunganFragment extends Fragment {

    private TextView tvTotalUang, tvTotalSampah, tvRiwayatTransaksi;

    public TabunganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabungan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTotalUang = view.findViewById(R.id.tv_total_uang);
        tvTotalSampah = view.findViewById(R.id.tv_total_sampah);
        tvRiwayatTransaksi = view.findViewById(R.id.tv_riwayat_transaksi);
    }
}
