package com.example.mouna.inscriptionandconnection;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Mouna on 26/03/2017.
 */


public class ShouldInscribeDialog  {

    Activity activity;
    String choice;

    public ShouldInscribeDialog(Activity a, String choice) {
        this.activity=a;
        this.choice = choice;
    }


    public void show()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        builder.setTitle(activity.getString(R.string.NonInscritTitle));
        builder.setMessage(choice);

        String positiveText = activity.getString(R.string.inscription);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveToInscription();
                    }
                });


        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }



    public void moveToInscription( )
    {

        Intent intent;

        if (choice.equals(activity.getString(R.string.NonInscritMDP)))
        {  intent=new Intent(activity,   PasswordInscriptionActivity.class  );
            activity.startActivity(intent);
        }
        else if (choice.equals(activity.getString(R.string.NonInscritFaciale)))
        {
            ////
        }
        else if ( choice.equals(activity.getString(R.string.NonInscritVocale))  )
        {     intent=new Intent(activity,   VocalInscriptionActivity.class  );
            activity.startActivity(intent);  }

    }

}
