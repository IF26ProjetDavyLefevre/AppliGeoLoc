package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapView;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import java.util.Calendar;


public class Tab2Fragment extends MapFragment implements LocationListener {

    Context mContext;

    private LocationManager lm;

    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;
    public GoogleMap Googlemap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Location location = new Location("dummyprovider");
        location.setLatitude(48.2989);
        location.setLongitude(4.07806);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        accuracy = location.getAccuracy();
        //View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        String msg = "New location : Latitude = "+latitude+", Longitude = "+longitude+", Altitude = "+altitude+", Accuracy = "+accuracy;
        Log.d("create : ", msg);

        //LatLng troyes= new LatLng(latitude,longitude);
        //this.getMap().setMapType(2);

        GoogleMap m = getMap();
        //m.getMyLocation();


        return super.onCreateView(inflater, container, savedInstanceState);
    }



   public void onMapReady(GoogleMap Gmap) {
       Gmap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));

        String msg = "New location : Latitude = "+latitude+", Longitude = "+longitude+", Altitude = "+altitude+", Accuracy = "+accuracy;
        Log.d("pos : ", msg);
    }

    @Override
    public void onResume() {
       // Log.d("resume : ", "resume");
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        accuracy = location.getAccuracy();

        LatLng troyes= new LatLng(latitude,longitude);
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(troyes,16));


        String msg = "New location : Latitude = "+latitude+", Longitude = "+longitude+", Altitude = "+altitude+", Accuracy = "+accuracy;
        Log.d("msg : ", msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}