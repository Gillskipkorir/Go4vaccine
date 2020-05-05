package com.example.gillz.vaccdrug;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

public class Doctor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,addvaccine.OnFragmentInteractionListener,faciltydet.OnFragmentInteractionListener,addvaccinedetail.OnFragmentInteractionListener {

    AlertDialog.Builder builder;

    UserSessionManager session;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        } else {
            session = new UserSessionManager(getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();
             Username = user.get(UserSessionManager.KEY_DOCTOR_ID_NUMBER);


            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            initToolbar();
        }
    }




    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AlertDialog.Builder funga=new AlertDialog.Builder(Doctor.this);
        funga.setMessage("Do you want to go Home?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent(Doctor.this,Homeactivity.class);
                        startActivity(intent);
                        finish();
                    }

                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert= funga.create();
        funga.setTitle("Close");
        alert.show();
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            AlertDialog.Builder funga=new AlertDialog.Builder(Doctor.this);
            funga.setMessage("Do you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            session.logoutUser();
                            finish();


                        }

                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert= funga.create();
            funga.setTitle("Close");
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addvaccine) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            addvaccine add = new addvaccine();
            ft.replace(R.id.docframe_container,add);
            ft.addToBackStack("Vaccine");
            ft.commit();

        }  /*else if (id == R.id.nav_addvaccinedetails) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            addvaccinedetail add = new addvaccinedetail();
            ft.replace(R.id.docframe_container,add);
            ft.addToBackStack("Vaccinedetail");
            ft.commit();

        }*/ else if (id == R.id.nav_faciltydet) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            faciltydet add = new faciltydet();
            ft.replace(R.id.docframe_container,add);
            ft.addToBackStack("Facilitydet");
            ft.commit();

        } else if (id == R.id.nav_logout) {

            AlertDialog.Builder funga=new AlertDialog.Builder(Doctor.this);
            funga.setMessage("Do you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            session.logoutUser();
                            finish();
                            }

                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert= funga.create();
            funga.setTitle("Close");
            alert.show();

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private void initToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome "+Username);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
