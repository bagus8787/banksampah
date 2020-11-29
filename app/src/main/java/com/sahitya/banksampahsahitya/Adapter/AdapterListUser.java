package com.sahitya.banksampahsahitya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.user.DetailTransaksiActivity;
import com.sahitya.banksampahsahitya.model.User;

import java.util.ArrayList;

public class AdapterListUser extends RecyclerView.Adapter<AdapterListUser.ListUserViewHolder> {
    private ArrayList<User> dataList;
    private Context context;

    public AdapterListUser(Context context) {
        this.context = context;
    }

    @Override
    public ListUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_user, parent, false);

        return new ListUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListUserViewHolder holder, int position) {

        holder.setName(dataList.get(position).getName());
//        holder.setBarcode(dataList.get(position).getBarcode());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public void setDataList(ArrayList<User> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public class ListUserViewHolder extends RecyclerView.ViewHolder {
        View mView;
        boolean verified;
        String verifiedText;
        Integer point;
        String name;

        TextView it_name_user, it_ambil_point;

        public ListUserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    context.startActivity(new Intent(context, DetailTransaksiActivity.class)
//                            .putExtra("IT_VERIFIED", verifiedText)
//                            .putExtra("IT_POINT", point)
//                            .putExtra("IT_BARCODE", barcode)
//                    );
                }
            });
        }

        public void setName(String name) {
            this.name = name;
            it_name_user = (TextView)mView.findViewById(R.id.it_nama_user);
        }

//        public void setVerified(boolean verified){
//            this.verified = verified;
//            it_ambil_verified = (TextView)mView.findViewById(R.id.it_ambil_verified);
//            if (verified) {
//                verifiedText = "Verified";
//            } else {
//                verifiedText = "Unverified";
//            }
//            it_ambil_verified.setText(verifiedText);
//        }

//        public void setPoint(Integer point) {
//            this.point = point;
//            it_ambil_point = (TextView)mView.findViewById(R.id.it_ambil_point);
//            it_ambil_point.setText(point.toString());
//        }

//        public void setBarcode(String barcode) {
//            this.barcode = barcode;
//        }
    }
}

