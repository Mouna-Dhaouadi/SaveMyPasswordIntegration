package com.example.mouna.inscriptionandconnection.myapplication;

import android.graphics.Color;
import android.widget.ProgressBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Imen on 31/03/2017.
 */

public class EvaluerMdp {
    void evaluer(String password, ProgressBar bar)
    {
        int count=0;
        if(password.length()>9)
        {
            count++;
        }
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());

        if(hasUppercase)
            count++;
        if(hasLowercase)
            count++;

        Pattern p1 = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p1.matcher(password);
        if(m.find())
            count++;
        Pattern p2 = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
        Matcher m2 = p2.matcher(password);
        if(m2.find())
            count++;
        if(count>0&&count<3)
        {
            bar.getProgressDrawable().setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        else if(count>2&&count<5)
        {
            bar.getProgressDrawable().setColorFilter(
                    Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        else if(count==5)
        {
            bar.getProgressDrawable().setColorFilter(
                    Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        bar.setProgress(count);

    }


}
