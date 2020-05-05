package com.example.gillz.vaccdrug;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Patient_account extends AppCompatActivity {

    PatientSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new PatientSessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        } else {

            FloatingActionButton fab = findViewById(R.id.floatingActionButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Logout Button Clicked", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    AlertDialog.Builder funga = new AlertDialog.Builder(Patient_account.this);
                    funga.setMessage("Do you want to Logout?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    session.logoutUser();
                                    finish();;


                                }

                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = funga.create();
                    funga.setTitle("Close");
                    alert.show();
                }
            });
        }
    }

}
