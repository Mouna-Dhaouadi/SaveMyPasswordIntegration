package com.example.mouna.inscriptionandconnection.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.example.mouna.inscriptionandconnection.R;


public class AddActivity extends AppCompatActivity {
    ArrayList<Bitmap> mList = new ArrayList<Bitmap>();
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    SiteModel msite;
    ImageView icon ;
    EditText nom,lien,login,passwd,email;
    ProgressBar bar;
    TextView affMdp;
    ImageButton refresh;
    boolean clicked1=false;
    GenererMdp gn= new GenererMdp();
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView iconView =(ImageView)findViewById(R.id.img);
        setSupportActionBar(toolbar);
        afficher_Site();
        passwd.setOnClickListener(new Mdp_Click() );
        passwd.addTextChangedListener(new Mdp_Text_Change() );
        refresh.setOnClickListener(new Refresh_Click());
        affMdp.setOnClickListener(new CopierMdp());
        button.setOnClickListener(new Add_Enregistrement());

        for (int i = 0; i< ListEnregistrements.sites.size(); i++)
        {
            mList.add(BitmapFactory.decodeByteArray(ListEnregistrements.sites.get(i).icon, 0, ListEnregistrements.sites.get(i).icon.length));
        }
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

    }
    class Add_Enregistrement implements View.OnClickListener{

        @Override
        public void onClick(View v) {

           Toast.makeText(getBaseContext(),"Souleima's work",Toast.LENGTH_SHORT);

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
    private void showAlertDialog() {
        // Prepare grid view
        GridView gridView = new GridView(this);


        gridView.setAdapter(new ImageAdapter(AddActivity.this,mList));


        gridView.setNumColumns(5);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.w("hhiiii","********");
            }
        });

        // Set grid view to alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ScrollView sview = new ScrollView(AddActivity.this);
        sview.addView(gridView);
        builder.setView(sview);
        builder.setTitle(R.string.choisirUneIcone);
        builder.setPositiveButton(R.string.telecharger, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                loadImagefromGallery(sview);
            }
        });
        builder.setCancelable(true);

        builder.show();
    }
    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
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
        lien=(EditText) findViewById(R.id.editTextLinkAdd);
        nom=(EditText) findViewById(R.id.textSiteNameAdd);
        login=(EditText) findViewById(R.id.editTextAdd);
        passwd=(EditText) findViewById(R.id.editPasswdAdd);
        bar=(ProgressBar)findViewById(R.id.progressBarAdd) ;

        email= (EditText) findViewById(R.id.editTextAdd2);
        refresh =(ImageButton) findViewById(R.id.refresh1);
        affMdp=(TextView)findViewById(R.id.textViewMdpAdd);
        button=(Button)findViewById(R.id.buttonAdd);


    }

}
