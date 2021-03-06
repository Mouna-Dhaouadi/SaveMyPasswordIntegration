package com.example.mouna.inscriptionandconnection;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Mouna on 26/03/2017.
 */

public class VerifyVocalInscriptionDialog {

    VocalInscriptionActivity activity;
    String text,title;

    public VerifyVocalInscriptionDialog(VocalInscriptionActivity a, String title,String text) {
        this.activity=a;
        this.text = text;
        this.title=title;
    }

    public void show()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        builder.setTitle(title);
        builder.setMessage(text);

        String positiveText = activity.getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.inscrire();
                    }
                });


        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }




}
