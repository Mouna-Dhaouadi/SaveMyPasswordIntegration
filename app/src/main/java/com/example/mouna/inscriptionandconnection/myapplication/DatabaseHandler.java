package com.example.mouna.inscriptionandconnection.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;
import com.example.mouna.inscriptionandconnection.R;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.example.mouna.inscriptionandconnection.R;

/**
 * Created by Imen on 08/04/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    public  boolean first_Access =false;
    // Database Version

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SaveMyPass";

    // Contacts table name
    private static final String TABLE_Site = "site";
    private static final String TABLE_Enregistrement = "Enregistrement";

    // Contacts Table Columns names site
    private static final String IDS = "idS";
    private static final String NAMES = "nameS";
    private static final String LINKS = "lienS";
    private static final String ICON = "IconS";
    // Contacts Table Columns names enregistrement
    private static final String IDE = "idE";
    private static final String Date = "date";
    private static final String Login= "login";
    private static final String mail = "mail";
    private static final String mdp = "mdp";
    private static final String SiteID = "SiteID";


 Resources res ;
    SQLiteDatabase db ;
    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        res=context.getResources();
        db = this.getWritableDatabase();

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("****","Create");
        String CREATE_SITE_TABLE = "CREATE TABLE " + TABLE_Site + "("
                + IDS + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAMES + " TEXT,"
                + LINKS + " TEXT," +ICON + " BLOB" + ")";
        db.execSQL(CREATE_SITE_TABLE);
        String CREATE_ENR_TABLE = "CREATE TABLE " + TABLE_Enregistrement + "("
                + IDE + " INTEGER PRIMARY KEY AUTOINCREMENT," + Date + " TEXT,"
                + Login + " TEXT," +mail + " TEXT," +mdp + " TEXT," + SiteID + " INTEGER, "
                + " FOREIGN KEY ("+SiteID+") REFERENCES "+TABLE_Site+"("+IDS+"));";
        db.execSQL(CREATE_ENR_TABLE);
        first_Access=true;
       // db.close();
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Site);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Enregistrement);

        // Create tables again
        onCreate(db);
    }
    public void addSite(Site s,SQLiteDatabase db) {
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAMES, s.name);
        values.put(LINKS, s.link);
        values.put(ICON,s.icon);


        // Inserting Row
        db.insert(TABLE_Site, null, values);
       // db.close(); // Closing database connection
    }
    public void addSite(Site s) {
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAMES, s.name);
        values.put(LINKS, s.link);
        values.put(ICON,s.icon);


        // Inserting Row
        db.insert(TABLE_Site, null, values);
        //db.close(); // Closing database connection
    }
    public void addEnregistrement(Enregistrement s,int id) {
      //  SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Date, s.lastModif.toString());
        values.put(Login, s.login);
        values.put(mail,s.memail);
        values.put(mdp,s.passwd);
        values.put(SiteID,id);



        // Inserting Row
        db.insert(TABLE_Enregistrement, null, values);
       // db.close(); // Closing database connection
    }

    public List<Site> getAllSites() {
        List<Site> siteList = new ArrayList<Site>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Site;

      //  SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Site s = new Site();
                s.name=cursor.getString(1);
                s.link=cursor.getString(2);
                s.icon=cursor.getBlob(3);
                s.ID=cursor.getInt(0);
                // Adding contact to list
                siteList.add(s);
            } while (cursor.moveToNext());
        }

        // return contact list
        return siteList;
    }
    public List<Enregistrement> getAllEnregistrements() {
        List<Enregistrement> siteList = new ArrayList<Enregistrement>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Enregistrement;

      //  SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Enregistrement s = new Enregistrement();
                Site site = getSite(cursor.getInt(5));
                s.name=site.name;
                s.link=site.link;
                s.icon=site.icon;
                s.login=cursor.getString(2);
                s.memail=cursor.getString(3);
                s.passwd=cursor.getString(4);
                s.SiteID=cursor.getInt(5);
                s.ID=cursor.getInt(0);
                s.lastModif = cursor.getString(1);



                // Adding contact to list
                siteList.add(s);
            } while (cursor.moveToNext());
        }

        // return contact list
        return siteList;
    }

    Site getSite(int id) {
      //  SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_Site, new String[] { IDS,
                        NAMES, LINKS,ICON }, IDS + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Site s = new Site(cursor.getString(1),cursor.getString(2),cursor.getBlob(3));
        s.ID=cursor.getInt(0);
        // return s
        return s;
    }
    boolean siteUsed(int id) {
        String selectQuery = "SELECT  Count(*) FROM " + TABLE_Enregistrement+" WHERE SiteID = "+id;

        //  SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
            }
            return false;
        }




    Enregistrement getEnregistrement(int id) throws ParseException {
      //  SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_Enregistrement, new String[] { IDE,
                        Date, Login,mail,mdp,SiteID }, IDE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Site site = getSite(Integer.parseInt(SiteID));
        String strDate = cursor.getString(1);
        Enregistrement s = new Enregistrement(site.name,site.link,strDate, cursor.getString(4),
                cursor.getString(2),cursor.getString(3),cursor.getInt(5),site.icon);
        s.ID=cursor.getInt(0);
        // return s
        return s;
    }
    // Updating single s
    public int updateSite(Site s,int id) {
      //  SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAMES, s.name);
        values.put(LINKS, s.link);
        values.put(ICON, s.icon);

        // updating row
        return db.update(TABLE_Site, values, IDS + " = ?",
                new String[] { String.valueOf(id) });
    }
    public int updateEnregistrement(Enregistrement s,int id) {
       // SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Date, s.lastModif);
        values.put(Login, s.login);
        values.put(mail, s.memail);
        values.put(mdp, s.passwd);

        // updating row
        return db.update(TABLE_Enregistrement, values, IDE + " = ?",
                new String[] { String.valueOf(id) });
    }
    // Deleting single s
    public void deleteSite(int id) {
      //  SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Site, IDS + " = ?",
                new String[] { String.valueOf(id) });
      //  db.close();
    }

    public void deleteEnregistrement(int id) {
       // SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Enregistrement, IDE + " = ?",
                new String[] { String.valueOf(id) });
      //  db.close();
    }


    // Getting contacts Count
    public int getEnregistrementCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Enregistrement;
      //  SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting contacts Count
    public int getSiteCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Site;
       // SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public void initialiseSite( Resources drawable)
    {
        addSite(new Site("PayPal","www.paypal.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.paypal))));
        addSite(new Site("Amazon","www.paypal.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.amazon))));
        addSite(new Site("Drop Box","www.dropbox.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.dropbox))));
        addSite(new Site("Facebook","www.facebook.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.facebook))));
        addSite(new Site("Google +","www.gmail.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.google))));
        addSite(new Site("Linkedin","www.linkedin.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.linkedin))));
        addSite(new Site("Instagram","www.instagram.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.instagram))));
        addSite(new Site("Printerest","www.printerest.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.pinterest))));
        addSite(new Site("Reddit","www.reddit.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.reddit))));
        addSite(new Site("Skype +","www.skype.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.skype))));
        addSite(new Site("Snapchat +","www.snapchat.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.snapchat))));
        addSite(new Site("Tumblr +","www.tumblr.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.tumblr))));
        addSite(new Site("Twitter +","www.twitter.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.twitter))));
        addSite(new Site("Vimeo +","www.vimeo.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.vimeo))));
        addSite(new Site("Whatsapp +","www.whatsapp.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.whatsapp))));
        addSite(new Site("Wikipedia +","www.wikipedia.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.wikipedia))));
        addSite(new Site("Windows +","www.windows.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.windows))));
        addSite(new Site("WordPress +","www.tumblr.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.wordpress))));
        addSite(new Site("Yahoo +","www.yahoo.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.yahoo))));
        addSite(new Site("Youtube","www.youtube.com",getIcon(BitmapFactory.decodeResource( drawable, R.drawable.youtube))));



    }
    byte[] getIcon(Bitmap bmp)
    {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


}