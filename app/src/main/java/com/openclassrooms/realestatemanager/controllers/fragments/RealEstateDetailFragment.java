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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RealEstateDetailFragment extends Fragment implements PhotoAdapter.Listener{

    // FOR DESIGN
    @BindView(R.id.detail_recycler_view) RecyclerView recyclerView;

    private View view;

    private BienImmobilierComplete bienImmobilierComplete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        this.bienImmobilierComplete = (BienImmobilierComplete) getArguments().getSerializable("bienImmobilier");
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
        List<PointInteret> pointInteretList = bienImmobilierComplete.getPointInterets();
        String poi = "";
        if(pointInteretList.size()>0){
            for (int i = 0; i<pointInteretList.size(); i++){
                if(i > 0) poi = poi + "\n";
                poi = String.format("%s- %s", poi, pointInteretList.get(i).getLibelle());
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
        PhotoAdapter adapter = new PhotoAdapter(this);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        List<Photo> photos = bienImmobilierComplete.getPhotos();
        adapter.updateData(photos);
    }

    public static RealEstateDetailFragment newInstance(BienImmobilierComplete bienImmobilierComplete){
        RealEstateDetailFragment realEstateDetailFragment = new RealEstateDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("bienImmobilier", bienImmobilierComplete);
        realEstateDetailFragment.setArguments(args);
        return realEstateDetailFragment;
    }

    @Override
    public void onClickDeleteButton(int position) {
        // ---- //
    }
}
