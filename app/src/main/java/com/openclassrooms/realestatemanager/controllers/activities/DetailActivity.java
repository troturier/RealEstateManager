package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.RealEstateDetailFragment;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_detail);
        // Fetch the bienImmobilier to display from bundle
        BienImmobilierComplete bienImmobilierComplete = (BienImmobilierComplete) getIntent().getSerializableExtra("bienImmobilier");
        if (savedInstanceState == null){
            // Insert detail fragment based on the bienImmobilier passed
            RealEstateDetailFragment realEstateDetailFragment = RealEstateDetailFragment.newInstance(bienImmobilierComplete);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flDetailContainer, realEstateDetailFragment);
            fragmentTransaction.commit();
        }
    }

    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }
}
