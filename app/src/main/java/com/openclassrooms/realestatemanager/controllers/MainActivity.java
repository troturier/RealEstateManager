package com.openclassrooms.realestatemanager.controllers;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.RealEstateAdapter;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.viewmodels.BienImmobilierViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RealEstateAdapter.Listener {

    // FOR DESIGN
    @BindView(R.id.main_recycler_view) RecyclerView recyclerView;

    // 1 - FOR DATA
    private BienImmobilierViewModel bienImmobilierViewModel;
    private List<BienImmobilierComplete> bienImmobilierList;
    private RealEstateAdapter adapter;
    private static int USER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 8 - Configure RecyclerView & ViewModel
        this.configureRecyclerView();
        this.configureViewModel();

        // 9 - Get current user & items from Database
        this.getCurrentUtilisateur(USER_ID);
        this.getBienImmobiliers();

    }

    // -------------------
    // DATA
    // -------------------

    // 2 - Configuring ViewModel

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.bienImmobilierViewModel = ViewModelProviders.of(this, mViewModelFactory).get(BienImmobilierViewModel.class);
        this.bienImmobilierViewModel.init(USER_ID);
    }

    // 3 - Get Current Utilisateur
    private void getCurrentUtilisateur(int utilisateurId){
        this.bienImmobilierViewModel.getUtilisateur(utilisateurId);
        // this.bienImmobilierViewModel.getUtilisateur(utilisateurId).observe(this, this::updateHeader);
    }

    // 3 - Get all BienImmobiliers
    private void getBienImmobiliers(){
        this.bienImmobilierViewModel.ggetBienImmobiliersComplete().observe(this, this::updateBienImmobiliersList);
    }

    // 3 - Create a new BienImmobilier
    /**private void createBienImmobilier(){
        BienImmobilier bienImmobilier = new BienImmobilier(//FORM FIELDS DATA);
        this.bienImmobilierViewModel.createBienImmobilier(bienImmobilier);
    }*/

    // 3 - Delete an bienImmobilier
    private void deleteBienImmobilier(BienImmobilier bienImmobilier){
        this.bienImmobilierViewModel.deleteBienImmobilier(bienImmobilier);
    }

    // 3 - Update an bienImmobilier
    private void updateBienImmobilier(BienImmobilier bienImmobilier){
        this.bienImmobilierViewModel.updateBienImmobilier(bienImmobilier);
    }

    // -------------------
    // UI
    // -------------------

    // 4 - Configure RecyclerView
    private void configureRecyclerView(){
        this.adapter = new RealEstateAdapter(this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // 5 - Update header
    /**private void updateHeader(Utilisateur utilisateur){
        this.profileText.setText(utilisateur.getPrenom());
    }*/

    // 6 - Update the list of items
    private void updateBienImmobiliersList(List<BienImmobilierComplete> bienImmobilierList){
        this.bienImmobilierList = bienImmobilierList;
        updateAdapter();
    }

    private void updateAdapter(){
        this.adapter.updateData(bienImmobilierList);
    }

    @Override
    public void onClickDeleteButton(int position) {
        // ---- //
    }
}
