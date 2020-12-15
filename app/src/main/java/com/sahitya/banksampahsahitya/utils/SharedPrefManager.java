package com.sahitya.banksampahsahitya.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sahitya.banksampahsahitya.model.Role;

public class SharedPrefManager {

    public static final String SP_LOGIN_APP = "spLoginApp";

    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_MOBILE = "spMobile";
    public static final String SP_ADDRESS = "spAddress";
    public static final String SP_SEX = "spSex";
    public static final String SP_RT = "spRt";

    public static final String SP_AVATAR = "spAvatar";

    public static final String SP_POINT_TOTAL = "spPointTotal";

    public static final String SP_TOKEN = "spToken";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    public static final String SP_ROLE = "spRole";
    public static final String SP_CUR_USER_ID = "spCurrentUserId";
    public static final String SP_HAS_WARGA = "spHasWarga";
    public static final String SP_TOTAL_POINT_WARGA = "spWargaPointTotal";
    public static final String SP_TOTAL_POINT_ADMIN = "spAdminPointTotal";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;


    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_LOGIN_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSpAddress(){
        return sp.getString(SP_ADDRESS, "");
    }

    public String getSpSex(){
        return sp.getString(SP_SEX, "");
    }

    public String getSpRt(){
        return sp.getString(SP_RT, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public String getSPMobile(){
        return sp.getString(SP_MOBILE, "");
    }

    public Integer getSPPointTotal(){
        return sp.getInt(SP_POINT_TOTAL, 0);
    }

    public String getSPToken(){
        return sp.getString(SP_TOKEN, "");
    }

    public String getSPAvatar(){
        return sp.getString(SP_AVATAR, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public String getRole(){
        return sp.getString(SP_ROLE, Role.ROLE_USER);
    }

    public Integer getCurrentUserId(){
        return sp.getInt(SP_CUR_USER_ID, 0);
    }

    public Integer getWargaPointTotal(){
        return sp.getInt(SP_TOTAL_POINT_WARGA, 0);
    }

    public Integer getAdminPointTotal(){
        return sp.getInt(SP_TOTAL_POINT_ADMIN, 0);
    }

    public void setCurrentUserId(Integer id) {
        saveSPInt(SP_CUR_USER_ID, id);
    }

    public boolean isUser() {
        return getRole().equals(Role.ROLE_USER);
    }

    public boolean isCoordinator() {
        return getRole().equals(Role.ROLE_COODINATOR);
    }

    public boolean isAdmin() {
        return getRole().equals(Role.ROLE_ADMIN);
    }

    public boolean hasWarga() {
        return sp.getBoolean(SP_HAS_WARGA, false);
    }

    public void setSpAddress(String SpAddress) {
        if (!SpAddress.equals(""))
        saveSPString(SharedPrefManager.SP_ADDRESS, SpAddress);
    }
    public void setSpEmail(String SpEmail) {
        if (!SpEmail.equals(""))
        saveSPString(SharedPrefManager.SP_EMAIL, SpEmail);
    }
    public void setSpNama(String SpNama) {
        if (!SpNama.equals(""))
        saveSPString(SharedPrefManager.SP_NAMA, SpNama);
    }
    public void setSpMobile(String SpMobile) {
        if (!SpMobile.equals(""))
        saveSPString(SharedPrefManager.SP_MOBILE, SpMobile);
    }
    public void setSpSex(String SpSex) {
        if (!SpSex.equals(""))
        saveSPString(SharedPrefManager.SP_SEX, SpSex);
    }
    public void setSpRt(String SpRt) {
        if (!SpRt.equals(""))
        saveSPString(SharedPrefManager.SP_RT, SpRt);
    }

    public void setSpAvatar(String SpAvatar) {
        if (!SpAvatar.equals(""))
            saveSPString(SharedPrefManager.SP_AVATAR, SpAvatar);
    }


}
