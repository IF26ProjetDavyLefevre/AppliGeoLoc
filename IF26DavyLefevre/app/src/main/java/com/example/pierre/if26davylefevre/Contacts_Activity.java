package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Contacts_Activity extends Activity {

    private String login;
    private String token;
    String[][] tabContact;
    HashMap<String, String> element;
    List<HashMap<String, String>> liste;
    private ListAdapter adapter;
    private ListView listView;
    Context myContext ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        myContext= getApplicationContext();

        Intent intent =getIntent();
        login = intent.getStringExtra("Login");
        token = intent.getStringExtra("token");

        //a supprimer
        token ="123";

        listView = (ListView) findViewById(R.id.lVContact);
        liste = new ArrayList<HashMap<String, String>>();
        ThreadContactActivity contactTask = new ThreadContactActivity();
        contactTask.execute(login, token);

        try {
            synchronized (this) {
                //on attend 3 secondes que la tâche asynchrone finisse son travail de récupération des noms
                wait(3000);
            }
        } catch (InterruptedException ex) {
        }

        adapter = new SimpleAdapter(myContext, liste, android.R.layout.simple_list_item_2,
                new String[] {"text1", "text2"}, new int[] {android.R.id.text1, android.R.id.text2 });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3)
            {
                String loginContact = tabContact[position][2];
                Intent map_Activity = new Intent(getApplicationContext(),Map_Activity.class);
                map_Activity.putExtra("Login",login);
                map_Activity.putExtra("token",token);
                map_Activity.putExtra("LoginContact",loginContact);
                startActivity(map_Activity);
            }
        });

        // Go to activité paramètres
        Button btnParam = (Button) findViewById(R.id.btnParam);
        btnParam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent param_Activity = new Intent(getApplicationContext(),Param_Activity.class);
                param_Activity.putExtra("Login",login);
                startActivity(param_Activity);
            }
        });

        // Go to activité map
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent map_Activity = new Intent(getApplicationContext(),Map_Activity.class);
                map_Activity.putExtra("Login",login);
                startActivity(map_Activity);
            }
        });
    }

    //thread  pour l'envoi de la requete
    public class ThreadContactActivity extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params){
            // http://pierredavy.com/login.php?login=davypier&password=if26
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("getrelations.php").appendQueryParameter("login", params[0]).appendQueryParameter("token", params[1]);
            String url = uri.build().toString();
            String content = null;
            try {
                HttpClient clientHTTP = new DefaultHttpClient();
                HttpResponse responseHTTP = clientHTTP.execute(new HttpGet(url));
                content = EntityUtils.toString(responseHTTP.getEntity(), "utf8");
            }
            catch (Exception e){
                Log.e("httpGet ", e.toString(), e);
            }
            try {
                JSONObject contacts = new JSONObject(content);
                JSONArray conv = contacts.getJSONArray("user");
                tabContact = new String[conv.length()][3];
                for (int i = 0; i < conv.length(); i ++){

                    tabContact[i][0] = conv.getJSONObject(i).getString("login").toString();
                    tabContact[i][1] = conv.getJSONObject(i).getString("latitude").toString();
                    tabContact[i][2] = conv.getJSONObject(i).getString("longitude").toString();
                }
                for(int i = 0 ; i < tabContact.length ; i++) {
                    element = new HashMap<String, String>();
                    element.put("text1", tabContact[i][0]);
                    element.put("text2", tabContact[i][1]);
                    liste.add(element);
                }
                Log.d("liste:", liste.toString());
            }

            catch (Exception e){
                Log.e("json ", e.toString(), e);
            }
            return content;
        }
    }
}
