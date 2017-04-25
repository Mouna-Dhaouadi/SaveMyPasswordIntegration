package com.example.mouna.inscriptionandconnection.myapplication;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Imen on 28/03/2017.
 */

public class Enregistrement  implements Serializable {
    public int ID;
    public String name;
    public String link;
    public String lastModif;
    public String passwd;
    public String login;
    public String memail;
    public int SiteID;
    public byte[] icon=null;
    public Enregistrement( String name,
                      String link,
                           String lastModif,
                      String passwd,
                      String login,
                      String memail,
                           int SiteId,
                      byte[] icon
                      )
    {
        this.SiteID=SiteId;
        this.name=name;
        this.link=link;
        this.lastModif=lastModif;
        this.passwd=passwd;
        this.login=login;
        this.memail=memail;
        this.icon=icon;

    }
    public Enregistrement(  String name,
                       String link,
                            String lastModif,
                       String passwd,
                       String login,
                       String memail,
                            int SiteId
                       )
    {
        this.SiteID=SiteId;
        this.name=name;
        this.link=link;
        this.lastModif=lastModif;
        this.passwd=passwd;
        this.login=login;
        this.memail=memail;


    }


    public Enregistrement() {

    }
}
