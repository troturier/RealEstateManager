package com.openclassrooms.realestatemanager.controllers.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.RealEstateEditFragment;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    // -----------------------
    // TOOLBAR BUTTON ACTIONS
    // -----------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        RealEstateEditFragment realEstateEditFragment = (RealEstateEditFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentEdit);
        switch (item.getItemId()) {
            case R.id.action_accept:
                assert realEstateEditFragment != null;
                realEstateEditFragment.updateBienImmobilier();
                return true;

            case R.id.action_cancel:
                intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
