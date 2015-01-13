package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.util.Objects.hash;


public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText login = (EditText) findViewById(R.id.T_log);
        final EditText password = (EditText) findViewById(R.id.T_password);


        Button BLogin = (Button) findViewById(R.id.BLogin);
        BLogin.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          if (isNetworkAvailable(getApplicationContext())) {
                                              if (login.getText().toString().equals("") || password.getText().toString().equals("")) {
                                                  if(login.getText().toString().equals("") && password.getText().toString().equals("")){
                                                      Toast.makeText(getApplicationContext(),
                                                              "Veuillez compléter les champs Login et Password",
                                                              Toast.LENGTH_SHORT).show();
                                                  }else if(login.getText().toString().equals("") && !password.getText().toString().equals("")){
                                                      Toast.makeText(getApplicationContext(),
                                                              "Veuillez compléter le champ Login",
                                                              Toast.LENGTH_SHORT).show();
                                                  }else if(!login.getText().toString().equals("") && password.getText().toString().equals("")){
                                                      Toast.makeText(getApplicationContext(),
                                                              "Veuillez compléter le champ Password",
                                                              Toast.LENGTH_SHORT).show();
                                                  }
                                              }else {
                                                  threadActivity DOC = new threadActivity();
                                                  DOC.execute(login.getText().toString(), password.getText().toString());
                                              }
                                          } else {
                                              Toast.makeText(getApplicationContext(), "Vous n'êtes pas connecté à internet. Veuillez vous connecter.", Toast.LENGTH_LONG).show();
                                          }
                                      }
                                  }

        );

        Button Bcreate = (Button) findViewById(R.id.BCreate);
        Bcreate.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View v) {
                                           if (isNetworkAvailable(getApplicationContext())) {
                                               Intent pageCreateUser = new Intent(getApplicationContext(), CreateContact_Activity.class);
                                               startActivity(pageCreateUser);
                                           } else {
                                               Toast.makeText(getApplicationContext(), "Vous n'êtes pas connecté à internet. Veuillez vous connecter.", Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   }

        );
    }

    //fonction permettant de vérifier si le téléphone est connecté à internet

    public boolean isNetworkAvailable(Context context) {
        boolean value = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            value = true;
        }
        return value;
    }

    //thread qui permet l'accès à la base de données pour le login
    public class threadActivity extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            Uri.Builder uriSalt = new Uri.Builder();
            uriSalt.scheme("http").authority("pierredavy.com").appendPath("login.php").appendQueryParameter("login", params[0]).appendQueryParameter("salt", "");
            String urlSalt = uriSalt.build().toString();
            String resultSalt = null;
            try {
                HttpClient HTTPCLlientSalt = new DefaultHttpClient();
                HttpResponse HTTPResponseSalt = HTTPCLlientSalt.execute(new HttpGet(urlSalt));
                resultSalt = EntityUtils.toString(HTTPResponseSalt.getEntity(), "utf8");
            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }

            String JSONResultSalt[];
            String resultArraySalt[] = resultSalt.split(",");
            JSONResultSalt = resultArraySalt[0].split(":");

            String salt = null;
            for (int j = 0; j < JSONResultSalt.length; j++) {
                Log.d("Jsonresult :", JSONResultSalt[j]);
            }
            if (JSONResultSalt[1].equals("false")) {
                JSONObject userSalt;
                try {
                    userSalt = new JSONObject(resultSalt);
                    salt = userSalt.getJSONObject("user").getString("salt").toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (md != null) {
                md.update((params[1] + salt).getBytes());
            }
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            String passwordEncrypted = sb.toString();

            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("login.php").appendQueryParameter("login", params[0]).appendQueryParameter("password", passwordEncrypted);
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
            Log.d("Result   : ", result);

            String JSONResult[];
            String resultArray[] = result.split(",");
            JSONResult = resultArray[0].split(":");


            for (int j = 0; j < JSONResult.length; j++) {
                Log.d("Jsonresult :", JSONResult[j]);
            }

            //Si les identifiants sont corrects, on lance l'activité 2
            if (JSONResult[1].equals("false")) {
                JSONObject user;
                try {
                    user = new JSONObject(result);
                    Intent mapActivity = new Intent(getApplicationContext(), Map_Activity.class);
                    // token= JSONToken[1].substring(1, JSONToken[1].length() - 2);
                    mapActivity.putExtra("Login", params[0]);
                    mapActivity.putExtra("Token", user.getJSONObject("user").getString("token").toString());
                    finish();
                    startActivity(mapActivity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
