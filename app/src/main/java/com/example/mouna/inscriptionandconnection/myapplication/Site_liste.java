package com.example.mouna.inscriptionandconnection.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.mouna.inscriptionandconnection.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class Site_liste extends AppCompatActivity {
   List<Site> sites ;
    private static int RESULT_LOAD_IMG = 1;
    SiteAdabter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_liste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sites= ListEnregistrements.db.getAllSites();
        RecyclerView rvSites = (RecyclerView) findViewById(R.id.my_recycler_view_site);
         adapter = new SiteAdabter(this, sites);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvSites.addItemDecoration(itemDecoration);
        rvSites.setItemAnimator(new SlideInUpAnimator());
        rvSites.setAdapter(adapter);
        rvSites.setLayoutManager(new LinearLayoutManager(this));
        rvSites.addOnItemTouchListener(new RecyclerItemClickListener(Site_liste.this, rvSites, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AddActivity.choosed=true;
               AddActivity.msite=sites.get(position);
                Intent myIntent = new Intent( Site_liste.this,AddActivity.class);

                startActivity(myIntent);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Site_liste.this);
                alertDialogBuilder.setMessage("Choisir une action");
                alertDialogBuilder.setPositiveButton("Editer",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showAlertDialog(sites.get(position),position);

                    }});
                alertDialogBuilder.setNegativeButton("Supprimer",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       if((ListEnregistrements.db.siteUsed(sites.get(position).ID)))
                        {
                            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(Site_liste.this);
                            alertDialogBuilder1.setMessage("Ce site est utilise, vous ne pouvez pas le supprimer");
                            alertDialogBuilder1.setTitle("Erreur");
                            alertDialogBuilder1.setCancelable(true);
                            alertDialogBuilder1.show();
                        }
                        else
                        {showDiagSupprimer(sites.get(position),position);}

                    }});
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.show();


            }


        }));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertDialog();

            }
        });
    }
    private void showDiagSupprimer(Site s,int i)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Site_liste.this);
        alertDialogBuilder.setMessage("Vous etes sure !");
        final Site ss=s;
        final int ii= i;
        alertDialogBuilder.setPositiveButton("Oui",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sites.remove(ii);
                adapter.notifyDataSetChanged();
                ListEnregistrements.db.deleteSite(ss.ID);
            }});
        alertDialogBuilder.setNegativeButton("Non",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            finish();
            }});
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.show();
    }
    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText nom = new EditText(this);
        nom.setHint(R.string.NomSite);
        final EditText lien = new EditText(this);
        lien.setTextColor(Color.BLUE);
        lien.setHint(R.string.lien);
        layout.addView(nom);
        layout.addView(lien);
        builder.setView(layout);
        builder.setTitle(R.string.AjoutP);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Site s=new Site(nom.getText().toString(),lien.getText().toString(),getIcon(R.drawable.lock));
                ListEnregistrements.db.addSite(s);

                sites.add(s);
                adapter.notifyDataSetChanged();


            }
        });
        builder.setNegativeButton(R.string.telecharger, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                loadImagefromGallery(layout);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }
    private void showAlertDialog(Site s,int i) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText nom = new EditText(this);
        nom.setText(s.name);
        final EditText lien = new EditText(this);
        lien.setTextColor(Color.BLUE);
        lien.setText(s.link);
        layout.addView(nom);
        layout.addView(lien);
        builder.setView(layout);
        builder.setTitle(R.string.AjoutP);
        final Site site=s;
        final int ii =i;
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Site ss=new Site(nom.getText().toString(),lien.getText().toString(),site.icon);
                ListEnregistrements.db.updateSite(ss,site.ID);

                sites.add(ii, ListEnregistrements.db.getSite(sites.get(ii).ID));
                sites.remove(ii+1);
                adapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton(R.string.telecharger, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                loadImagefromGallery(layout);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }
    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    byte[] getIcon(int r)
    {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), r);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
