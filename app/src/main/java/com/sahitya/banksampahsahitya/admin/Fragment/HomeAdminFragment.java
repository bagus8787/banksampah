package com.sahitya.banksampahsahitya.admin.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.Adapter.AdapterListRT;
import com.sahitya.banksampahsahitya.Adapter.AdapterListUser;
import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.ListUserRtActivity;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.network.response.ByRtResponse;
import com.sahitya.banksampahsahitya.repositories.ListRtRepository;
import com.sahitya.banksampahsahitya.repositories.PointRepository;
import com.sahitya.banksampahsahitya.repositories.UserListRepository;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.ListRtVieModel;
import com.sahitya.banksampahsahitya.viewmodels.ProfileViewModel;
import com.sahitya.banksampahsahitya.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdminFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeAdminFragment extends Fragment {

    Context context;

    ProfileViewModel profileViewModel;
    UserViewModel userViewModel;

    SharedPrefManager sharedPrefManager;

    PointRepository pointRepository;
    ListRtRepository listRtRepository;
    ListRtVieModel listRtVieModel;

    UserListRepository userListRepository;

    AdapterListRT adapterListRT;
    AdapterListUser adapterListUser;

    HashMap<String, Integer> map;

    ApiInterface apiInterface;

    ListView lv_rt;

    TextView txt_total_rt;

    public HomeAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home_admin, container, false);

        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        sharedPrefManager = new SharedPrefManager(getActivity());
        pointRepository = new PointRepository();
        listRtRepository = new ListRtRepository();
        userListRepository = new UserListRepository();

        TextView txt_point_admin = view.findViewById(R.id.txt_point_admin);
        TextView txt_total_user = view.findViewById(R.id.txt_total_user);
        txt_total_rt = view.findViewById(R.id.txt_total_rt);

        lv_rt = view.findViewById(R.id.lv_rt);

        lv_rt.setAdapter(adapterListRT);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().hide();
        }

        map = new HashMap<>();

        adapterListRT = new AdapterListRT(map,getContext());
        adapterListUser = new AdapterListUser(getContext());

        Log.d("adadapter", "==" + adapterListRT);

        listRtVieModel = ViewModelProviders.of(this).get(ListRtVieModel.class);
        listRtVieModel.init();
        listRtVieModel.getUserResponseLiveData().observe(this, new Observer<HashMap<String, Integer>>() {
            @Override
            public void onChanged(HashMap<String, Integer> rtResponse) {
                if (rtResponse != null) {
                    Log.d("hasile : ", String.valueOf(rtResponse));
//                    adapterListRT.setDataList(rtResponse);
                }
            }
        });

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();

        profileViewModel.getProfileResponseLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User userResponse) {
                if (userResponse != null) {
                    sharedPrefManager.setSpAvatar(userResponse.getAvatarLocation());
                    if (userResponse.getWarga() != null) {
                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_POINT_TOTAL, userResponse.getWarga().getPointTotal());
//                        tv_saldo.setText(Integer.toString(userResponse.getWarga().getPointTotal()));
                    }
                }
            }
        });

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();

        userViewModel.getUserResponseLiveData().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                adapterListUser.setDataList(users);
                Log.d("usersss", String.valueOf(adapterListUser.getItemCount()));

                txt_total_user.setText(String.valueOf(adapterListUser.getItemCount()));
            }
        });

        listRtVieModel.getListRtt();
        listRtRepository.getListRt();

        profileViewModel.getProfile();
        pointRepository.getAdminPoints();

//        userViewModel.getUserList();
//        userListRepository.getUserList();

        txt_point_admin.setText("Rp. " + sharedPrefManager.getAdminPointTotal().toString());

        Call<ByRtResponse> getSummaryRT = apiInterface.getSummaryRT(
                sharedPrefManager.getSPToken()
        );

        getSummaryRT.enqueue(new Callback<ByRtResponse>() {
            @Override
            public void onResponse(Call<ByRtResponse> call, Response<ByRtResponse> response) {

                HashMap<String, Integer> test = new HashMap<String, Integer>();
                test = response.body().getRt_point_totals();

//                for (String rt : test.keySet()){
//                    Log.d("rtsss", "rt nya :" + rt);
//                }
//
//                for (Integer point : test.values()){
//                    Log.d("pointsss", "pointnya :" + point);
//                }

                setListRt(test, context);

            }
            @Override
            public void onFailure(Call<ByRtResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        listRtVieModel.getListRtt();
        userViewModel.getUserList();
    }

    public void setListRt(HashMap<String, Integer> map, Context context) {
        AdapterListRT adapter = new AdapterListRT(map, context);
        lv_rt.setAdapter(adapter);

        Log.d("totalaaaaa" , "=" + adapter.getCount());

        txt_total_rt.setText(String.valueOf(adapter.getCount()));
    }
}