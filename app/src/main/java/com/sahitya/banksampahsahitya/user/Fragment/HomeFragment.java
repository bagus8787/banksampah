package com.sahitya.banksampahsahitya.user.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.user.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    MainActivity mainActivity;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (MainActivity)getActivity();
        return inflater.inflate(R.layout.fragment_coba, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView tv_name = view.findViewById(R.id.tv_name);
        final TextView tv_saldo = view.findViewById(R.id.tv_saldo);

        Call<User> getUser = mainActivity.apiInterface.getUser(mainActivity.sharedPrefManager.getSPToken());
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    User user = response.body();
//                    Toast.makeText(MahasiswaActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    tv_name.setText(response.body().getName());
                    tv_saldo.setText(Integer.toString(response.body().getWarga().getPointTotal()));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
