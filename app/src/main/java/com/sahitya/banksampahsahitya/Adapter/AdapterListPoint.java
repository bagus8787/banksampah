package com.sahitya.banksampahsahitya.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;

import java.util.ArrayList;

public class AdapterListPoint extends RecyclerView.Adapter<AdapterListPoint.ViewHolder> {

    private final ArrayList<User> listuser;
    private final Context context;
    private boolean admin;
    User user;

    public AdapterListPoint(ArrayList<User> listuser, Context context) {
        this.listuser = listuser;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_point, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        User user = listuser.get(position);
        holder.display(user);
    }

    @Override
    public int getItemCount() {
        return listuser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_item_photo;
        private TextView tv_title;

        ViewHolder(View itemView) {
            super(itemView);
//            iv_item_photo = itemView.findViewById(R.id.photopromotion);
//            tv_title = itemView.findViewById(R.id.titlepromotion);
        }

        void display(final User user) {
//            Glide.with(itemView.getContext())
//                    .load(promotion.getPhoto())
//                    .into(iv_item_photo);
//            tv_title.setText(promotion.getTitle());
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (admin) {
//                        Intent intent = new Intent(context, InputOrderUserActivity.class);
//                        intent.putExtra("jenis_laundry", promotion);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(context, InputOrderUserActivity.class);
//                        intent.putExtra("jenis_laundry", promotion);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                    }
//                }
//            });
        }

    }
}
