package com.example.gillz.vaccdrug;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Adminlogin extends AppCompatActivity {
    EditText idno,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btnl;
        btnl= findViewById(R.id.button);
        idno= findViewById(R.id.doctorid);
        password= findViewById(R.id.doctorpassword);

        btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }

        });

    }
    private void login(){
        if (TextUtils.isEmpty(idno.getText().toString().trim()))
        {
            idno.setError("Your Admin Id cant be Empty");
        }
        else if (TextUtils.isEmpty(password.getText().toString().trim()))
        {
            password.setError("Password Field Cant be Empty");

        }
        else {
            AdminloginBtask Btask = new AdminloginBtask(Adminlogin.this);
            Btask.execute(idno.getText().toString(), password.getText().toString());

        }
    }

}
