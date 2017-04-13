package com.example.mouna.inscriptionandconnection.myapplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Imen on 31/03/2017.
 */

public class GenererMdp {
    char[] charactersMaj = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    char[] charactersMin ="abcdefghijklmnopqrstuvwxyz".toCharArray();
    char[]num="0123456789".toCharArray();
   char[]sysmboles="~!@#$%^&*()-_?".toCharArray();
    Random r = new Random();

    String generer()
    {
        List l= new ArrayList();

        l.add(charactersMaj[r.nextInt(charactersMaj.length)]);
        l.add(charactersMaj[r.nextInt(charactersMaj.length)]);
        l.add(charactersMaj[r.nextInt(charactersMaj.length)]);
        l.add(charactersMin[r.nextInt(charactersMin.length)]);
        l.add(charactersMin[r.nextInt(charactersMin.length)]);
        l.add(charactersMin[r.nextInt(charactersMin.length)]);
        l.add(num[r.nextInt(num.length)]);
        l.add(num[r.nextInt(num.length)]);
        l.add(num[r.nextInt(num.length)]);
        l.add(sysmboles[r.nextInt(sysmboles.length)]);
        l.add(sysmboles[r.nextInt(sysmboles.length)]);
        l.add(sysmboles[r.nextInt(sysmboles.length)]);
       Collections.shuffle(l);
        StringBuilder ss= new StringBuilder();
        for(int i=0;i<l.size();i++)
        {
            ss.append(l.get(i));
        }
       return ss.toString();



    }
}
