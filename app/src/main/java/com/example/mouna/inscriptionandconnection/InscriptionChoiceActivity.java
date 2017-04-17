package com.example.mouna.inscriptionandconnection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by Mouna on 26/03/2017.
 */

public class InscriptionChoiceActivity extends AppCompatActivity implements View.OnClickListener{

    Button button_choix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription_choice);

        button_choix=(Button)findViewById(R.id.button_choix_inscription);
        button_choix.setOnClickListener(this );

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        MyAlertDialog scd;


        if ( ((RadioButton)findViewById(R.id.radioButton_facial_inscription)).isChecked() )
        {
           // don't forget to put this with the code:  edit.putString( getString(R.string.inscribedFacially), getString(R.string.yes));
           intent = new Intent(InscriptionChoiceActivity.this,FacialInscriptionActivity.class);
            startActivity(intent);

        }
        else
            if ( ((RadioButton)findViewById(R.id.radioButton_vocal_inscription)).isChecked()  )
            {
                intent = new Intent(InscriptionChoiceActivity.this,VocalInscriptionActivity.class);
                startActivity(intent);

            }
        else
            if ( ((RadioButton)findViewById(R.id.radioButton_password_inscription)).isChecked()  ) {
                intent = new Intent(InscriptionChoiceActivity.this,PasswordInscriptionActivity.class);
                startActivity(intent);

            }
            else
                if (((RadioButton)findViewById(R.id.radioButton_facial_inscription)).isChecked()){
                    intent = new Intent(InscriptionChoiceActivity.this,FacialInscriptionActivity.class);
                    startActivity(intent);
                }

        else {
                new MyAlertDialog(InscriptionChoiceActivity.this, getString(R.string.MustChooseTitle),getString(R.string.MustchooseInscription)).show();
            }




    }
}
