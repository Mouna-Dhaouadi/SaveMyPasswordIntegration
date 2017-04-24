package com.example.mouna.inscriptionandconnection;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import com.example.mouna.inscriptionandconnection.myapplication.ListEnregistrements;

/**
 * Created by Mouna on 26/03/2017.
 */

public class VocalConnectionActivity  extends AppCompatActivity implements View.OnClickListener {

        Button button;
        TextView textView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vocal_connection);

            textView = (TextView) findViewById(R.id.textViewConnection);
            button = (Button) findViewById(R.id.buttonConnection);
            button.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,R.string.startTalking);
            startActivityForResult(intent, 0);

        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            //Extract the data…
            final String password = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString(getString(R.string.vocalPassword), "");

            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                boolean success=false;

                for (int i = 0; i < matches.size(); i++) {

                    String hashedTrial;

                    try {
                        hashedTrial=hash(matches.get(i));
                    }
                    catch (Exception e)
                    {
                        hashedTrial=matches.get(i);
                    }

                    if (   hashedTrial.equals(password)     ) {
                             success=true;
                        //On doit le connecter
                        Intent intent =new Intent( VocalConnectionActivity.this, ListEnregistrements.class);
                            startActivity(intent);
                       
                        break;
                    }


                }

                if (! success) {
                    new MyAlertDialog( VocalConnectionActivity.this,getString(R.string.connexionEchouée) , getString(R.string.FauxVOCAL)).show();

                    }

            }

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



