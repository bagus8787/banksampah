package com.sahitya.banksampahsahitya.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.DetailUserActivity;
import com.sahitya.banksampahsahitya.admin.HistoryUserActivity;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.user.DetailTransaksiActivity;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import java.util.ArrayList;

public class AdapterListUser extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<User> dataList;
    private Context context;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;
    SharedPrefManager sharedPrefManager;

    public AdapterListUser(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        if(viewType==SHOW_MENU){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user_menu, parent, false);
            return new MenuViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user, parent, false);
            return new ListUserViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(dataList.get(position).isShowMenu()){
            return SHOW_MENU;
        }else{
            return HIDE_MENU;
        }
    }

    public void showMenu(int position) {
        for(int i=0; i<dataList.size(); i++){
            dataList.get(i).setShowMenu(false);
        }
        dataList.get(position).setShowMenu(true);
        notifyDataSetChanged();
    }

    public boolean isMenuShown() {
        for(int i=0; i<dataList.size(); i++){
            if(dataList.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    public void closeMenu() {
        for(int i=0; i<dataList.size(); i++){
            dataList.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListUserViewHolder) {
            ((ListUserViewHolder) holder).setUser(dataList.get(position));

            ((ListUserViewHolder)holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showMenu(position);
                    return true;
                }
            });
        }
        if(holder instanceof MenuViewHolder){
            ((MenuViewHolder) holder).setUser(dataList.get(position));
        }
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
        TextView it_name_user, it_id_user, it_email_user, it_status_user;
        User user;

        public ListUserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            user = new User(dataList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeMenu();
                    context.startActivity(new Intent(context, DetailUserActivity.class)
                            .putExtra("IT_ID_USER", user.getId())
                            .putExtra("IT_NAMA_USER", user.getName())
                            .putExtra("IT_EMAIL_USER", user.getEmail())
                            .putExtra("IT_ADDRESS_USER", user.getAddress())
                            .putExtra("IT_NOPE_USER", user.getMobile())
                            .putExtra("IT_SEX_USER", user.getSex())
                    );
                }
            });
        }

        public void setUser(User user) {
            this.user = user;
            it_name_user = (TextView)mView.findViewById(R.id.it_nama_user);
            it_email_user = (TextView)mView.findViewById(R.id.it_email_user);
            it_status_user = (TextView)mView.findViewById(R.id.it_status_user);

            it_name_user.setText(user.getName());
            it_email_user.setText(user.getEmail());
            it_status_user.setText(user.getRoleName());
        }

    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        User user;

        public MenuViewHolder(View view){
            super(view);

            Button item_history = view.findViewById(R.id.item_history_btn);
            Button item_edit = view.findViewById(R.id.item_edit_btn);

            item_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPrefManager.setCurrentUserId(user.getId());
                    if (sharedPrefManager.isAdmin()) {
                        closeMenu();
                        context.startActivity(new Intent(context, HistoryUserActivity.class).putExtra("IT_NAMA", user.getName()));
                    }
                }
            });

            item_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sharedPrefManager.isAdmin()) {
                        closeMenu();
                        Toast.makeText(context, "Warga diatur sebagai Koordinator", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}

