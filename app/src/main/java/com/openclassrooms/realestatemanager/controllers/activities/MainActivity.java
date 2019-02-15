package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.RealEstateListFragment;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.BienImmobilierViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    // 1 - FOR DATA
    private BienImmobilierViewModel bienImmobilierViewModel;
    private List<BienImmobilierComplete> bienImmobilierList;
    private static int USER_ID = 1;

    public boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        determinePaneLayout();
        if(!Utils.checkPermissionForReadExtertalStorage(this)){
            try {
                Utils.requestPermissionForReadExtertalStorage(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            configure();
        }
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
        this.bienImmobilierViewModel.getBienImmobiliersComplete().observe(this, this::updateBienImmobiliersList);
    }

    // 3 - Create a new BienImmobilier
    /**private void createBienImmobilier(){
     BienImmobilier bienImmobilier = new BienImmobilier(//FORM FIELDS DATA);
     this.bienImmobilierViewModel.createBienImmobilier(bienImmobilier);
     }*/

    // 3 - Delete a bienImmobilier
    private void deleteBienImmobilier(BienImmobilier bienImmobilier){
        this.bienImmobilierViewModel.deleteBienImmobilier(bienImmobilier);
    }

    // 3 - Update a bienImmobilier
    private void updateBienImmobilier(BienImmobilier bienImmobilier){
        this.bienImmobilierViewModel.updateBienImmobilier(bienImmobilier);
    }

    // -------------------
    // UI
    // -------------------

    // 5 - Update header
    /**private void updateHeader(Utilisateur utilisateur){
     this.profileText.setText(utilisateur.getPrenom());
     }*/

    // 6 - Update the list of items
    private void updateBienImmobiliersList(List<BienImmobilierComplete> bienImmobilierList){
        this.bienImmobilierList = bienImmobilierList;
        RealEstateListFragment realEstateListFragment = (RealEstateListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRealEstateList);
        realEstateListFragment.updateAdapter(this.bienImmobilierList);
    }

    private void configure(){
        // 8 - Configure RecyclerView & ViewModel
        this.configureViewModel();
        // 9 - Get BienImmobiliers from Database
        this.getBienImmobiliers();
    }

    private void determinePaneLayout() {
        FrameLayout fragmentDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
        if(fragmentDetail != null){
            isTwoPane = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        configure();
    }
}
