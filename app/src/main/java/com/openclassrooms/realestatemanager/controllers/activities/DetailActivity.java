package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.RealEstateDetailFragment;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Toast.makeText(this, "Add button selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_edit:
                RealEstateDetailFragment realEstateDetailFragment = (RealEstateDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flDetailContainer);
                Toast.makeText(this, "Edit button selected for " + realEstateDetailFragment.bienImmobilierComplete.getType().get(0).getLibelle(), Toast.LENGTH_SHORT).show();
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
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }
}
