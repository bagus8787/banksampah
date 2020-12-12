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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        holder.setPointHistory(dataList.get(position));
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
        PointHistory pointHistory;
        String verifiedText;
        TextView it_ambil_verified, it_ambil_point, it_ambil_type, it_ambil_updated;

        public TransaksiViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, DetailTransaksiActivity.class)
                            .putExtra("IT_VERIFIED", verifiedText)
                            .putExtra("IT_POINT", pointHistory.getPoint())
                            .putExtra("IT_BARCODE", pointHistory.getBarcode())
                            .putExtra("IT_TYPE", pointHistory.getType())
                            .putExtra("IT_DESCRIPTION", pointHistory.getDescription())
                            .putExtra("IT_POINT_TOTAL", pointHistory.getPointTotal())
                            .putExtra("IT_CREATED", pointHistory.getCreatedAtFormatted())
                            .putExtra("IT_UPDATED", pointHistory.getUpdatedAtFormatted())
                    );
                }
            });
        }

        public void setPointHistory(PointHistory pointHistory) {
            this.pointHistory = pointHistory;
            it_ambil_verified = (TextView)mView.findViewById(R.id.it_ambil_verified);
            it_ambil_point = (TextView)mView.findViewById(R.id.it_ambil_point);
            it_ambil_type = (TextView)mView.findViewById(R.id.it_ambil_type);
            it_ambil_updated = (TextView)mView.findViewById(R.id.it_ambil_update);

            if (pointHistory.isVerified()) {
                verifiedText = "Verified";
            } else {
                verifiedText = "Unverified";
            }
            it_ambil_verified.setText(verifiedText);

            if (pointHistory.getType().equals("tukar")) {
                it_ambil_point.setText("+ Rp. "+String.valueOf(pointHistory.getPointTotal()));
            } else {
                it_ambil_point.setText("- Rp. "+String.valueOf(pointHistory.getPointTotal()));
            }
            it_ambil_type.setText(pointHistory.getType().toUpperCase());

            it_ambil_updated.setText(pointHistory.getCreatedAt());
        }

    }
}
