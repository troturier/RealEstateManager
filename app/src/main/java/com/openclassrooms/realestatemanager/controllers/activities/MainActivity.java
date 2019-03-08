package com.openclassrooms.realestatemanager.controllers.activities;

import android.content.Intent;
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
import com.openclassrooms.realestatemanager.models.PointInteret;
import com.openclassrooms.realestatemanager.repositories.injections.Injection;
import com.openclassrooms.realestatemanager.repositories.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.BienImmobilierViewModel;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    // 1 - FOR DATA
    private BienImmobilierViewModel bienImmobilierViewModel;

    public List<PointInteret> pointInteretList;
    private List<BienImmobilierComplete> bienImmobilierCompleteList;

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

    // 3 - Get all BienImmobiliers
    public void getBienImmobiliers(){
        this.bienImmobilierViewModel.getBienImmobiliersComplete().observe(this, this::updateBienImmobiliersList);
    }

    public void getPoi(){
        this.bienImmobilierViewModel.getPointInterets().observe(this, this::updatePoiList);
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
        this.bienImmobilierCompleteList = bienImmobilierList;
        getPoi();
    }

    private void updatePoiList(List<PointInteret> pointInteretList){
        this.pointInteretList = pointInteretList;
        RealEstateListFragment realEstateListFragment = (RealEstateListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRealEstateList);
        Objects.requireNonNull(realEstateListFragment).updateAdapter(this.bienImmobilierCompleteList, this.pointInteretList);
        if(isTwoPane && realEstateListFragment.adapter.row_index > -1){
            realEstateListFragment.updateDetailFragment();
        }
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
                    Intent intent = new Intent(this, EditActivity.class);
                    intent.putExtra("bienImmobilier", realEstateDetailFragment.bienImmobilierComplete);
                    intent.putExtra("poi", (Serializable) pointInteretList);
                    startActivityForResult(intent, 1);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                getBienImmobiliers();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        configure();
    }
}
