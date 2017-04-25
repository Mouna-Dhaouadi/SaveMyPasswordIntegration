package com.example.mouna.inscriptionandconnection.myapplication;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Imen on 08/04/2017.
 */

public class Site implements Serializable {
    public String name;
    public String link;
    public int ID;
    public boolean used=false;

    public byte[] icon=null;
    public Site( String name,
                      String link,

                      byte[] icon
    )
    {
        this.name=name;
        this.link=link;

        this.icon=icon;

    }
    public Site(  String name,
                       String link   )
    {
        this.name=name;
        this.link=link;
    }
     public Site() {

    }
}
