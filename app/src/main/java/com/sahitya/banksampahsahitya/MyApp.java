package com.sahitya.banksampahsahitya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.sahitya.banksampahsahitya.utils.TypefaceUtil;

public class MyApp extends Application {

    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }


    @Override
    public void onCreate() {
        instance = this;
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        super.onCreate();
    }
}