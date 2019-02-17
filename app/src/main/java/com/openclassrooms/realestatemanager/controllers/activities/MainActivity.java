package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.RealEstateDetailFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.RealEstateListFragment;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.repositories.injections.Injection;
import com.openclassrooms.realestatemanager.repositories.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.BienImmobilierViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    // 1 - FOR DATA
    private BienImmobilierViewModel bienImmobilierViewModel;

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
        int USER_ID = 1;
        this.bienImmobilierViewModel.init(USER_ID);
    }

    // --Commented out by Inspection START (17/02/2019 18:47):
    //    // 3 - Get Current Utilisateur
    //    private void getCurrentUtilisateur(int utilisateurId){
    //        this.bienImmobilierViewModel.getUtilisateur(utilisateurId);
    //        // this.bienImmobilierViewModel.getUtilisateur(utilisateurId).observe(this, this::updateHeader);
    //    }
    // --Commented out by Inspection STOP (17/02/2019 18:47)

    // 3 - Get all BienImmobiliers
    private void getBienImmobiliers(){
        this.bienImmobilierViewModel.getBienImmobiliersComplete().observe(this, this::updateBienImmobiliersList);
    }

    // 3 - Create a new BienImmobilier
    // --Commented out by Inspection START (17/02/2019 18:47):
    //    /**private void createBienImmobilier(){
    //     BienImmobilier bienImmobilier = new BienImmobilier(//FORM FIELDS DATA);
    //     this.bienImmobilierViewModel.createBienImmobilier(bienImmobilier);
    // --Commented out by Inspection START (17/02/2019 18:47):
    ////     }*/
    ////
    ////    // 3 - Delete a bienImmobilier
    ////    private void deleteBienImmobilier(BienImmobilier bienImmobilier){
    ////        this.bienImmobilierViewModel.deleteBienImmobilier(bienImmobilier);
    // --Commented out by Inspection STOP (17/02/2019 18:47)
    //    }
    // --Commented out by Inspection STOP (17/02/2019 18:47)

    // --Commented out by Inspection START (17/02/2019 18:47):
    //    // 3 - Update a bienImmobilier
    //    private void updateBienImmobilier(BienImmobilier bienImmobilier){
    //        this.bienImmobilierViewModel.updateBienImmobilier(bienImmobilier);
    //    }
    // --Commented out by Inspection STOP (17/02/2019 18:47)

    // -------------------
    // UI
    // -------------------

    // 5 - Update header
    /**private void updateHeader(Utilisateur utilisateur){
     this.profileText.setText(utilisateur.getPrenom());
     }*/

    // 6 - Update the list of items
    private void updateBienImmobiliersList(List<BienImmobilierComplete> bienImmobilierList){
        RealEstateListFragment realEstateListFragment = (RealEstateListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRealEstateList);
        Objects.requireNonNull(realEstateListFragment).updateAdapter(bienImmobilierList);
    }

    private void configure(){
        // 8 - Configure RecyclerView & ViewModel
        this.configureViewModel();
        // 9 - Get BienImmobiliers from Database
        this.getBienImmobiliers();
    }

    private void determinePaneLayout() {
        FrameLayout fragmentDetail = findViewById(R.id.flDetailContainer);
        if(fragmentDetail != null){
            isTwoPane = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Toast.makeText(this, "Add button selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_edit:
                RealEstateDetailFragment realEstateDetailFragment;
                if (getSupportFragmentManager().findFragmentById(R.id.flDetailContainer) != null) {
                    realEstateDetailFragment = (RealEstateDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flDetailContainer);
                    Toast.makeText(this, "Edit button selected for " + realEstateDetailFragment.bienImmobilierComplete.getType().get(0).getLibelle(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please select an item first", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.action_search:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        configure();
    }
}
