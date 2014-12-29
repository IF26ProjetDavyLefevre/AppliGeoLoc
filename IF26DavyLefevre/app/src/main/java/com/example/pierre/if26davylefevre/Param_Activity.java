package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Param_Activity extends Activity {

    private String login;
    private CheckBox cb_visible;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);

        Intent intent = getIntent();
        login = intent.getStringExtra("Login");


        //activité qui va chercher l'état de visible
        cb_visible = (CheckBox) findViewById(R.id.cBVisibility);
        threadActivityGetVisible thread = new threadActivityGetVisible();
        thread.execute(String.valueOf(login));
        try {
            synchronized (this) {
                //on attend 3 secondes que la tâche asynchrone finisse son travail de récupération des noms
                wait(2000);
            }
        } catch (InterruptedException ex) {
        }


        Button btnContacts = (Button) findViewById(R.id.btnContacts);
        btnContacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent contacts_Activity = new Intent(getApplicationContext(), Contacts_Activity.class);
                contacts_Activity.putExtra("Login", login);
                startActivity(contacts_Activity);
            }
        });

        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent map_Activity = new Intent(getApplicationContext(), Map_Activity.class);
                map_Activity.putExtra("Login", login);
                startActivity(map_Activity);
            }
        });


        //permet de Set le visible
        cb_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (cb_visible.isChecked()) {
                    Log.d("result :", "checked");

                   threadActivity thread = new threadActivity();
                   thread.execute(String.valueOf(login), "1");

                } else {
                    Log.d("result :", "UNchecked");

                    threadActivity thread = new threadActivity();
                    thread.execute(String.valueOf(login), "0");
                }
            }
        });

    }




    public class threadActivity extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("setVisible.php").appendQueryParameter("login", params[0]).appendQueryParameter("visible", params[1]);
            String url = uri.build().toString();
            String result = null;
            try {
                HttpClient HTTPCLlient = new DefaultHttpClient();
                HttpResponse HTTPResponse = HTTPCLlient.execute(new HttpGet(url));
                result = EntityUtils.toString(HTTPResponse.getEntity(), "utf8");
            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }


            return result;
        }

    }



    //Thread qui permet d'obtenir l'état de visible
    public class threadActivityGetVisible extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("getVisible.php").appendQueryParameter("login", params[0]);
            String url = uri.build().toString();
            String result = null;
            try {
                HttpClient HTTPCLlient = new DefaultHttpClient();
                HttpResponse HTTPResponse = HTTPCLlient.execute(new HttpGet(url));
                result = EntityUtils.toString(HTTPResponse.getEntity(), "utf8");
            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }


            //debug dans la console
            Log.d("Result   : ",result);

            String JSONResult[] ;
            String resultArray[] = result.split(",");
            JSONResult = resultArray[1].split(":");
            String Final[]= JSONResult[2].split("\"");

                Log.d("setvisibleStatus :",Final[1]);


            if (Final[1].equals("1")) {
                cb_visible.setChecked(!cb_visible.isChecked());
            }
            else{
                cb_visible.setChecked(false);
            }


            return result;
        }

    }


}
