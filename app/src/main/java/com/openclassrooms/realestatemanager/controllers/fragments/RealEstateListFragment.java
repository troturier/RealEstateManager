package com.openclassrooms.realestatemanager.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.RealEstateAdapter;
import com.openclassrooms.realestatemanager.controllers.activities.DetailActivity;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RealEstateListFragment extends Fragment implements RealEstateAdapter.Listener {

    // FOR DESIGN
    @BindView(R.id.main_recycler_view) RecyclerView recyclerView;
    private RealEstateAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_estate_list, container, false);
        ButterKnife.bind(this, view);
        adapter = new RealEstateAdapter(this);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.configureOnClickRecyclerView();
        return view;
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.main_recycler_view_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent i = new Intent(getActivity(), DetailActivity.class);
                        i.putExtra("bienImmobilier", adapter.getItem(position));
                        startActivity(i);
                    }
                });
    }


        @Override
    public void onClickDeleteButton(int position) {
        // ---- //
    }

    public void updateAdapter(List<BienImmobilierComplete> bienImmobilierList){
        this.adapter.updateData(bienImmobilierList);
    }
}
