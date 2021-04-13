package com.sahitya.banksampahsahitya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.DetailUserActivity;
import com.sahitya.banksampahsahitya.admin.HistoryUserActivity;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.repositories.UserListRepository;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import java.util.ArrayList;

public class AdapterListUser extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<User> dataList;
    private Context context;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;
    SharedPrefManager sharedPrefManager;

    UserListRepository userListRepository;

    public AdapterListUser(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        userListRepository = new UserListRepository();
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
                    if (sharedPrefManager.isAdmin()) {
                        showMenu(position);
                    }
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
        TextView it_name_user, it_point_total, it_email_user, it_status_user;
        User user;

        public ListUserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            user = new User(dataList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeMenu();

                    String address, rt, sex;
                    boolean hasWarga = user.getWarga() != null;
                    address = hasWarga ? user.getWarga().getAddress() : "";
                    rt = hasWarga ? user.getWarga().getRt() : "";
                    sex = hasWarga ? user.getWarga().getSex() : "";
                    context.startActivity(new Intent(context, DetailUserActivity.class)
                            .putExtra("IT_ID_USER", user.getId())
                            .putExtra("IT_NAMA_USER", user.getName())
                            .putExtra("IT_EMAIL_USER", user.getEmail())
                            .putExtra("IT_NOPE_USER", user.getMobile())
                            .putExtra("IT_AVATAR_USER", user.getAvatarLocation())
                            .putExtra("IT_ADDRESS_USER", address)
                            .putExtra("IT_RT_USER", rt)
                            .putExtra("IT_SEX_USER", sex)
                            .putExtra("IT_HAS_WARGA", hasWarga)
                    );
                }
            });
        }

        public void setUser(User user) {
            this.user = user;
            it_name_user = (TextView)mView.findViewById(R.id.it_nama_user);
            it_email_user = (TextView)mView.findViewById(R.id.it_email_user);
            it_status_user = (TextView)mView.findViewById(R.id.it_status_user);
            it_point_total = (TextView)mView.findViewById(R.id.it_point_total);

            it_name_user.setText(user.getName());
            it_email_user.setText(user.getEmail());
            it_status_user.setText(user.getRoleName());
            if (user.getWarga() != null) {
                it_point_total.setText("Rp. " + String.valueOf(user.getWarga().getPointTotal()));
            } else {
                it_point_total.setText("Rp. 0");
            }
        }

    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        User user;
        Button as_admin, as_koor, as_user;

        public MenuViewHolder(View view){
            super(view);

            Button item_history = view.findViewById(R.id.item_history_btn);
            as_admin = view.findViewById(R.id.as_admin_btn);
            as_koor = view.findViewById(R.id.as_koor_btn);
            as_user = view.findViewById(R.id.as_user_btn);

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

            as_admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sharedPrefManager.isAdmin()) {
                        toggleRole(user, Role.ROLE_ADMIN);
                        closeMenu();
                    }
                }
            });

            as_koor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sharedPrefManager.isAdmin()) {
                        toggleRole(user, Role.ROLE_COODINATOR);
                        closeMenu();
                    }
                }
            });

            as_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sharedPrefManager.isAdmin()) {
                        toggleRole(user, Role.ROLE_USER);
                        closeMenu();
                    }
                }
            });
        }

        public void setUser(User user) {
            this.user = user;
            Drawable drawable;
            switch (user.getRoleName()) {
                case Role.ROLE_ADMIN:
                    toggleButtonActive(as_admin, R.drawable.ic_as_admin, true);
                    toggleButtonActive(as_koor, R.drawable.ic_as_coordinator, false);
                    toggleButtonActive(as_user, R.drawable.ic_as_user, false);
                    break;
                case Role.ROLE_COODINATOR:
                    toggleButtonActive(as_admin, R.drawable.ic_as_admin, false);
                    toggleButtonActive(as_koor, R.drawable.ic_as_coordinator, true);
                    toggleButtonActive(as_user, R.drawable.ic_as_user, false);
                    break;
                case Role.ROLE_USER:
                    toggleButtonActive(as_admin, R.drawable.ic_as_admin, false);
                    toggleButtonActive(as_koor, R.drawable.ic_as_coordinator, false);
                    toggleButtonActive(as_user, R.drawable.ic_as_user, true);
                    break;
                default:
                    break;
            }
        }

        public void toggleButtonActive(Button button, Integer drawable_id, boolean active) {
            Drawable clone = context.getResources().getDrawable(drawable_id).getConstantState().newDrawable();
            if (active) {
                clone.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                button.setCompoundDrawablesWithIntrinsicBounds(null, clone, null, null);
                button.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                clone.setColorFilter(context.getResources().getColor(R.color.colorFont), PorterDuff.Mode.SRC_ATOP);
                button.setCompoundDrawablesWithIntrinsicBounds(null, clone, null, null);
                button.setTextColor(context.getResources().getColor(R.color.colorFont));
            }
        }

        public void toggleRole(User user, String role) {
            userListRepository.setUserRole(user.getId(), role);
            Toast.makeText(context, user.getName() + " diatur sebagai " + role, Toast.LENGTH_SHORT).show();
        }
    }
}

