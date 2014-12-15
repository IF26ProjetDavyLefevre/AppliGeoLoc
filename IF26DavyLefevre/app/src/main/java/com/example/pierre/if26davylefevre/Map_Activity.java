package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class Map_Activity extends Activity implements LocationListener {

    private GoogleMap map;
    private LocationManager lm;
    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;
    private Marker myPosition;
    private LatLng me;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

        Location location = new Location("MySelf");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        accuracy = location.getAccuracy();
        String msg = "Latitude ="+latitude+", Longitude = "+longitude;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
        String msg = "Latitude ="+latitude+", Longitude = "+longitude;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
}