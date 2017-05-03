package com.cunoraz.eksiseyler.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.ui.main.MainActivity;

/**
 * Created by cuneytcarikci on 03/05/2017.
 */

public class DialogBuilder {
    /*AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
            .setTitle("\u2713 " + "Tasarruf Modu Etkin")
            .setMessage("Bundan sonraki içerik görüntülemenizde, resimler yüklenmez ve data paketinizden tasarruf edersiniz.")
            .setPositiveButton("Tamam", null)
            .create();
        dialog.show();*/
    public static AlertDialog.Builder infoDialog(Context context, @StringRes int title, @StringRes int message) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_action_ok, null);
    }
}
