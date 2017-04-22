package com.cunoraz.eksiseyler.app;

/**
 * Created by cuneytcarikci on 20/11/2016.
 */

import java.io.File;

import android.app.Application;

import com.cunoraz.eksiseyler.utility.AppSettings;
import com.cunoraz.eksiseyler.utility.SharedPrefManager;

public class MyApplication extends Application {

    private static MyApplication instance;

    //private SharedPrefManager manager;

    private AppSettings appSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (String aChildren : children) {
                    deletedAll = deleteFile(new File(file, aChildren)) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }

    public synchronized AppSettings getSharedPrefManager() {
        if (appSettings == null)
            appSettings = AppSettings.getInstance();

        return appSettings;
    }
}