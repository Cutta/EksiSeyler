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
            if (i == null || !i.isConnected() || !i.isAvailable()) {
                Toast.makeText(context, "İnternet bağlantısı mevcut değil.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
