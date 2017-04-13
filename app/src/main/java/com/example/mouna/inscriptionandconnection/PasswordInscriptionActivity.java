package com.example.mouna.inscriptionandconnection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Mouna on 26/03/2017.
 */

public class PasswordInscriptionActivity extends AppCompatActivity
{

        Button button;
        EditText editText;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_password_inscription);

            button=(Button)findViewById(R.id.buttonInscription);
            editText=(EditText)findViewById(R.id.enterPasswordField);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password=editText.getText().toString();
                    String passwordHached;

                    try {
                        //Hash
                        passwordHached =hash(password);

                    }
                    catch(Exception e)
                    {
                        passwordHached=password;
                    }


                    //Saving the password in shared prefrences
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString(getString(R.string.password), passwordHached);
                    edit.putString( getString(R.string.inscribedPW),getString(R.string.yes));
                    edit.commit();



                    Intent intent=new Intent(PasswordInscriptionActivity.this, PasswordConnectionActivity.class);
                    startActivity(intent);


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



