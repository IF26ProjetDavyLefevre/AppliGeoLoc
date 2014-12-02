package com.example.pierre.projetif26;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private EditText login = null;
    private EditText pw = null;
    private Button val;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = (EditText) findViewById(R.id.T_login);
        pw = (EditText) findViewById(R.id.T_pwd);
        Button val = (Button) findViewById(R.id.B_login);


        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText login = (EditText) findViewById(R.id.T_login);
                EditText password = (EditText) findViewById(R.id.T_pwd);

                threadActivity DOC = new threadActivity();
                DOC.execute(login.getText().toString(), password.getText().toString());
                //Toast.makeText(getApplicationContext(), "mauvais identifiants", Toast.LENGTH_SHORT).show();


            }
        });

    }


    public class threadActivity extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {




           /*   http://train.sandbox.eutech-ssii.com/messenger/login.php?email=%@&password=%@     */
            //Uri.Builder uri = new Uri.Builder();
            //uri.scheme("http").authority("train.sandbox.eutech-ssii.com").appendPath("messenger").appendPath("login.php").appendQueryParameter("email", params[0]).appendQueryParameter("password", params[1]);
            String url = "http://www.pierredavy.com/addnew.php";
            // String url = "http://train.sandbox.eutech-ssii.com/messenger/login.php?email=test1@test.fr&password=test";
            String result = null;



            try {
                HttpClient clientHTTP = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login", "pierredavy"));
                nameValuePairs.add(new BasicNameValuePair("password", "motdepasse"));
                nameValuePairs.add(new BasicNameValuePair("token", "98765431"));
                nameValuePairs.add(new BasicNameValuePair("last_update", "13/01/1992"));
                nameValuePairs.add(new BasicNameValuePair("coordonnees", "24:42"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = clientHTTP.execute(httppost);

            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }
           // String JSONResult[], JSONToken[];

            // ecrit dans la console en debug
            Log.d(result,"ICI   : "+result);


       //     String contentBuffer[] = result.split(",");
         //   JSONResult = contentBuffer[0].split(":");

/*

            if (JSONResult[1].equals("false")) {
                JSONToken = contentBuffer[1].split(":");
                Intent page2 = new Intent(getApplicationContext(),SecondActivity.class);
                token= JSONToken[1].substring(1, JSONToken[1].length() - 2);
                page2.putExtra("Token",token);
                startActivity(page2);
            }
            else {
                Log.d(result,"LALAALALALAA ");
                //sendMsg("MAUVAIS LOGIN");
                //fait planter l'appli
                //Toast.makeText(getApplicationContext(), "mauvais identifiants", Toast.LENGTH_SHORT).show();

            }*/
            return result;
        }



        //@Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }



    }


}