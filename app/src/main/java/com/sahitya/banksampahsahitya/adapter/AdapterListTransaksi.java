package com.sahitya.banksampahsahitya.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.user.DetailTransaksiActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterListTransaksi extends RecyclerView.Adapter<AdapterListTransaksi.TransaksiViewHolder> {
    private ArrayList<PointHistory> dataList;
    private Context context;

    public AdapterListTransaksi(Context context) {
        this.context = context;
    }

    @Override
    public TransaksiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_ambil_saldo, parent, false);

        return new TransaksiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransaksiViewHolder holder, int position) {
        holder.setType(dataList.get(position).getType());
        holder.setVerified(dataList.get(position).isVerified());
        holder.setPoint(dataList.get(position).getPoint(), dataList.get(position).getPointTotal());
        holder.setBarcode(dataList.get(position).getBarcode());
        holder.setDescription(dataList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public void setDataList(ArrayList<PointHistory> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        View mView;
        boolean verified;
        String verifiedText;
        Integer point;
        String barcode;
        String type;
        String description;
        Integer point_total;

        TextView it_ambil_verified, it_ambil_point, it_ambil_type;

        public TransaksiViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, DetailTransaksiActivity.class)
                            .putExtra("IT_VERIFIED", verifiedText)
                            .putExtra("IT_POINT", point)
                            .putExtra("IT_BARCODE", barcode)
                            .putExtra("IT_TYPE", type)
                            .putExtra("IT_DESCRIPTION", description)
                            .putExtra("IT_POINT_TOTAL", point_total)
                    );
                }
            });
        }

        public void setVerified(boolean verified){
            this.verified = verified;
            it_ambil_verified = (TextView)mView.findViewById(R.id.it_ambil_verified);
            if (verified) {
                verifiedText = "Verified";
            } else {
                verifiedText = "Unverified";
            }
            it_ambil_verified.setText(verifiedText);
        }

        public void setPoint(Integer point, Integer point_total) {
            this.point = point;
            this.point_total = point_total;
            it_ambil_point = (TextView)mView.findViewById(R.id.it_ambil_point);
            it_ambil_point.setText(point_total.toString());
        }

        public void setType(String type) {
            this.type = type;
            it_ambil_type = (TextView)mView.findViewById(R.id.it_ambil_type);
            it_ambil_type.setText(type.toUpperCase());

        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
