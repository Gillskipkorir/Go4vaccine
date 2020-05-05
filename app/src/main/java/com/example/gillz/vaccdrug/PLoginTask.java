package com.example.gillz.vaccdrug;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class PLoginTask extends AsyncTask<String,Void,String> {
    Context ctx;
    ProgressDialog progressDialog;
    Activity activity;
    AlertDialog.Builder builder;
    String upload_url="http://192.168.43.145/plogin.php";

//    public static final String upload_key

    String idno="idno";
    String pass="pass";

    // User Session Manager Class
    PatientSessionManager session;
    public PLoginTask(Context ctx){
        this.ctx=ctx;
        activity=(Activity)ctx;
    }
    @Override
    protected String doInBackground(String... params) {

        String field=params[0];
        String field2=params[1];

        HashMap<String, String> data1 = new HashMap<>();

        data1.put(idno,field);
        data1.put(pass,field2);

        try{
            URL url=new URL(upload_url);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(10000);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(getPostDataString(data1));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            String line="";
            while((line=bufferedReader.readLine())!= null){
                stringBuilder.append(line+"\n");

            }
            httpURLConnection.disconnect();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString().trim();


        }catch (MalformedURLException e)
        {
            e.printStackTrace();
        }catch (IOException e){

            e.printStackTrace();
        }


        return null;

    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result= new StringBuilder();
        boolean first=true;
        for (Map.Entry<String,String>entry:params.entrySet()){
            if (first){
                first=false;
            }else{
                result.append("&");

            }
            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }

        return result.toString();

    }


    @Override
    protected void onPreExecute() {
        builder=new AlertDialog.Builder(ctx);
        builder=new AlertDialog.Builder(activity);
        progressDialog=new ProgressDialog(ctx);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("verifying your details....");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
      super.onProgressUpdate(values);


    }

    @Override
    protected void onPostExecute(String json) {
        if (json == null) {

            progressDialog.dismiss();
            builder.setTitle("Error!!! Something went Wrong...");
            builder.setMessage("You Entered Wrong Credentials.\n");
            builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            }).setNegativeButton("Dont Have an Account", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(ctx,Register.class);
                    ctx.startActivity(intent);
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        if (json != null) {
            try {
             //Toast.makeText(ctx,json,Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject obj = jsonArray.getJSONObject(0);
                String code = obj.getString("code");
                String message = obj.getString("message");
                final String idno = obj.getString("idno");


                if (code.equals("login_true")) {
                    showDialog("Login Success", message, code);
                    Toast.makeText(ctx," Successfully",Toast.LENGTH_LONG).show();
                    session = new PatientSessionManager(ctx);
                    session.createUserLoginSession(idno);
                    Intent intent = new Intent(ctx,Patient_account.class);
                    ctx.startActivity(intent);

                } else if (code.equals("login_false")) {
                    showDialog("Login Failed ", message, code);
                    Toast.makeText(ctx," Failed",Toast.LENGTH_LONG).show();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public  void  showDialog(String title,String message,String code){
        builder.setTitle(title);
        if(code.equals("upload_true")||code.equals("upload_false")){
            builder.setMessage(message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }


}
