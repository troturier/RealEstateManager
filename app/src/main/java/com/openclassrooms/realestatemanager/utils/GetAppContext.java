package com.openclassrooms.realestatemanager.utils;

import android.app.Application;
import android.content.Context;

/**
 * Class used to access the context of the application at any point in the code
 */
public class GetAppContext extends Application {
    private static GetAppContext instance;

    public static GetAppContext getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
