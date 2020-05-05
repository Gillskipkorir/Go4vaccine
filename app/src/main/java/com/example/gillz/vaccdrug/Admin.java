package com.example.gillz.vaccdrug;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Admin extends AppCompatActivity  implements Adddoc.OnFragmentInteractionListener,viewdocs.OnFragmentInteractionListener,viewusers.OnFragmentInteractionListener{
    Button btndocregister,btnviewdoc,viewu;
    AdminSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new AdminSessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        } else {
            btndocregister = findViewById(R.id.btnregister);
            /*btnviewdoc = findViewById(R.id.btnviewdoc);
            viewu = findViewById(R.id.btnviewusers);
            */FloatingActionButton fab = findViewById(R.id.afab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Logout Button Clicked", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    AlertDialog.Builder funga = new AlertDialog.Builder(Admin.this);
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


            btndocregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Adddoc homeFragment = new Adddoc();
                    ft.replace(R.id.frame_container, homeFragment);
                    ft.addToBackStack("home");
                    ft.commit();
                }
            });
           /* btnviewdoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ShowToast("Coming soon");

                }
            });
            viewu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowToast("Coming soon");


                }
            });*/

        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void ShowToast(String  msg) {
        Toast.makeText(Admin.this,msg, Toast.LENGTH_LONG).show();

    }

}




