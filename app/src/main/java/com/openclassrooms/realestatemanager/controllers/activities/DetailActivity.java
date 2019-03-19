package com.openclassrooms.realestatemanager.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.RealEstateDetailFragment;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.PointInteret;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class DetailActivity extends AppCompatActivity {

    private List<PointInteret> pointInteretList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_detail);
        // Fetch the bienImmobilier to display from bundle
        BienImmobilierComplete bienImmobilierComplete = (BienImmobilierComplete) getIntent().getSerializableExtra("bienImmobilier");
        //noinspection unchecked
        pointInteretList = (List<PointInteret>) getIntent().getSerializableExtra("poi");
        if (savedInstanceState == null){
            // Insert detail fragment based on the bienImmobilier passed
            RealEstateDetailFragment realEstateDetailFragment = RealEstateDetailFragment.newInstance(bienImmobilierComplete, pointInteretList);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flDetailContainer, realEstateDetailFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(this, EditActivity.class);
                intent.putExtra("poi", (Serializable) pointInteretList);
                intent.putExtra("requestCode", "edit");
                startActivityForResult(intent, 1);
                return true;

            case R.id.action_edit:
                RealEstateDetailFragment realEstateDetailFragment = (RealEstateDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flDetailContainer);
                intent = new Intent(this, EditActivity.class);
                intent.putExtra("bienImmobilier", Objects.requireNonNull(realEstateDetailFragment).bienImmobilierComplete);
                intent.putExtra("poi", (Serializable) pointInteretList);
                intent.putExtra("requestCode", "edit");
                startActivityForResult(intent, 1);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                BienImmobilierComplete bienImmobilierComplete = (BienImmobilierComplete) data.getSerializableExtra("bienImmobilier");
                //noinspection unchecked
                pointInteretList = (List<PointInteret>) data.getSerializableExtra("poi");
                RealEstateDetailFragment realEstateDetailFragment = (RealEstateDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flDetailContainer);
                Objects.requireNonNull(realEstateDetailFragment).updateContent(bienImmobilierComplete, this.pointInteretList);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }
}
