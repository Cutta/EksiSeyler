package com.cunoraz.eksiseyler.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cuneytcarikci on 02/01/2017.
 */

public class SharedPrefManager {
    Context context;

    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
        createSharedPref();
    }

    public void createSharedPref() {
        if (sharedPreference == null) {
            sharedPreference = context.getSharedPreferences("eksi_db", Context.MODE_PRIVATE);
        }
        editor = sharedPreference.edit();
    }

    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreference.getBoolean(key, true);//default resimleri goster
    }


}
