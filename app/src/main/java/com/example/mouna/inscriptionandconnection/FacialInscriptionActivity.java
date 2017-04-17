package com.example.mouna.inscriptionandconnection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

public class FacialInscriptionActivity extends AppCompatActivity {

    // Progress dialog popped up when communicating with server.
    ProgressDialog progressDialog;

    private void setUiBeforeBackgroundTask() {
        progressDialog.show();
    }

    // Show the status of background detection task on screen.
    private void setUiDuringBackgroundTask(String progress) {
        progressDialog.setMessage(progress);

        setInfo(progress);
    }
    // Set the information panel on screen.
    private void setInfo(String info) {
        TextView textView = (TextView) findViewById(R.id.info);
        textView.setText(info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial_inscription);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.progress_dialog_title));
        Button btTraining = (Button) findViewById(R.id.btTraining);
        btTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String personGroupId = UUID.randomUUID().toString();

                    Intent intent = new Intent(FacialInscriptionActivity.this, PersonGroupActivity.class);
                    intent.putExtra("AddNewPersonGroup", true);
                    intent.putExtra("PersonGroupName", "");
                    intent.putExtra("PersonGroupId", personGroupId);
                    startActivity(intent);

            }
        });
    }


}
