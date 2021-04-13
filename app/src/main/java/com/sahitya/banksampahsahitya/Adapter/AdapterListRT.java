package com.sahitya.banksampahsahitya.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.HomeAdminActivity;
import com.sahitya.banksampahsahitya.admin.ListUserRtActivity;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterListRT extends BaseAdapter {
    private ArrayList dataRt;
    private Context context;
    private SharedPrefManager sharedPrefManager;

    public AdapterListRT(HashMap<String, Integer> map, Context context) {
        dataRt = new ArrayList();
        dataRt.addAll(map.entrySet());

        this.context = context;
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
    }

//    public AdapterListRT(HashMap<String, Integer> map) {
//        dataRt = new ArrayList();
//        dataRt.addAll(map.entrySet());
//
//    }

    public void setDataList(ArrayList dataList) {
        this.dataRt = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return dataRt.size();
    }

    @Override
    public HashMap.Entry<String, Integer> getItem (int position){
        return (HashMap.Entry) dataRt.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final View result;

        if (convertView == null){
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_per_rt, parent, false);

        } else {
            result = convertView;
        }

        HashMap.Entry<String, Integer> item = getItem(position);

        RelativeLayout rv = result.findViewById(R.id.rv_list_rt);

        ((TextView) result.findViewById(R.id.txt_nama_rt)).setText("RT " + item.getKey());
        ((TextView) result.findViewById(R.id.txt_total_saldo)).setText(item.getValue().toString());

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ListUserRtActivity.class)
                .putExtra("rt", item.getKey().toString())
                .putExtra("point_rt", item.getValue().toString()));

            }
        });

        return result;
    }
}

