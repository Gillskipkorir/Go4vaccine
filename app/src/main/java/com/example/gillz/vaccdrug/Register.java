package com.example.gillz.vaccdrug;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    TextView textViewlogin;
    EditText etfname,etlname,etidno,etphoneno,tipass,ticpass;
    TextInputEditText textInputEditText;
    Button btnregister;
    AlertDialog.Builder builder;
    String Amessage;
    String fname,lname,Phone,idno,pass,cpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textViewlogin=findViewById(R.id.textviewlogin);
        etfname=findViewById(R.id.dfname);
        etlname=findViewById(R.id.dlnmae);
        etphoneno=findViewById(R.id.etphone);
        etidno=findViewById(R.id.dno);
        tipass=findViewById(R.id.pass);
        ticpass=findViewById(R.id.conpass);
        btnregister=findViewById(R.id.btnadddoc);



        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname = etfname.getText().toString().trim();
                lname = etlname.getText().toString().trim();
                Phone=etphoneno.getText().toString().trim();
                idno= etidno.getText().toString().trim();
                pass = tipass.getText().toString().trim();
                cpass = ticpass.getText().toString().trim();

                if (TextUtils.isEmpty(Phone)&&TextUtils.isEmpty(fname)&&TextUtils.isEmpty(lname)&& TextUtils.isEmpty(idno)&&TextUtils.isEmpty(pass)&& TextUtils.isEmpty(cpass)) {

                    /*etfname.setBackgroundColor(Color.parseColor("#eda49c"));
                    etlname.setBackgroundColor(Color.parseColor("#eda49c"));
                    etidno.setBackgroundColor(Color.parseColor("#eda49c"));
                    etphoneno.setBackgroundColor(Color.parseColor("#eda49c"));
                    tipass.setBackgroundColor(Color.parseColor("#eda49c"));
                    ticpass.setBackgroundColor(Color.parseColor("#eda49c"));*/
                    ShowToast("Please Fill All Fields");

                    etfname.setError("First name Required");
                    etlname.setError("First name Required");
                    etphoneno.setError("Phone Number is Required");
                    etidno.setError("Idno is Required");
                    tipass.setError("Password Required");
                    ticpass.setError("Confirm Password is Required");
                    return;

                }

                 else if (TextUtils.isEmpty(fname))
                {
                    etfname.setError("Please Enter First name");
                }

                else if (TextUtils.isDigitsOnly(fname))
                {
                    etfname.setError("Your First Name Is Incorrect");
                }
                else if (TextUtils.isEmpty(lname))
                {
                    etlname.setError("Please Enter Last name");
                }
                else if (TextUtils.isDigitsOnly(lname))
                {
                    etlname.setError("Your Last Name Is Incorrect");
                }
                else if (Phone.length()<10||Phone.length()>10)
                {
                   etphoneno.setError("Your Phone Number is incorrect");
                }
                else if (!TextUtils.isDigitsOnly(idno))
                {
                    etidno.setError("Your Id number Name Is Incorrect");
                }
                 else if (idno.length()<7||idno.length()>8)
                 {
                     etidno.setError("You have entered a wrong Id number format");
                 }
                 else if (pass.length()<4)
                 {
                     tipass.setError("Your Password is too Short");
                 }
                 else if (!pass.equals(cpass))
                {
                   ShowToast("Your Passords doesnt Match!, Please try Again");
                }

                 else {

                     RegisterBTask registerBTask = new RegisterBTask(Register.this);
                     registerBTask.execute(fname,lname,Phone,idno,pass);


                 }


            }

        });


        textViewlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Register.this,Plogin.class);
                startActivity(intent);
                finish();

            }
        });


    }
    public void Adialog()

    {
        builder=new AlertDialog.Builder(Register.this);
        builder.setTitle("Registration Failed....");
        builder.setMessage(Amessage);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private void ShowToast(String  msg) {
        Toast.makeText(Register.this,msg, Toast.LENGTH_LONG).show();

    }


}
