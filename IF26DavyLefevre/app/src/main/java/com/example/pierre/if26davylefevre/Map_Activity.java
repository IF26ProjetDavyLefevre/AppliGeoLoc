package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class Map_Activity extends Activity implements LocationListener {

    private GoogleMap map;
    private LocationManager lm;
    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;
    private Marker myPosition;
    private LatLng me;
    private String strLogin;
    private Location location;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        strLogin = intent.getStringExtra("Login");
        Button btnContacts = (Button) findViewById(R.id.btnContacts);
        btnContacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent contacts_Activity = new Intent(getApplicationContext(),Contacts_Activity.class);
                contacts_Activity.putExtra("Login",strLogin);
                startActivity(contacts_Activity);
            }
        });

        Button btnParam = (Button) findViewById(R.id.btnParam);
        btnParam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent param_Activity = new Intent(getApplicationContext(),Param_Activity.class);
                param_Activity.putExtra("Login",strLogin);
                startActivity(param_Activity);
            }
        });

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

        location = new Location("MySelf");
        // on thread la récupération des informations de l'utilisateur
        ThreadMapActivity DOC = new ThreadMapActivity();
        DOC.execute(strLogin);
        String msg = "Latitude ="+latitude+", Longitude = "+longitude;
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        me = new LatLng(latitude,longitude);
        myPosition = map.addMarker(new MarkerOptions().position(me).title("MySelf"));
        if (map!=null && latitude != 0 && longitude != 0) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 10));
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 1500, null);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        accuracy = location.getAccuracy();
        me = new LatLng(latitude,longitude);
        myPosition.setPosition(me);
        //String msg = "Latitude ="+latitude+", Longitude = "+longitude;
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //ici il va falloir que l'on se mette d'accord sur les mouvements de caméra, est ce que ça suit les mouvements de l'utilisateur ou non
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public class ThreadMapActivity extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            // http://pierredavy.com/login.php?login=davypier&password=if26
            //Il faudra modifier le appendPath pour qu'il aille taper sur le bon fichier php et penser au fait qu'il faudra aussi
            // aller chercher la liste des contacts de l'utilisateur en question et récupérer leurs coordonnées
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("login.php").appendQueryParameter("login", params[0]);
            String url = uri.build().toString();
            String result = null;
            try {
                HttpClient HTTPCLlient = new DefaultHttpClient();
                HttpResponse HTTPResponse = HTTPCLlient.execute(new HttpGet(url));
                result = EntityUtils.toString(HTTPResponse.getEntity(), "utf8");
            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }
            //Ici on vérifie que les informations envoyées sont celles du bon contact
            Log.d("Result   : ",result);

            //on va mettre le contenu dans un JSONObject, pas sûr que ça marche mais on peut essayer
            // et on récupère les valeurs voulues, pour l'instant ça ne marche pas vu que la page php n'existe pas
            try {
                JSONObject msg = new JSONObject(result);
                latitude = Double.parseDouble(msg.getString("latitude").toString());
                longitude = Double.parseDouble(msg.getString("longitude").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

    }
}