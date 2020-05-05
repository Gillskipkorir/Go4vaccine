package com.example.gillz.vaccdrug;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    AlertDialog.Builder builder;
    private TextView mEmailView;
    private EditText mPasswordView;
    EditText idno,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Toolbar toolbar =  findViewById(R.id.toolbar);
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




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                builder=new AlertDialog.Builder(LoginPage.this);
                builder.setTitle("About....");
                builder.setMessage("Please Enter your credentials Provided by the Admin during Registration to Login....");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }


                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });
    }
    private void login(){
        if (TextUtils.isEmpty(idno.getText().toString().trim()))
        {
            idno.setError("Your User Id cant be Empty");
        }
        else if (TextUtils.isEmpty(password.getText().toString().trim()))
        {
            password.setError("Password Field Cant be Empty");

        }
        else {
            LoginPageBtask loginPageBtask = new LoginPageBtask(LoginPage.this);
            loginPageBtask.execute(idno.getText().toString(), password.getText().toString());

        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

                        Intent intent = new Intent(LoginPage.this,Homeactivity.class);
                        startActivity(intent);
                        finish();
        return super.onKeyDown(keyCode, event);
    }

}
