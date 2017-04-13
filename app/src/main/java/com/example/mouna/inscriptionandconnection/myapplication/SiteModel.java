package com.example.mouna.inscriptionandconnection.myapplication;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Imen on 28/03/2017.
 */

public class SiteModel  implements Serializable {
    public String name;
    public String link;
    public Date lastModif;
    public String passwd;
    public String login;
    public String memail;
    public byte[] icon=null;
    public SiteModel( String name,
                      String link,
                      Date lastModif,
                      String passwd,
                      String login,
                      String memail,
                      byte[] icon
                      )
    {
        this.name=name;
        this.link=link;
        this.lastModif=lastModif;
        this.passwd=passwd;
        this.login=login;
        this.memail=memail;
        this.icon=icon;

    }
    public SiteModel(  String name,
                       String link,
                       Date lastModif,
                       String passwd,
                       String login,
                       String memail
                       )
    {
        this.name=name;
        this.link=link;
        this.lastModif=lastModif;
        this.passwd=passwd;
        this.login=login;
        this.memail=memail;


    }


}
