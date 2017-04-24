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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.example.mouna.inscriptionandconnection.R;

public class ConfigActivity extends AppCompatActivity {
     Enregistrement msite;
    ImageView icon ;
    EditText login,passwd,email;
    ProgressBar bar;
    TextView affMdp,nom,lien;
    ImageButton refresh;
    Button button;
    boolean clicked1=false;
    GenererMdp gn= new GenererMdp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
         msite = (Enregistrement) intent.getExtras().getSerializable("siteDetaille");
        afficher_Site(msite);
        passwd.setOnClickListener(new Mdp_Click() );
        passwd.addTextChangedListener(new Mdp_Text_Change() );
        refresh.setOnClickListener(new Refresh_Click());
        affMdp.setOnClickListener(new CopierMdp());
        button.setOnClickListener(new UpdateEnrg());

    }
    class UpdateEnrg implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String d= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            Enregistrement e = new Enregistrement(msite.name,msite.link, d,passwd.getText().toString(),login.getText().toString(),
                    email.getText().toString(),msite.SiteID,msite.icon);
            ListEnregistrements.db.updateEnregistrement(e,msite.SiteID);
            Intent myIntent = new Intent( ConfigActivity.this,ListEnregistrements.class);
            startActivity(myIntent);
        }
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
    void afficher_Site(Enregistrement s)
    {
        icon=(ImageView)findViewById(R.id.imgM);
        lien=(TextView) findViewById(R.id.editTextLinkM);
        nom=(TextView) findViewById(R.id.textSiteNameM);
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

        button=(Button)findViewById(R.id.BAjout);

        Bitmap bmp = BitmapFactory.decodeByteArray(s.icon, 0, s.icon.length);
        icon.setImageBitmap(bmp);
    }

}
