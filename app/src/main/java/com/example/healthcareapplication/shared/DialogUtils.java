package com.example.healthcareapplication.shared;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtils {

    public interface OnYesClickListener {
        void onYesClicked();
    }

    public interface OnNoClickListener {
        void onNoClicked();
    }

    public static void showYesNoDialog(Context context, String title, String message, OnYesClickListener yesClickListener, OnNoClickListener noClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yesClickListener != null) {
                            yesClickListener.onYesClicked();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (noClickListener != null) {
                            noClickListener.onNoClicked();
                        }
                    }
                })
                .show();
    }
}

