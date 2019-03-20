package com.openclassrooms.realestatemanager.controllers.activities;

import android.Manifest;
import android.annotation.SuppressLint;
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

import java.util.List;
import java.util.Objects;

import androidx.fragment.app.FragmentActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.openclassrooms.realestatemanager.utils.Utils.getLocationFromAddress;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private String[] perms;
    private FusedLocationProviderClient mFusedLocationClient;
    private List<BienImmobilierComplete> bienImmobilierCompleteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        bienImmobilierCompleteList = (List<BienImmobilierComplete>) getIntent().getSerializableExtra("bienImmobilier");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
        getUserLocation();
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(3)
    public void getUserLocation(){
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
                                        CameraPosition user = CameraPosition.builder().target(new LatLng(location2.getLatitude(), location2.getLongitude())).zoom(16).bearing(0).build();
                                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(user));
                                    }
                                });
                    }
                });
    }

    private void addMarkers(){
        for (int i=0; i<bienImmobilierCompleteList.size();i++){
            LatLng latLng = getLocationFromAddress(this, this.bienImmobilierCompleteList.get(i));
            if(latLng != null) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(Objects.requireNonNull(getLocationFromAddress(this, this.bienImmobilierCompleteList.get(i))));
                markerOptions.title(this.bienImmobilierCompleteList.get(i).getBienImmobilier().getRue());
                mMap.addMarker(markerOptions).showInfoWindow();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
