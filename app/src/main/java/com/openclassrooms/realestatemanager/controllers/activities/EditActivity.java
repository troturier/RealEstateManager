package com.openclassrooms.realestatemanager.controllers.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.models.Utilisateur;
import com.openclassrooms.realestatemanager.repositories.injections.Injection;
import com.openclassrooms.realestatemanager.repositories.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewmodels.BienImmobilierViewModel;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class EditActivity extends AppCompatActivity {

    private BienImmobilierComplete bienImmobilierComplete;
    private BienImmobilierViewModel bienImmobilierViewModel;

    // UI
    private EditText streetEt;
    private EditText street2Et;
    private EditText cpEt;
    private EditText cityEt;
    private EditText stateEt;
    private EditText bathroomsEt;
    private EditText bedroomsEt;
    private EditText roomsEt;
    private EditText surfaceEt;
    private EditText descriptionEt;
    private Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Retrieve selected item
        bienImmobilierComplete = (BienImmobilierComplete) getIntent().getSerializableExtra("bienImmobilier");

        // Configure ViewModel for database operations
        configureViewModel();

        // Update UI from selected item
        updateDescriptionUI();
        updateSurfaceUI();
        updateRoomsUI();
        updateLocationUI();
    }

    // -----------------------
    // EDITTEXT UPDATE
    // -----------------------

    private void updateLocationUI(){
        streetEt = findViewById(R.id.etStreet);
        streetEt.setText(bienImmobilierComplete.getBienImmobilier().getRue());

        street2Et = findViewById(R.id.etStreet2);

        if (bienImmobilierComplete.getBienImmobilier().getComplementRue() != null) {
            street2Et.setText(bienImmobilierComplete.getBienImmobilier().getComplementRue());
        }

        cpEt = findViewById(R.id.etCp);
        cpEt.setText(bienImmobilierComplete.getBienImmobilier().getCp());

        cityEt = findViewById(R.id.etCity);
        cityEt.setText(bienImmobilierComplete.getBienImmobilier().getVille());

        stateEt = findViewById(R.id.etState);
        stateEt.setText(bienImmobilierComplete.getBienImmobilier().getPays());
    }

    private void updateRoomsUI() {
        roomsEt = findViewById(R.id.etRooms);
        roomsEt.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getPieces()));
        roomsEt.setInputType(InputType.TYPE_CLASS_NUMBER);

        bedroomsEt = findViewById(R.id.etBedrooms);
        bedroomsEt.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getChambres()));
        bedroomsEt.setInputType(InputType.TYPE_CLASS_NUMBER);

        bathroomsEt = findViewById(R.id.etBathrooms);
        bathroomsEt.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getSdb()));
        bathroomsEt.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void updateSurfaceUI() {
        surfaceEt = findViewById(R.id.etSurface);
        surfaceEt.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getSurface()));
        surfaceEt.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void updateDescriptionUI() {
        descriptionEt = findViewById(R.id.etDescription);
        descriptionEt.setText(bienImmobilierComplete.getBienImmobilier().getDescription());
    }

    // -----------------------
    // SPINNERS UPDATE
    // -----------------------

    private void updateUtilisateurSpinner(List<Utilisateur> utilisateurList){
        Spinner utilisateurSpinner = findViewById(R.id.spinner_utilisateur);
        ArrayAdapter<Utilisateur> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, utilisateurList);
        utilisateurSpinner.setAdapter(arrayAdapter);
        utilisateurSpinner.setSelection(bienImmobilierComplete.getUtilisateurs().get(0).getId()-1);
        utilisateurSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utilisateur utilisateur = (Utilisateur) utilisateurSpinner.getSelectedItem();
                bienImmobilierComplete.getBienImmobilier().setIdAgent(utilisateur.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageButton addUserIB = findViewById(R.id.action_add_user);
        addUserIB.setOnClickListener(v -> createAddUserDialog());
    }

    private void updateTypeSpinner(List<Type> types){
        typeSpinner = findViewById(R.id.spinner_type);
        ArrayAdapter<Type> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        typeSpinner.setAdapter(arrayAdapter);
        typeSpinner.setSelection(bienImmobilierComplete.getType().get(0).getId()-1);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Type type = (Type) typeSpinner.getSelectedItem();
                bienImmobilierComplete.getBienImmobilier().setIdType(type.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageButton addTypeIB = findViewById(R.id.action_add_type);
        addTypeIB.setOnClickListener(v -> createAddTypeDialog());
    }

    // -----------------------
    // DIALOG BOX CREATION
    // -----------------------

    private void createAddTypeDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.add_type_dialog, null);

        final EditText editText = dialogView.findViewById(R.id.type_name);
        Button submitButton = dialogView.findViewById(R.id.addType_buttonSubmit);
        Button cancelButton = dialogView.findViewById(R.id.addType_buttonCancel);

        cancelButton.setOnClickListener(view -> dialogBuilder.dismiss());
        submitButton.setOnClickListener(view -> {
            if (!editText.getText().toString().isEmpty()) {
                Type type = new Type();
                type.setLibelle(editText.getText().toString());
                addType(type);
                dialogBuilder.dismiss();
            } else {
                Toast.makeText(EditActivity.this, "Please enter a name for the new type", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void createAddUserDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.add_user_dialog, null);

        final EditText userLNEditText = dialogView.findViewById(R.id.user_lastname);
        final EditText userFNEditText = dialogView.findViewById(R.id.user_firstname);
        Button submitButton = dialogView.findViewById(R.id.addUser_buttonSubmit);
        Button cancelButton = dialogView.findViewById(R.id.addUser_buttonCancel);

        cancelButton.setOnClickListener(view -> dialogBuilder.dismiss());
        submitButton.setOnClickListener(view -> {
            if (!userFNEditText.getText().toString().isEmpty() || !userLNEditText.getText().toString().isEmpty()) {
                Utilisateur user = new Utilisateur();
                user.setNom(userLNEditText.getText().toString());
                user.setPrenom(userFNEditText.getText().toString());
                addUser(user);
                dialogBuilder.dismiss();
            } else {
                Toast.makeText(EditActivity.this, "Please fill all the fields before adding a new user", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    // -----------------------
    // DATABASE
    // -----------------------

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        bienImmobilierViewModel = ViewModelProviders.of(this, mViewModelFactory).get(BienImmobilierViewModel.class);

        bienImmobilierViewModel.getUtilisateurs().observe(this, this::updateUtilisateurSpinner);

        bienImmobilierViewModel.getTypes().observe(this, this::updateTypeSpinner);
    }

    private void addType(Type type){
        bienImmobilierViewModel.createType(type);
        bienImmobilierViewModel.getTypes().observe(this, this::updateTypeSpinner);
    }

    private void addUser(Utilisateur user){
        bienImmobilierViewModel.createUtilisateur(user);
        bienImmobilierViewModel.getUtilisateurs().observe(this, this::updateUtilisateurSpinner);
    }

    private void updateBienImmobilier(BienImmobilier bienImmobilier){
        bienImmobilier.setDescription(this.descriptionEt.getText().toString());
        bienImmobilier.setSurface(Integer.parseInt(this.surfaceEt.getText().toString()));
        bienImmobilier.setPieces(Integer.parseInt(this.roomsEt.getText().toString()));
        bienImmobilier.setChambres(Integer.parseInt(this.bedroomsEt.getText().toString()));
        bienImmobilier.setSdb(Integer.parseInt(this.bathroomsEt.getText().toString()));
        bienImmobilier.setRue(this.streetEt.getText().toString());
        bienImmobilier.setComplementRue(this.street2Et.getText().toString());
        bienImmobilier.setVille(this.cityEt.getText().toString());
        bienImmobilier.setCp(this.cpEt.getText().toString());
        bienImmobilier.setPays(this.stateEt.getText().toString());

        this.bienImmobilierViewModel.updateBienImmobilier(bienImmobilier);
    }

    // -----------------------
    // TOOLBAR BUTTON ACTIONS
    // -----------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_accept:
                updateBienImmobilier(bienImmobilierComplete.getBienImmobilier());
                intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }
}
