package com.example.gillz.vaccdrug;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Plogin extends AppCompatActivity {
    TextView textViewreg;
    Button btnlogin;
    EditText idno,password;
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plogin);

        idno= findViewById(R.id.doctorid);
        password= findViewById(R.id.patientpassword);

        btnlogin=findViewById(R.id.btnpatientlogin);
        textViewreg=findViewById(R.id.textViewreg);
        textViewreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Plogin.this,Register.class);
                startActivity(intent);


            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }

        });
    }
    private void login(){
        if (TextUtils.isEmpty(idno.getText().toString().trim()))
        {
            idno.setError("Your idNo cant be Empty");
        }
        else if (TextUtils.isEmpty(password.getText().toString().trim()))
        {
            password.setError("Please Provide your Password");

        }
        else {
            PLoginTask loginTask = new PLoginTask(Plogin.this);
            loginTask.execute(idno.getText().toString(), password.getText().toString());

        }
    }
}
