package com.example.mouna.inscriptionandconnection.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mouna.inscriptionandconnection.R;

import java.text.SimpleDateFormat;

public class DetaillActivity extends AppCompatActivity {

    ImageView icon ;
    TextView nom,lien,login,dateModif,passwd,email;
    CheckBox passAffiche;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final SiteModel msite = (SiteModel) intent.getExtras().getSerializable("siteDetaille");
        afficher_Site(msite);
    passAffiche.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                passwd.setTransformationMethod(null );
            }
            else
            {
                passwd.setTransformationMethod(new PasswordTransformationMethod());
            }

        }
    });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DetaillActivity.this, ConfigActivity.class);

                myIntent.putExtra("siteDetaille",  msite);

                startActivity(myIntent);
            }
        });
    }
    void afficher_Site(SiteModel s)
    {
        icon=(ImageView)findViewById(R.id.imgDetaille);
        lien=(TextView)findViewById(R.id.editTextLinkAff);
        nom=(TextView)findViewById(R.id.textSiteName);
        login=(TextView)findViewById(R.id.editTextAff);
        passwd=(TextView)findViewById(R.id.editPasswdAff);
        passAffiche=(CheckBox)findViewById(R.id.checkBox);
        email= (TextView)findViewById(R.id.editTextEmailAff);
        dateModif=(TextView)findViewById(R.id.editDateAff);
        passwd.setTransformationMethod(new PasswordTransformationMethod());
        lien.setText(s.link);
        nom.setText(s.name);
        login.setText(s.login);
        passwd.setText(s.passwd);
        email.setText(s.memail);

        dateModif.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(s.lastModif));

        Bitmap bmp = BitmapFactory.decodeByteArray(s.icon, 0, s.icon.length);
        icon.setImageBitmap(bmp);
    }


}
