package com.example.mouna.inscriptionandconnection;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mouna.inscriptionandconnection.myapplication.ListEnregistrements;

import java.math.BigInteger;
import java.security.MessageDigest;


/**
 * Created by Mouna on 26/03/2017.
 */

public class PasswordConnectionActivity extends AppCompatActivity {

        Button button;
        EditText editText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_password_connection);

            button=(Button)findViewById(R.id.buttonConnection);
            editText=(EditText)findViewById(R.id.enterPasswordFieldForConnection);

            //Extract the data…
            final String password =  PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString(getString(R.string.password), "");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String trial=editText.getText().toString();

                    String trialHached;
                    try {
                        trialHached = hash(trial);
                    }
                    catch (Exception e)
                    {
                        trialHached =trial;
                    }


                    if (trialHached.equals(password))
                        {
                            Intent intent =new Intent( PasswordConnectionActivity.this, ListEnregistrements.class);
                            startActivity(intent);
                        }

                    else
                        {
                            new MyAlertDialog( PasswordConnectionActivity.this,getString(R.string.connexionEchouée) , getString(R.string.FauxMDP)).show();

                        }

                    }

            });



        }



    public String hash(String s ) throws  Exception {


        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(s.getBytes());
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }
        return  hashtext ;
    }


    }


