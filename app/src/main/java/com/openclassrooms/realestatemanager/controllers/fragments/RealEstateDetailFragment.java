package com.openclassrooms.realestatemanager.controllers.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PhotoAdapter;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.PointInteret;
import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("unchecked")
public class RealEstateDetailFragment extends Fragment implements PhotoAdapter.Listener{

    // FOR DESIGN
    @BindView(R.id.detail_recycler_view) RecyclerView recyclerView;

    private View view;

    public BienImmobilierComplete bienImmobilierComplete;
    private List<PointInteret> pointInteretList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        this.bienImmobilierComplete = (BienImmobilierComplete) getArguments().getSerializable("bienImmobilier");
        this.pointInteretList = (List<PointInteret>) getArguments().getSerializable("poi");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_real_estate_detail, container, false);
        configureRecyclerView();

        // DESCRIPTION
        configureDescription();

        // ROOMS
        configureRooms();

        // LOCATION
        configureLocation();

        // POINTS OF INTEREST
        configurePOI();

        return view;
    }

    // -------------------
    // UI UPDATE
    // -------------------

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


        tvLocation.setText(String.format("%s%s%s%s%s", rue, complement, ville, cp, pays));
    }

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

    private void configureRooms(){
        TextView tvRooms = view.findViewById(R.id.tvRooms);
        tvRooms.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getPieces()));

        TextView tvBathrooms = view.findViewById(R.id.tvBathrooms);
        tvBathrooms.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getSdb()));

        TextView tvBedrooms = view.findViewById(R.id.tvBedrooms);
        tvBedrooms.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getChambres()));
    }

    // -------------------
    // UI SETUP
    // -------------------

    private void configureRecyclerView(){
        ButterKnife.bind(this, view);
        PhotoAdapter adapter = new PhotoAdapter(this, false, getActivity());
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        List<Photo> photos = bienImmobilierComplete.getPhotos();
        adapter.updateData(photos,bienImmobilierComplete);
    }

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
}
