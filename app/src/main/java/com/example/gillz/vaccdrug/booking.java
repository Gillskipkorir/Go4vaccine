package com.example.gillz.vaccdrug;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.Env;

public class booking extends AppCompatActivity {


    EditText paybill;
    Button pay;
    Daraja daraja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        paybill=findViewById(R.id.paybill);
        pay=findViewById(R.id.btnpay);


        //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
        daraja = Daraja.with("oHHh8Ua6iyi4sVduay0saOjWkHebAJmY", "VbJsaZDVT9bA5EiW", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(booking.this.getClass().getSimpleName(), accessToken.getAccess_token());
                Toast.makeText(booking.this, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(booking.this.getClass().getSimpleName(), error);
            }
        });



        //TODO :: THIS IS A SIMPLE WAY TO DO ALL THINGS AT ONCE!!! DON'T DO THIS :)
        /**
         * Using Lambda here -> Added Java 8 support :)
         */

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //Get Phone Number from User Input
                    String phoneNumber=paybill.getText().toString().trim();

                    if (TextUtils.isEmpty(phoneNumber)) {
                        paybill.setError("Please enter Paybill");
                        return;
                    }

                    //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
                    LNMExpress lnmExpress = new LNMExpress(
                            "174379",
                            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                            "10",
                            "254792095190",
                            "174379",
                            phoneNumber,
                            "http://meal.shulemall.com/api",
                            "001ABC",
                            "VACCINE"
                    );

                    //This is the
                    daraja.requestMPESAExpress(lnmExpress,
                            new DarajaListener<LNMResult>() {
                                @Override
                                public void onResult(@NonNull LNMResult lnmResult) {
                                    Log.i(booking.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                                }

                                @Override
                                public void onError(String error) {
                                    Log.i(booking.this.getClass().getSimpleName(), error);
                                }
                            }
                    );

                }

        });
    }
}
