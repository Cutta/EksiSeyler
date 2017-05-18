package com.cunoraz.eksiseyler.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.cunoraz.eksiseyler.R;

/**
 * Created by cuneytcarikci on 03/05/2017.
 */

public class DialogBuilder {

    public static AlertDialog.Builder infoDialog(Context context, @StringRes int title, @StringRes int message) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_action_ok, null);
    }
}
