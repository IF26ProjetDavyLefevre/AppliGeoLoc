package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class Login extends Activity {

   // Context myContext= getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button BLogin = (Button) findViewById(R.id.BLogin);
        BLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText login = (EditText) findViewById(R.id.T_log);
                EditText password = (EditText) findViewById(R.id.T_password);
                threadActivity DOC = new threadActivity();
                DOC.execute(login.getText().toString(), password.getText().toString());
            }
        });


        Button Bcreate = (Button) findViewById(R.id.BCreate);
        Bcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pageCreateUser = new Intent(getApplicationContext(),CreateContact_Activity.class);
                startActivity(pageCreateUser);
            }
        });

        Button BGotoMap = (Button) findViewById(R.id.B_GotoMap);
        BGotoMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pageCreateUser = new Intent(getApplicationContext(),Map_Activity.class);
                startActivity(pageCreateUser);
            }
        });

    }

    public class threadActivity extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            // http://pierredavy.com/login.php?login=davypier&password=if26
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("login.php").appendQueryParameter("login", params[0]).appendQueryParameter("password", params[1]);
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

            String JSONResult[] , JSONToken[];
            String resultArray[] = result.split(",");
                JSONResult = resultArray[0].split(":");



            for (int j =0;j<JSONResult.length;j++){
                Log.d("Jsonresult :",JSONResult[j]);
            }

            //Si les identifiants sont corrects, on lance l'activité 2
            if (JSONResult[1].equals("false")) {

                Intent mainpage = new Intent(getApplicationContext(),MainPage.class);
               // token= JSONToken[1].substring(1, JSONToken[1].length() - 2);
                //page2.putExtra("Token",token);
                startActivity(mainpage);
            }
            // on affiche que les idéntifiants sont faux sinon
            else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Login.this, "mauvais identifiants", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return result;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
