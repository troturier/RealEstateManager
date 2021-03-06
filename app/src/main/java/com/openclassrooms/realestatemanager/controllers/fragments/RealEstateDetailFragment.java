package com.openclassrooms.realestatemanager.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PhotoAdapter;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.PointInteret;
import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;
import com.openclassrooms.realestatemanager.utils.InternetCheck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.realestatemanager.utils.Utils.getLocationFromAddress;

@SuppressWarnings("unchecked")
public class RealEstateDetailFragment extends Fragment implements PhotoAdapter.Listener, OnMapReadyCallback {

    // FOR DESIGN
    @BindView(R.id.detail_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.mapview_placeholder) ImageView mapViewPlaceHolder;
    @BindView(R.id.mapView_Placeholder_Tv) TextView mapViewPlaceHolderTv;
    @BindView(R.id.mapView_PH_LL) LinearLayout mapViewPlaceHolderLL;

    private View view;
    private MapView mMapView;

    public BienImmobilierComplete bienImmobilierComplete;
    private List<PointInteret> pointInteretList;
    private boolean internet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        this.bienImmobilierComplete = (BienImmobilierComplete) getArguments().getSerializable("bienImmobilier");
        this.pointInteretList = (List<PointInteret>) getArguments().getSerializable("poi");
        this.internet = false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_real_estate_detail, container, false);
        mMapView = view.findViewById(R.id.map);

        new InternetCheck(internet1 -> {
            if (internet1) {
                try {
                    this.internet = true;
                    // Inflate the layout for this fragment
                    mMapView.onCreate(savedInstanceState);
                    mMapView.onResume();
                    mMapView.getMapAsync(this);
                } catch (Exception e) {
                    Log.e("DETAIL_FRAG", "Inflate exception");
                }
            }
            else {
                Context context = getActivity();
                if (context != null) {
                    mMapView.setVisibility(View.GONE);
                    mapViewPlaceHolderLL.setVisibility(View.VISIBLE);
                    Glide
                            .with(Objects.requireNonNull(getActivity()))
                            .load(R.drawable.ic_no_internet)
                            .into(mapViewPlaceHolder);
                    mapViewPlaceHolderTv.setText(getString(R.string.no_internet));
                }
            }
        });

        updateContent(this.bienImmobilierComplete, this.pointInteretList);

        return view;
    }

    /**
     * Configure and update the UI
     * @param bienImmobilierComplete BienImmobilierComplete retrieved from the intent
     * @param pointInteretList List of points of interest
     */
    public void updateContent(BienImmobilierComplete bienImmobilierComplete, List<PointInteret> pointInteretList){
        this.bienImmobilierComplete = bienImmobilierComplete;
        this.pointInteretList = pointInteretList;

        // MEDIA
        configureRecyclerView();

        // DESCRIPTION
        configureDescription();

        // ROOMS
        configureRooms();

        // LOCATION
        configureLocation();

        // POINTS OF INTEREST
        configurePOI();
    }

    // -------------------
    // UI SETUP AND UPDATE
    // -------------------

    /**
     * Configure Points of Interest part of the UI
     */
    private void configurePOI(){
        View divider = view.findViewById(R.id.divider4);
        TextView tvPoI = view.findViewById(R.id.tvPoI);
        TextView tvPoItitle = view.findViewById(R.id.tvPoItitle);
        ImageView ivPoI = view.findViewById(R.id.ivPoI);

        List<PointInteret> pointInteretBienImmobilierList = new ArrayList<>();
        for (PointInteret poi2 : this.pointInteretList) {
            // Loop arrayList1 items
            boolean found = false;
            for (PointInteretBienImmobilier poi1 : bienImmobilierComplete.getPointInteretBienImmobiliers()) {
                if (poi2.getId() == poi1.getIdPoi()) {
                    found = true;
                }
            }
            if (found) {
                pointInteretBienImmobilierList.add(poi2);
            }
        }

        String poi = "";
        if(pointInteretList.size()>0){
            for (int i = 0; i< pointInteretBienImmobilierList.size(); i++){
                if(i > 0) poi = poi + "\n";
                poi = String.format("%s- %s", poi, pointInteretBienImmobilierList.get(i).getLibelle());
            }
            tvPoI.setText(poi);
        }
        else {
            tvPoI.setVisibility(View.GONE);
            tvPoItitle.setVisibility(View.GONE);
            ivPoI.setVisibility(View.GONE);
            if(divider != null) divider.setVisibility(View.GONE);
        }
    }

    /**
     * Configure Location part of the UI
     */
    private void configureLocation(){
        TextView tvLocation = view.findViewById(R.id.tvLocation);
        String rue = bienImmobilierComplete.getBienImmobilier().getRue() + "\n";
        String complement = "";
        if(bienImmobilierComplete.getBienImmobilier().getComplementRue() != null) {
            complement = bienImmobilierComplete.getBienImmobilier().getComplementRue() + "\n";
        }
        String cp = bienImmobilierComplete.getBienImmobilier().getCp() + "\n";
        String ville = bienImmobilierComplete.getBienImmobilier().getVille() + "\n";
        String pays = bienImmobilierComplete.getBienImmobilier().getPays();

        String address = String.format("%s%s%s%s%s", rue, complement, ville, cp, pays);
        tvLocation.setText(address);
    }

    /**
     * Configure Description part of the UI
     */
    private void configureDescription(){
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        if(bienImmobilierComplete.getBienImmobilier().getDescription() != null)
            tvDescription.setText(bienImmobilierComplete.getBienImmobilier().getDescription());
        else {
            tvDescription.setText(getString(R.string.no_description));
        }

        TextView tvSurface = view.findViewById(R.id.tvSurface);
        tvSurface.setText(String.format("%s sq m", String.valueOf(bienImmobilierComplete.getBienImmobilier().getSurface())));

    }

    /**
     * Configure Rooms part of the UI
     */
    private void configureRooms(){
        TextView tvRooms = view.findViewById(R.id.tvRooms);
        tvRooms.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getPieces()));

        TextView tvBathrooms = view.findViewById(R.id.tvBathrooms);
        tvBathrooms.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getSdb()));

        TextView tvBedrooms = view.findViewById(R.id.tvBedrooms);
        tvBedrooms.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getChambres()));
    }

    /**
     * Configure Photos part of the UI
     */
    private void configureRecyclerView(){
        ButterKnife.bind(this, view);
        PhotoAdapter adapter = new PhotoAdapter(this, false, getActivity(), "edit");
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter.updateData(bienImmobilierComplete);
    }

    // -------------------
    // NEW INSTANCE
    // -------------------

    /**
     * RealEstateDetailFragment new instance method
     * @param bienImmobilierComplete BienImmobilierComplete retrieved from the intents
     * @param pointInteretList List of Points of Interest retrieved from the intents
     * @return RealEstateDetailFragment
     */
    public static RealEstateDetailFragment newInstance(BienImmobilierComplete bienImmobilierComplete, List<PointInteret> pointInteretList){
        RealEstateDetailFragment realEstateDetailFragment = new RealEstateDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("bienImmobilier", bienImmobilierComplete);
        args.putSerializable("poi", (Serializable) pointInteretList);
        realEstateDetailFragment.setArguments(args);
        return realEstateDetailFragment;
    }

    @Override
    public void onClickDeleteButton(int position) {
        // ---- //
    }

    // ------------------ MAP -------------------------

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Context context = getContext();
        if (context != null) {
            MapsInitializer.initialize(context);
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Objects.requireNonNull(getActivity()), R.raw.map_style));
            googleMap.getUiSettings().setCompassEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(false);

            LatLng latLng = getLocationFromAddress(getActivity(), this.bienImmobilierComplete);
            if(latLng != null) {
                CameraPosition cameraPosition = CameraPosition.builder().target(latLng).zoom(17).bearing(0).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(Objects.requireNonNull(getLocationFromAddress(getActivity(), this.bienImmobilierComplete)));
                markerOptions.title(this.bienImmobilierComplete.getBienImmobilier().getRue());
                googleMap.addMarker(markerOptions).showInfoWindow();
            }
            else {
                mMapView.setVisibility(View.GONE);
                mapViewPlaceHolderLL.setVisibility(View.VISIBLE);
                Glide
                        .with(Objects.requireNonNull(getActivity()))
                        .load(R.drawable.ic_location)
                        .into(mapViewPlaceHolder);
                mapViewPlaceHolderTv.setText(getString(R.string.location_not_found));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.internet) {
            mMapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.internet)
            mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        if (this.internet)
            mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.internet)
            mMapView.onResume();
    }

}
