package com.example.gillz.vaccdrug;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.Toast;

public class Homeactivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, vaccdrug.OnFragmentInteractionListener,drugs.OnFragmentInteractionListener,facilities.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_homeactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homeactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {

            Intent intent = new Intent(Homeactivity.this,Doctor.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_about) {
            return true;
        }
        else if (id == R.id.admin) {

            Intent intent = new Intent(Homeactivity.this,Admin.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_vaccine) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            vaccdrug homeFragment = new vaccdrug();
            ft.replace(R.id.frame_container,homeFragment);
            ft.addToBackStack("Vaccine");
            ft.commit();

        }
       /* else if (id == R.id.nav_vaccinedetails) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            drugs homeFragment = new drugs();
            ft.replace(R.id.frame_container,homeFragment);
            ft.addToBackStack("vaccinedetails");
            ft.commit();


        }*/
        else if (id==R.id.nav_facilitydetails)
        {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            facilities homeFragment = new facilities();
            ft.replace(R.id.frame_container,homeFragment);
            ft.addToBackStack("facilities");
            ft.commit();

        }
        else if (id == R.id.nav_llogin) {

            Intent intent = new Intent(Homeactivity.this,Patient_account.class);
            startActivity(intent);

        }

       else if (id == R.id.nav_facilities) {

            Intent intent = new Intent(Homeactivity.this,MapboxF.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_update) {
            Intent intent = new Intent(Homeactivity.this,Doctor.class);
            startActivity(intent);
            finish();

        }
         /*else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
