package com.sahitya.banksampahsahitya.base.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sahitya.banksampahsahitya.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GridListKategoriFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GridListKategoriFragment extends Fragment {

    public GridListKategoriFragment() {
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
        return inflater.inflate(R.layout.fragment_grid_list_kategori, container, false);
    }
}