package com.example.mouna.inscriptionandconnection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mouna on 26/03/2017.
 */
 public class VocalInscriptionActivity  extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemClickListener{

        Button button;
        TextView textView;
        ListView listView;
        ArrayList<String> matches;
         String password="";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vocal_inscription);

            button = (Button) findViewById(R.id.startSpeech);
            textView = (TextView) findViewById(R.id.textViewInscription);
            listView = (ListView) findViewById(R.id.list);

            PackageManager pm = getPackageManager();
            List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

            if (activities.size() != 0) {
                button.setOnClickListener(this);
            }

            else {

                new  InscriptionNotPossibleDialog(VocalInscriptionActivity.this, getString(R.string.InscriptionImpossible), getString(R.string.autreInscription)).show();

            }


        }



        @Override
        public void onClick(View v) {
            Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.startTalking);
            startActivityForResult(intent, 0);

        }


        @Override
        protected void onActivityResult(int requestCode , int resultCode, Intent data)
        {
            if (resultCode == RESULT_OK)
            {
                textView.setText(R.string.chooseItem);
                matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                listView.setOnItemClickListener(this);
                listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,matches));
            }

            else
            {
                listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[]{"Pas de résultat"}));
            }

        }



        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Récuprer string
            password=  matches.get(position);

            new VerifyVocalInscriptionDialog(VocalInscriptionActivity.this, getString(R.string.Confirmation),getString(R.string.DemandeInscription)+ " "+password).show();

        }



    public void inscrire()
    {
        String passwordHached;
        try{
            //Hash
            passwordHached=hash(password);
        }


        catch(Exception e)
        {
            passwordHached=password;
        }


        //Saving the password in shared prefrences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(getString(R.string.vocalPassword), passwordHached);
        edit.putString(getString(R.string.inscrbedVocally), getString(R.string.yes));
        edit.commit();

        Intent intent=new Intent(VocalInscriptionActivity.this, VocalConnectionActivity.class);
        startActivity(intent);


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



          public void moveToChoiceInscription() {
              Intent intent = new Intent(VocalInscriptionActivity.this, InscriptionChoiceActivity.class);

              startActivity(intent);
          }

    }



