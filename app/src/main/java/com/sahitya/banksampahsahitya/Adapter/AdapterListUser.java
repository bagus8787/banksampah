package com.sahitya.banksampahsahitya.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.DetailUserActivity;
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
        holder.setId(dataList.get(position).getId());
        holder.setEmail(dataList.get(position).getEmail());
        holder.setSex(dataList.get(position).getSex());
        holder.setNope(dataList.get(position).getMobile());
        holder.setAddress(dataList.get(position).getAddress());
        holder.setStatus(dataList.get(position).getRoleName());
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
        Integer point, id;
        String name, email, status, address, sex, nope;

        TextView it_name_user, it_id_user, it_email_user, it_status_user;

        User user;

        public ListUserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            user = new User(dataList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(new Intent(context, DetailUserActivity.class)
                            .putExtra("IT_ID_USER", id)
                            .putExtra("IT_NAMA_USER", name)
                            .putExtra("IT_EMAIL_USER", email)
                            .putExtra("IT_ADDRESS_USER", address)
                            .putExtra("IT_NOPE_USER", nope)
                            .putExtra("IT_SEX_USER", sex)
                    );
                }
            });
        }

        public void setName(String name) {
            this.name = name;
            it_name_user = (TextView)mView.findViewById(R.id.it_nama_user);
            it_name_user.setText(name);
        }

        public void setEmail(String email) {
            this.email = email;
            it_email_user = (TextView)mView.findViewById(R.id.it_email_user);
            it_email_user.setText(email);
        }

        public void setStatus(String status) {
            this.status = status;
            it_status_user = (TextView)mView.findViewById(R.id.it_status_user);
            it_status_user.setText(status);
        }

        public void setId(Integer id){
            this.id = id;
        }

        public void setAddress(String address){
            this.address = address;
        }

        public void setSex(String sex){
            this.sex = sex;
        }

        public void setNope(String nope){
            this.nope = nope;
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

