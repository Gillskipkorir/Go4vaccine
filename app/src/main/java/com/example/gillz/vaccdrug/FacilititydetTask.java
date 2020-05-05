package com.example.gillz.vaccdrug;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class FacilititydetTask extends AsyncTask<String,Void,String> {
Context ctx;
ProgressDialog progressDialog;
Activity activity;
AlertDialog.Builder builder;
String upload_url="http://192.168.43.145/facilitydet.php";

String choosefacility="choosefacility";
String phone1="phone1";
String email="email";
String adress="adress";
String street ="street";
String town="town";
String openfrom="openfrom";
String opento="opento";
String paybill="paybill";

public FacilititydetTask(Context ctx){
    this.ctx=ctx;
    activity=(Activity)ctx;
}
@Override
protected String doInBackground(String... params) {
    String field1=params[0];
    String field2=params[1];
    String field3=params[2];
    String field4=params[3];
    String field5=params[4];
    String field6=params[5];
    String field7=params[6];
    String field8=params[7];
    String field9=params[8];

    HashMap<String, String> data1 = new HashMap<>();
    data1.put(choosefacility,field1);
    data1.put(phone1,field2);
    data1.put(email,field3);
    data1.put(adress,field4);
    data1.put(street,field5);
    data1.put(town,field6);
    data1.put(openfrom,field7);
    data1.put(opento,field8);
    data1.put(paybill,field9);

    try{
        URL url=new URL(upload_url);
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setConnectTimeout(1500);
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
            Thread.sleep(5000);
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
    progressDialog.setMessage("Submitting your data....");
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
        builder.setTitle("Something went Wrong...");
        builder.setMessage("Data not Submitted...\nCheck Your internet Connection.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        Toast.makeText(ctx," Failed",Toast.LENGTH_LONG).show();

    }
    if (json != null) {

        //Toast.makeText(ctx,json,Toast.LENGTH_LONG).show();

        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            JSONObject obj = jsonArray.getJSONObject(0);
            String code = obj.getString("code");
            String message = obj.getString("message");

            if (code.equals("upload_true")) {
                showDialog(" Success!!", message, code);

            } else if (code.equals("upload_false")) {
                showDialog("Error! ", message, code);

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
