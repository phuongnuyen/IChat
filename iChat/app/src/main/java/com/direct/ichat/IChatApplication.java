package com.direct.ichat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Phuong Nguyen Lan on 12/17/2016.
 */

public class IChatApplication extends Application{
    private static IChatApplication sInstance;
    public static IChatApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Firebase.setAndroidContext(this);
    }
}
