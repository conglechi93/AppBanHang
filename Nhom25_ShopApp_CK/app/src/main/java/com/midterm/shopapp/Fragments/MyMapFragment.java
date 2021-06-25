package com.midterm.shopapp.Fragments;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap googleMap;

    public MyMapFragment()  {
        getMapAsync(this);

    }

    @Override
    public void onMapReady(final GoogleMap gmap) {
        this.googleMap = gmap;

        LatLng bk = new LatLng( 16.0738064,108.1477255);
        this.googleMap.addMarker(new MarkerOptions().position(bk).title("BachKhoa IT Mart"));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(bk));
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bk,15));



    }

}