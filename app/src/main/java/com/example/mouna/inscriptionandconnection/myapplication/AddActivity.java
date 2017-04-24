package com.example.mouna.inscriptionandconnection.myapplication;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mouna.inscriptionandconnection.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    static Site msite;

    ImageView icon ;
    EditText login,passwd,email;
    ProgressBar bar;
    TextView affMdp,nom,lien;
    ImageButton refresh;
    boolean clicked1=false;
    GenererMdp gn= new GenererMdp();
    Button button;
   static Boolean choosed =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        afficher_Site();
        passwd.setOnClickListener(new Mdp_Click() );
        passwd.addTextChangedListener(new Mdp_Text_Change() );
        refresh.setOnClickListener(new Refresh_Click());
        affMdp.setOnClickListener(new CopierMdp());
        button.setOnClickListener(new Add_Enregistrement());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddSite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AddActivity.this, Site_liste.class);
                startActivity(myIntent);
            }
        });
if(msite != null)
{
    remplir(msite);
}

    }
    void remplir(Site s)
    {
        icon.setImageBitmap(BitmapFactory.decodeByteArray(s.icon, 0, s.icon.length));
        nom.setText(s.name);
        lien.setText(s.link);
    }
    class Add_Enregistrement implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(choosed == false)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddActivity.this);
                alertDialogBuilder.setMessage("Choisir un site");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.show();
                return;
            }

            else if((TextUtils.isEmpty(login.getText().toString()))) {
                login.setError("remplir ce champ");
                return;
            }
            else if((TextUtils.isEmpty(email.getText().toString()))) {
                login.setError("remplir ce champ");
                return;
            }
            else if((TextUtils.isEmpty(passwd.getText().toString()))) {
                login.setError("remplir ce champ");
                return;
            }
            else
            {
               String d= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                Enregistrement e = new Enregistrement(msite.name,msite.link, d,passwd.getText().toString(),login.getText().toString(),
                        email.getText().toString(),msite.ID,msite.icon);

                ListEnregistrements.db.addEnregistrement(e,msite.ID);
                Intent myIntent = new Intent( AddActivity.this,ListEnregistrements.class);
                startActivity(myIntent);

            }



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                Toast.makeText(this, "okkk",
                        Toast.LENGTH_LONG).show();
                // Get the Image from data

              /*  Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));*/

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
    void afficher_Site()
    {
        icon=(ImageView)findViewById(R.id.imgAdd);
        lien=(TextView) findViewById(R.id.editTextLinkAdd);
        nom=(TextView) findViewById(R.id.textSiteNameAdd);
        login=(EditText) findViewById(R.id.editTextAdd);
        passwd=(EditText) findViewById(R.id.editPasswdAdd);
        bar=(ProgressBar)findViewById(R.id.progressBarAdd) ;

        email= (EditText) findViewById(R.id.editTextAdd2);
        refresh =(ImageButton) findViewById(R.id.refresh1);
        affMdp=(TextView)findViewById(R.id.textViewMdpAdd);
        button=(Button)findViewById(R.id.buttonAdd);


    }

}
