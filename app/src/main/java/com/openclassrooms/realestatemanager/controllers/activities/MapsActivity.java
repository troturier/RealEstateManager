package com.openclassrooms.realestatemanager.controllers.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.PointInteret;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.openclassrooms.realestatemanager.utils.Utils.getLocationFromAddress;

@SuppressWarnings("unchecked")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private String[] perms;
    private FusedLocationProviderClient mFusedLocationClient;
    private static List<BienImmobilierComplete> bienImmobilierCompleteList;
    private static List<PointInteret> pointInteretList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        bienImmobilierCompleteList = (List<BienImmobilierComplete>) getIntent().getSerializableExtra("bienImmobilier");
        pointInteretList = (List<PointInteret>) getIntent().getSerializableExtra("poi");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
        getUserLocation();
    }

    /**
     * Locate the user position
     */
    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(3)
    private void getUserLocation(){
        if (EasyPermissions.hasPermissions(this, perms)) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            locateUser();
            addMarkers();
        } else {
            EasyPermissions.requestPermissions(this, "This app require access to your device location in order to show you the nearby properties",
                    3, perms);
        }
    }

    /**
     * Move camera and display user's position on the map
     */
    @SuppressLint("MissingPermission")
    private void locateUser() {
        MapsInitializer.initialize(this);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Get last known location. In some rare situations this can be null.
                    if (location != null) {
                        mFusedLocationClient.getLastLocation()
                                .addOnSuccessListener(this, location2 -> {
                                    // Get last known location. In some rare situations this can be null.
                                    if (location2 != null) {
                                        CameraPosition user = CameraPosition.builder().target(new LatLng(location2.getLatitude(), location2.getLongitude())).zoom(14).bearing(0).build();
                                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(user));
                                    }
                                });
                    }
                });
    }

    /**
     * Add all markers corresponding to the retrieved BienImmobilierComplete list
     */
    private void addMarkers(){
        for (int i=0; i<bienImmobilierCompleteList.size();i++){
            LatLng latLng = getLocationFromAddress(this, bienImmobilierCompleteList.get(i));
            if(latLng != null) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(Objects.requireNonNull(getLocationFromAddress(this, bienImmobilierCompleteList.get(i))));
                markerOptions.title(bienImmobilierCompleteList.get(i).getBienImmobilier().getRue());
                markerOptions.snippet(String.valueOf(i));
                mMap.addMarker(markerOptions).showInfoWindow();
            }
        }

        mMap.setOnMarkerClickListener(marker -> {
            Intent intent = new Intent(MapsActivity.this, DetailActivity.class);
            intent.putExtra("bienImmobilier", bienImmobilierCompleteList.get(Integer.parseInt(marker.getSnippet())));
            intent.putExtra("poi", (Serializable) pointInteretList);
            startActivity(intent);
            return true;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
