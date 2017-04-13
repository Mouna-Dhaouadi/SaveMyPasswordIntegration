package com.example.mouna.inscriptionandconnection.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mouna.inscriptionandconnection.R;


public class ConfigActivity extends AppCompatActivity {
     SiteModel msite;
    ImageView icon ;
    EditText nom,lien,login,passwd,email;
    ProgressBar bar;
    TextView affMdp;
    ImageButton refresh;
    boolean clicked1=false;
    GenererMdp gn= new GenererMdp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
         msite = (SiteModel) intent.getExtras().getSerializable("siteDetaille");
        afficher_Site(msite);
        passwd.setOnClickListener(new Mdp_Click() );
        passwd.addTextChangedListener(new Mdp_Text_Change() );
        refresh.setOnClickListener(new Refresh_Click());
        affMdp.setOnClickListener(new CopierMdp());


    }
    class CopierMdp implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            passwd.setText(affMdp.getText().toString());

        }
    }
    class Refresh_Click implements View.OnClickListener{

        @Override
        public void onClick(View v) {

                affMdp.setText(gn.generer());

        }
    }
    class Mdp_Click implements View.OnClickListener{

        @Override
        public void onClick(View v) {
           if(!clicked1)
           {
               affMdp.setVisibility(View.VISIBLE);
               refresh.setVisibility(View.VISIBLE);
               affMdp.setText(gn.generer());
           }
        }
    }
    class Mdp_Text_Change implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            new EvaluerMdp().evaluer(passwd.getText().toString(),bar);
        }
    }
    void afficher_Site(SiteModel s)
    {
        icon=(ImageView)findViewById(R.id.imgM);
        lien=(EditText) findViewById(R.id.editTextLinkM);
        nom=(EditText) findViewById(R.id.textSiteNameM);
        login=(EditText) findViewById(R.id.editTextM);
        passwd=(EditText) findViewById(R.id.editPasswdM);
        bar=(ProgressBar)findViewById(R.id.progressBar) ;
        new EvaluerMdp().evaluer(s.passwd,bar);
        email= (EditText) findViewById(R.id.editTextMailM);
        refresh =(ImageButton) findViewById(R.id.refresh);
        affMdp=(TextView)findViewById(R.id.textViewM);

        lien.setText(s.link);
        nom.setText(s.name);
        login.setText(s.login);
        passwd.setText(s.passwd);
        email.setText(s.memail);



        Bitmap bmp = BitmapFactory.decodeByteArray(s.icon, 0, s.icon.length);
        icon.setImageBitmap(bmp);
    }

}
