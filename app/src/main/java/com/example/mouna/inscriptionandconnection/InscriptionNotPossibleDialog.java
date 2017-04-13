package com.example.mouna.inscriptionandconnection;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mouna on 26/03/2017.
 */



public class InscriptionNotPossibleDialog {

    VocalInscriptionActivity activity;
    String text,title;

    public InscriptionNotPossibleDialog(VocalInscriptionActivity a, String title,String text) {
        this.activity=a;
        this.text = text;
        this.title=title;
    }

    public void show()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        builder.setTitle(title);
        builder.setMessage(text);

        String positiveText = activity.getString(android.R.string.yes);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.moveToChoiceInscription();
                    }
                });


        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }




}





