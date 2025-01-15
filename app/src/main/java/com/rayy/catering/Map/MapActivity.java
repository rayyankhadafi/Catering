package com.rayy.catering.Map;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.rayy.catering.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Mendapatkan SupportMapFragment dan memberi tahu bahwa kita ingin mendapatkan peta
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Lokasi pertama
        LatLng location1 = new LatLng(-7.8307314479124255, 110.37278302089022); // Bantul, Yogyakarta
        mMap.addMarker(new MarkerOptions()
                .position(location1)
                .title("Amm Aqiqah Jogja"));

        // Lokasi kedua
        LatLng location2 = new LatLng(-7.8291537750926326, 110.40248676670812);
        mMap.addMarker(new MarkerOptions()
                .position(location2)
                .title("Catering & Bakery Ibu Supardi (CIS)"));

        // Lokasi ketiga
        LatLng location3 = new LatLng(-7.8287440737670035, 110.40504990118043);
        mMap.addMarker(new MarkerOptions()
                .position(location3)
                .title("Sadasa Catering & Service"));

        // Lokasi keempat
        LatLng location4 = new LatLng(-7.820843025221007, 110.3822066027831);
        mMap.addMarker(new MarkerOptions()
                .position(location4)
                .title("Al Buruuj Catering"));

        // Pindahkan kamera ke lokasi pertama dengan zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 15));  // Zoom lebih besar agar seluruh lokasi terlihat
    }
}
