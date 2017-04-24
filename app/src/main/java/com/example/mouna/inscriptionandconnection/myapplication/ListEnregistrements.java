package com.example.mouna.inscriptionandconnection.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import com.example.mouna.inscriptionandconnection.R;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ListEnregistrements extends AppCompatActivity {

    static List<Enregistrement> enregistrements ;
    static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_enregistrements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);
        if(db.first_Access)
        {
            db.initialiseSite(getResources());
        }

        enregistrements= db.getAllEnregistrements();

       RecyclerView rvSites = (RecyclerView) findViewById(R.id.my_recycler_view);
       // RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
       // recyclerView.addItemDecoration(itemDecoration);
        if(enregistrements != null)
        {
            Toast.makeText(this,"I am in",Toast.LENGTH_SHORT);
            final EnregistrementAdabter adapter = new EnregistrementAdabter(this, enregistrements);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
            rvSites.addItemDecoration(itemDecoration);
            rvSites.setItemAnimator(new SlideInUpAnimator());
            rvSites.setAdapter(adapter);
            rvSites.setLayoutManager(new LinearLayoutManager(this));
            rvSites.addOnItemTouchListener(new RecyclerItemClickListener(ListEnregistrements.this, rvSites, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent myIntent = new Intent(ListEnregistrements.this, DetaillActivity.class);

                    myIntent.putExtra("siteDetaille",  enregistrements.get(position));

                    startActivity(myIntent);
                }

                @Override
                public void onItemLongClick(View view, int position) {
                     view.setBackgroundColor(Color.RED);
                    showDiagSupprimer(enregistrements.get(position),position,adapter);

                }

            }));

        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("La liste est vide");
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.show();

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListEnregistrements.this,AddActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        return true;

    }
    public void onGroupItemClick(MenuItem item)
    {
        Locale l = getResources().getConfiguration().locale;
        Toast.makeText(ListEnregistrements.this,l.toString(),Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.group_item1:


            {setLocale("fr");
                Toast.makeText(ListEnregistrements.this,"Francais",Toast.LENGTH_LONG).show();}


            case R.id.group_item2:


            {setLocale("en");

                     Toast.makeText(ListEnregistrements.this,"English",Toast.LENGTH_LONG).show();}


            case R.id.group_item3:


            {setLocale("de");

                   Toast.makeText(ListEnregistrements.this,"Deutsch",Toast.LENGTH_LONG).show();}




        }
    }
    private void showDiagSupprimer(Enregistrement s, int i, EnregistrementAdabter adabter)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListEnregistrements.this);
        alertDialogBuilder.setTitle("Supprimer");
        alertDialogBuilder.setMessage("Voulez-vous supprimer cet enregistrement");
        final Enregistrement ss=s;
        final int ii= i;
        final EnregistrementAdabter adabterr=adabter;
        alertDialogBuilder.setPositiveButton("Oui",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enregistrements.remove(ii);
                adabterr.notifyDataSetChanged();
                ListEnregistrements.db.deleteEnregistrement(ss.ID);
            }});
        alertDialogBuilder.setNegativeButton("Non",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }});
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.show();
    }

  /*  void setLangChecked(Locale l)
    {
        if(l == Locale.ENGLISH)
        {
           MenuItem m =(MenuItem) findViewById(R.id.group_item2);
            m.setChecked(true);
        }
        else if(l == Locale.FRANCE)
        {
            MenuItem m =(MenuItem) findViewById(R.id.group_item1);
            m.setChecked(true);
        }
        else if(l == Locale.GERMANY)
        {
            MenuItem m =(MenuItem) findViewById(R.id.group_item3);
            m.setChecked(true);
        }}*/




    void setLocale(String langue)
    {
        Locale myLocale = new Locale(langue);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(ListEnregistrements.this, ListEnregistrements.class);
        startActivity(refresh);
    }
}
