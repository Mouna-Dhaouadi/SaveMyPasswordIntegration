package com.example.mouna.inscriptionandconnection;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by Mouna on 28/03/2017.
 */

public class ConnectionChoiceActivity extends AppCompatActivity implements View.OnClickListener{

    Button button_choix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_choice);

        button_choix=(Button)findViewById(R.id.button_choix_connection);
        button_choix.setOnClickListener(this );

    }

    @Override
    public void onClick(View v) {
        Intent intent;


        if (((RadioButton) findViewById(R.id.radioButton_facial_connection)).isChecked()) {
            if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString( getString(R.string.inscribedFacially), "").equals(getString(R.string.yes))) {
                // intent = new Intent(ConnectionChoiceActivity.this,FacialConnectionActivity.class);
                // startActivity(intent);
            } else {

               new ShouldInscribeDialog(ConnectionChoiceActivity.this, getString(R.string.NonInscritFaciale)).show();
            }
        }

        else if (((RadioButton) findViewById(R.id.radioButton_vocal_connection)).isChecked()) {
            if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString( getString(R.string.inscrbedVocally), "").equals( getString(R.string.yes)  )) {
                intent = new Intent(ConnectionChoiceActivity.this, VocalConnectionActivity.class);
                startActivity(intent);
            }
         else {
                new ShouldInscribeDialog(ConnectionChoiceActivity.this, getString(R.string.NonInscritVocale) ).show();

        }
    }
        else
                if ( ((RadioButton)findViewById(R.id.radioButton_password_connection)).isChecked()  )
        {
        if( PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString( getString(R.string.inscribedPW), "").equals(  getString(R.string.yes) ) )
        {
            intent = new Intent(ConnectionChoiceActivity.this,PasswordConnectionActivity.class);
            startActivity(intent);
        }

        else
        {
            new ShouldInscribeDialog(ConnectionChoiceActivity.this, getString(R.string.NonInscritMDP) ).show();


        }
        }

        else
                {

                  new MyAlertDialog(ConnectionChoiceActivity.this,getString(R.string.MustChooseTitle),getString(R.string.MustchooseConnection)).show();


                }


    }
}

