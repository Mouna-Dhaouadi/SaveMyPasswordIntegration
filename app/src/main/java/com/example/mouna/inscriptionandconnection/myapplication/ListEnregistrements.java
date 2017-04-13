package com.example.mouna.inscriptionandconnection.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mouna.inscriptionandconnection.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ListEnregistrements extends AppCompatActivity {

    static ArrayList<SiteModel> sites ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_enregistrements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        sites= new ArrayList<SiteModel>();
        SiteModel m=new SiteModel("facebook","facebook.com",new Date(),"fhdskh","imen","iman.tra96@gmail.com",getIcon());
        for(int i=0;i<10;i++)
        {
            sites.add(m);
        }


        RecyclerView rvSites = (RecyclerView) findViewById(R.id.my_recycler_view);
       // RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
       // recyclerView.addItemDecoration(itemDecoration);

        SIteAdabter adapter = new SIteAdabter(this, sites);

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

                myIntent.putExtra("siteDetaille",  sites.get(position));

                startActivity(myIntent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // view.setBackgroundColor(Color.RED);
                Toast.makeText(ListEnregistrements.this, "long click "+position+" "+view.toString(),
                        Toast.LENGTH_LONG).show();


            }

        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListEnregistrements.this, AddActivity.class);
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



    byte[] getIcon()
    {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
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
