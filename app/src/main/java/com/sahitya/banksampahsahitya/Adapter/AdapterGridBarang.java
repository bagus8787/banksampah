package com.sahitya.banksampahsahitya.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.base.activity.DetailBarangActivity;
import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import java.util.ArrayList;

public class AdapterGridBarang extends RecyclerView.Adapter<AdapterGridBarang.BarangViewHolder> {
    private ArrayList<Barang> dataList;
    private Context context;
    private SharedPrefManager sharedPrefManager;

    public AdapterGridBarang(Context context) {
        this.context = context;
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
    }

    @NonNull
    @Override
    public AdapterGridBarang.BarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_sampah, parent, false);

        return new AdapterGridBarang.BarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGridBarang.BarangViewHolder holder, int position) {
        holder.setId(dataList.get(position).getId());
        holder.setName(dataList.get(position).getName());
        holder.setType(dataList.get(position).getType());
        holder.setPoint(dataList.get(position).getPoint());
    }

    public void setDataList(ArrayList<Barang> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class BarangViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Integer id;
        String name;
        Integer point;
        String type;

        TextView it_barang_nama, it_barang_point, it_barang_type;

        public BarangViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sharedPrefManager.getRole().endsWith(Role.ROLE_ADMIN) || sharedPrefManager.getRole().endsWith(Role.ROLE_COODINATOR)) {
                        context.startActivity(new Intent(context, DetailBarangActivity.class)
                                .putExtra("IT_ID", id)
                                .putExtra("IT_NAME", name)
                                .putExtra("IT_POINT", point)
                                .putExtra("IT_TYPE", type)
                        );
                    }
                }
            });
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setPoint(Integer point) {
            this.point = point;
            it_barang_point = (TextView)mView.findViewById(R.id.it_barang_point);
            it_barang_point.setText(point.toString());
        }

        public void setType(String type) {
            this.type = type;
            it_barang_type = (TextView)mView.findViewById(R.id.it_barang_type);
            it_barang_type.setText(type.toUpperCase());

        }

        public void setName(String name) {
            this.name = name;
            it_barang_nama = (TextView)mView.findViewById(R.id.it_barang_nama);
            it_barang_nama.setText(name.toUpperCase());
        }
    }
}
