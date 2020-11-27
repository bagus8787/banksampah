package com.sahitya.banksampahsahitya.admin.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sahitya.banksampahsahitya.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BankSampahFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankSampahFragment extends Fragment {



    public BankSampahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bank_sampah, container, false);
    }
}