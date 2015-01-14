package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CreateContact_Activity extends Activity implements LocationListener {

    private LocationManager lm;

    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_);

        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

        final EditText login = (EditText) findViewById(R.id.T_Login);
        final EditText password = (EditText) findViewById(R.id.T_Pwd);
        final EditText passwordBis = (EditText) findViewById(R.id.T_PwdBis);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        final String date = year + "-" + month + "-" + day;

        Button Bcreate = (Button) findViewById(R.id.Bcreate_send);
        Bcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("login : ", login.getText().toString());
                Log.d("Password : ", password.getText().toString());
                Log.d("Password Bis : ", passwordBis.getText().toString());
                if ((login.getText().toString().equals("") || password.getText().toString().equals("") || passwordBis.getText().toString().equals("")) ||
                        !password.getText().toString().equals(passwordBis.getText().toString()) ) {
                    if(login.getText().toString().equals("") && password.getText().toString().equals("") && passwordBis.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),
                                "Veuillez compléter les champs Login et Password",
                                Toast.LENGTH_SHORT).show();
                    }else if(login.getText().toString().equals("") && !password.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),
                                "Veuillez compléter le champ Login",
                                Toast.LENGTH_SHORT).show();
                    }else if((!login.getText().toString().equals("") && password.getText().toString().equals("")) ||
                            (!login.getText().toString().equals("") && passwordBis.getText().toString().equals(""))){
                        Toast.makeText(getApplicationContext(),
                                "Veuillez compléter les champs Password",
                                Toast.LENGTH_SHORT).show();
                    }else if(!password.getText().toString().equals(passwordBis.getText().toString())){
                        Toast.makeText(getApplicationContext(),
                                "Les 2 Password doivent être identiques!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    threadCreate async = new threadCreate();
                    async.execute(login.getText().toString(), password.getText().toString(), "1", date,
                            String.valueOf(latitude), String.valueOf(longitude), String.valueOf(altitude), String.valueOf(accuracy));
                }
            }
        });

    }

    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        accuracy = location.getAccuracy();

       /* String msg = "New location : Latitude = " + latitude + ", Longitude = " + longitude + ", Altitude = " + altitude + ", Accuracy = " + accuracy;
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();*/
    }

    public void onProviderDisabled(String provider) {
        String msg = "Provider disabled : " + provider;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onProviderEnabled(String provider) {
        String msg = "Provider enabled : " + provider;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        String newStatus = "";
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                newStatus = "OUT_OF_SERVICE";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                newStatus = "TEMPORARILY_UNAVAILABLE";
                break;
            case LocationProvider.AVAILABLE:
                newStatus = "AVAILABLE";
                break;
        }

        String msg = "Provider disabled : " + provider + ", New Status" + newStatus;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Thread pour la requete vers la base de données.
    public class threadCreate extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            Calendar c = Calendar.getInstance();

            int saltInt = (int)(Math.random()*100);
            String saltDay = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.FRANCE);
            String salt = saltDay+""+saltInt;


            String passwordToEncrypt = params[1]+salt;

            //cryptage du mdp en sha256
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (md != null) {
                md.update(passwordToEncrypt.getBytes());
            }
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            final String passwordEncrypted = sb.toString();
            // http://www.pierredavy.com/addnew.php
           /*   http://pierredavy.com/addnew.php?login=login&password=mdp&token=1&update=2014/12/02&coordonnees=12:23    */
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("addnew.php").appendQueryParameter("login", params[0]).appendQueryParameter("password", passwordEncrypted).appendQueryParameter("token", params[2])
                    .appendQueryParameter("update", params[3]).appendQueryParameter("latitude", params[4]).appendQueryParameter("longitude", params[5]).appendQueryParameter("altitude", params[6])
                    .appendQueryParameter("precise", params[7]).appendQueryParameter("salt", salt);
            String url = uri.build().toString();
            Log.d("url: ", url);

            String result = null;
            try {
                HttpClient HTTPCLlient = new DefaultHttpClient();
                HttpResponse HTTPResponse = HTTPCLlient.execute(new HttpGet(url));
                result = EntityUtils.toString(HTTPResponse.getEntity(), "utf8");
            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }

            Log.d("result", result);

            if (result.equals("false")) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(CreateContact_Activity.this, "login déjà exisant, veuillez en choisir un autre !", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(CreateContact_Activity.this, "Compte Créé !", Toast.LENGTH_LONG).show();
                    }
                });
            }

            Intent backtoStart = new Intent(getApplicationContext(), Login.class);
            finish();
            startActivity(backtoStart);

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
