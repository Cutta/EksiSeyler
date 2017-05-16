package com.cunoraz.eksiseyler.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * Created by cuneytcarikci on 05/05/2017.
 */

public class Utils {

    public static boolean isConnected(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo i = conMgr.getActiveNetworkInfo();
            return !(i == null || !i.isConnected() || !i.isAvailable());
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String getEncodedPostName(String url){
        return url.substring(29);
    }
}
