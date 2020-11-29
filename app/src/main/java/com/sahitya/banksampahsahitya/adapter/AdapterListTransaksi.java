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
        holder.setVerified(dataList.get(position).isVerified());
        holder.setPoint(dataList.get(position).getPoint());
        holder.setBarcode(dataList.get(position).getBarcode());
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

        TextView it_ambil_verified, it_ambil_point;

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

        public void setPoint(Integer point) {
            this.point = point;
            it_ambil_point = (TextView)mView.findViewById(R.id.it_ambil_point);
            it_ambil_point.setText(point.toString());
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }
    }
}
