package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

public class Contacts_Activity extends Activity {

    private String login;
    String[][] tabContact;
    HashMap<String, String> element;
    List<HashMap<String, String>> liste;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Intent intent =getIntent();
        login = intent.getStringExtra("Login");

        Button btnParam = (Button) findViewById(R.id.btnParam);
        btnParam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent param_Activity = new Intent(getApplicationContext(),Param_Activity.class);
                param_Activity.putExtra("Login",login);
                startActivity(param_Activity);
            }
        });

        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent map_Activity = new Intent(getApplicationContext(),Map_Activity.class);
                map_Activity.putExtra("Login",login);
                startActivity(map_Activity);
            }
        });
    }

    public class ThreadContactActivity extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params){
            // http://pierredavy.com/login.php?login=davypier&password=if26
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("contacts.php").appendQueryParameter("login", params[0]);
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
                JSONArray conv = contacts.getJSONArray("contacts");
                tabContact = new String[conv.length()][3];
                for (int i = 0; i < conv.length(); i ++){

                    tabContact[i][0] = conv.getJSONObject(i).getJSONObject("contact").getString("login").toString();
                    tabContact[i][1] = conv.getJSONObject(i).getJSONObject("contact").getString("email").toString();
                    tabContact[i][2] = conv.getJSONObject(i).getString("id").toString();
                }
                for(int i = 0 ; i < tabContact.length ; i++) {
                    element = new HashMap<String, String>();
                    element.put("text1", tabContact[i][0]);
                    element.put("text2", tabContact[i][1]);
                    liste.add(element);
                }
            }
            catch (Exception e){
                Log.e("json ", e.toString(), e);
            }
            return content;
        }
    }
}
