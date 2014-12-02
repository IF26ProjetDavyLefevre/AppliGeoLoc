package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class CreateContact_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_);



        Button Bcreate = (Button) findViewById(R.id.Bcreate_send);


        Bcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                threadCreate DOC = new threadCreate();

            }
        });

    }




    //Thread pour la requete vers la base de données.
    public class threadCreate extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            // http://www.pierredavy.com/addnew.php
           /*   http://train.sandbox.eutech-ssii.com/messenger/login.php?email=%@&password=%@     */
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("addnew.php").appendQueryParameter("email", params[0]).appendQueryParameter("password", params[1]);
            String url = uri.build().toString();
            // String url = "http://train.sandbox.eutech-ssii.com/messenger/login.php?email=test1@test.fr&password=test";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_contact_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
