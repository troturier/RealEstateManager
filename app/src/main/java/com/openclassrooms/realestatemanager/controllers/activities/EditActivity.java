package com.openclassrooms.realestatemanager.controllers.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PhotoAdapter;
import com.openclassrooms.realestatemanager.adapters.PoiAdapter;
import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.PointInteret;
import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.models.Utilisateur;
import com.openclassrooms.realestatemanager.repositories.injections.Injection;
import com.openclassrooms.realestatemanager.repositories.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.BienImmobilierViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity implements PhotoAdapter.Listener, LifecycleOwner, PoiAdapter.Listener {

    private BienImmobilierComplete bienImmobilierComplete;
    private BienImmobilierViewModel bienImmobilierViewModel;
    private List<Photo> photos;
    private List<PointInteretBienImmobilier> pointInteretBienImmobiliers;

    // FOR DESIGN
    @BindView(R.id.detail_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.poi_recyclerview)
    RecyclerView recyclerViewPoi;

    private ImageView add_media_iv;
    private EditText add_media_path;

    PhotoAdapter adapter;
    PoiAdapter poiAdapter;

    public List<PointInteret> pointInteretList;

    private Uri uriFilePath;

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
    private TextView soldTv;
    private Button soldButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit);

            if (savedInstanceState != null) {
                if (uriFilePath == null && savedInstanceState.getString("uri_file_path") != null) {
                    uriFilePath = Uri.parse(savedInstanceState.getString("uri_file_path"));
                }
            }

            pointInteretList = (List<PointInteret>) getIntent().getSerializableExtra("poi");

            // Retrieve selected item
            bienImmobilierComplete = (BienImmobilierComplete) getIntent().getSerializableExtra("bienImmobilier");

            // Configure ViewModel for database operations
            configureViewModel();

            // Configure RecyclerView for media
            configureMediaRecyclerView();

            configurePoiRecyclerView();

            // Update UI from selected item
            updateDescriptionUI();
            updateSurfaceUI();
            updateRoomsUI();
        updateLocationUI();
        updateSoldUI();
    }

    // -----------------------
    // RECYCLERVIEW UPDATE
    // -----------------------

    private void configureMediaRecyclerView() {
        ButterKnife.bind(this);
        this.adapter = new PhotoAdapter(this);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.photos = bienImmobilierComplete.getPhotos();
        this.adapter.updateData(photos, bienImmobilierComplete);
        this.configureOnClickRecyclerView();
        ImageButton addMediaIB = findViewById(R.id.action_add_media);
        addMediaIB.setOnClickListener(v -> createAddMediaDialog());
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.detail_recycler_view_item)
                .setOnItemClickListener((recyclerView, position, v) -> createEditMediaDialog(position));
    }

    private void updateMediaRecyclerView(List<Photo> photos){
        this.adapter.updateData(photos, bienImmobilierComplete);
    }

    private void configurePoiRecyclerView(){
        this.poiAdapter = new PoiAdapter(this, bienImmobilierViewModel);
        this.recyclerViewPoi.setAdapter(poiAdapter);
        this.recyclerViewPoi.setLayoutManager(new LinearLayoutManager(this));
        this.pointInteretBienImmobiliers = new ArrayList<>();
        this.pointInteretBienImmobiliers.addAll(this.bienImmobilierComplete.getPointInteretBienImmobiliers());
        this.poiAdapter.updateData(pointInteretList, bienImmobilierComplete);
        ImageButton addPoiIB = findViewById(R.id.action_add_poi);
        addPoiIB.setOnClickListener(v -> createAddPoiDialog());
    }

    private void updatePoiRecyclerView(List<PointInteret> pointInteretList){
        this.poiAdapter.updateData(pointInteretList, bienImmobilierComplete);
    }

    // -----------------------
    // EDITTEXT UPDATE
    // -----------------------

    private void updateSoldUI(){
        soldTv = findViewById(R.id.tvDateSold);
        soldButton = findViewById(R.id.action_sold);
        if(bienImmobilierComplete.getBienImmobilier().getDateVente() != null){
            if(!bienImmobilierComplete.getBienImmobilier().getDateVente().isEmpty()) {
                soldTv.setVisibility(View.VISIBLE);
                soldTv.setText("Sold on : " + bienImmobilierComplete.getBienImmobilier().getDateVente());
                soldButton.setText("Not sold");
            }
        }
        else
            soldTv.setText("This property is not sold yet");
        soldButton.setOnClickListener(v -> sold());
    }

    private void sold(){
        if(bienImmobilierComplete.getBienImmobilier().getDateVente() == null) {
            bienImmobilierComplete.getBienImmobilier().setDateVente(Utils.getTodayDate());
            soldTv.setVisibility(View.VISIBLE);
            soldTv.setText("Sold on : " + Utils.getTodayDate());
            soldButton.setText("Not sold");
        }
        else {
            bienImmobilierComplete.getBienImmobilier().setDateVente(null);
            soldTv.setText("This property is not sold yet");
            soldButton.setText("Sold");
        }
    }

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
        ArrayAdapter<Utilisateur> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, utilisateurList);
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
        ArrayAdapter<Type> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
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

    // ---------- MEDIA --------------

    private void createAddMediaDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.add_media_dialog, null);

        final EditText description = dialogView.findViewById(R.id.etMediaDescription);
        Button submitButton = dialogView.findViewById(R.id.add_media_submit);
        Button cancelButton = dialogView.findViewById(R.id.add_media_cancel);
        ImageButton cameraButton = dialogView.findViewById(R.id.add_media_camera);
        ImageButton galleryButton = dialogView.findViewById(R.id.add_media_gallery);

        this.add_media_iv = dialogView.findViewById(R.id.add_media_iv);
        this.add_media_path = dialogView.findViewById(R.id.etMediaPath);

        galleryButton.setOnClickListener(view -> {
            getImageFromAlbum();
        });

        cameraButton.setOnClickListener(v -> getImageFromCamera());

        cancelButton.setOnClickListener(view -> dialogBuilder.dismiss());
        submitButton.setOnClickListener(view -> {
            if (!description.getText().toString().isEmpty() || this.add_media_path.getText().toString().isEmpty()) {
                Photo photo = new Photo();
                photo.setDescription(description.getText().toString());
                photo.setCheminAcces(this.add_media_path.getText().toString());
                photo.setIdBien(bienImmobilierComplete.getBienImmobilier().getId());
                addMedia(photo);
                dialogBuilder.dismiss();
            } else {
                Toast.makeText(EditActivity.this, "Please fill all the fields before adding a new media", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void getImageFromAlbum(){
        try{
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);
        }catch(Exception exp){
            Log.i("Error",exp.toString());
        }
    }

    private void getImageFromCamera(){
        PackageManager packageManager = this.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            File mainDirectory = new File(Environment.getExternalStorageDirectory(), "DCIM");
            if (!mainDirectory.exists())
                mainDirectory.mkdirs();

            Calendar calendar = Calendar.getInstance();

            uriFilePath = Uri.fromFile(new File(mainDirectory, "IMG_" + calendar.getTimeInMillis()));
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (uriFilePath != null)
            outState.putString("uri_file_path", uriFilePath.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Picasso.get()
                    .load(new File(picturePath))
                    .resize(250, 250)
                    .centerCrop()
                    .error(R.mipmap.ic_iv_placeholder_no_image)
                    .into(add_media_iv);
            this.add_media_path.setText(picturePath);
            cursor.close();
        }
        else if(resultCode == RESULT_OK && requestCode == 2){
            String filePath = uriFilePath.getPath();
            Picasso.get()
                    .load(new File(filePath))
                    .resize(250, 250)
                    .centerCrop()
                    .error(R.mipmap.ic_iv_placeholder_no_image)
                    .into(add_media_iv);
            this.add_media_path.setText(filePath);
        }

    }

    // ----------------------------------------------------------------------

    private void createAddPoiDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.add_poi_dialog, null);

        final EditText editText = dialogView.findViewById(R.id.poi_name);
        Button submitButton = dialogView.findViewById(R.id.addPoi_buttonSubmit);
        Button cancelButton = dialogView.findViewById(R.id.addPoi_buttonCancel);

        cancelButton.setOnClickListener(view -> dialogBuilder.dismiss());
        submitButton.setOnClickListener(view -> {
            if (!editText.getText().toString().isEmpty()) {
                PointInteret pointInteret = new PointInteret();
                pointInteret.setLibelle(editText.getText().toString());
                addPoi(pointInteret);
                dialogBuilder.dismiss();
            } else {
                Toast.makeText(EditActivity.this, "Please enter a name for the new point of interest", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void createEditMediaDialog(int position){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.edit_media_dialog, null);

        final EditText mediaDescription = dialogView.findViewById(R.id.etMediaDescription);
        Button submitButton = dialogView.findViewById(R.id.editMedia_buttonSubmit);
        Button cancelButton = dialogView.findViewById(R.id.editMedia_buttonCancel);
        Button deleteButton = dialogView.findViewById(R.id.editMedia_buttonDelete);
        Button defaultButton = dialogView.findViewById(R.id.editMedia_buttonDefault);
        ImageView editMediaIv = dialogView.findViewById(R.id.edit_media_iv);

        Picasso.get()
                .load(new File(adapter.getItem(position).getCheminAcces()))
                .resize(250, 250)
                .centerCrop()
                .error(R.mipmap.ic_iv_placeholder_no_image)
                .into(editMediaIv);
        mediaDescription.setText(adapter.getItem(position).getDescription());

        cancelButton.setOnClickListener(view -> dialogBuilder.dismiss());

        submitButton.setOnClickListener(view -> {
            if (!mediaDescription.getText().toString().isEmpty()) {
                adapter.getItem(position).setDescription(mediaDescription.getText().toString());
                dialogBuilder.dismiss();
                this.photos = bienImmobilierComplete.getPhotos();
                this.adapter.updateData(photos, bienImmobilierComplete);
                this.bienImmobilierViewModel.updatePhoto(adapter.getItem(position));
            } else {
                Toast.makeText(EditActivity.this, "Please fill all the fields before editing a media", Toast.LENGTH_LONG).show();
            }
        });

        defaultButton.setOnClickListener(v -> {
            bienImmobilierComplete.getBienImmobilier().setIdPhotoCouverture(adapter.getItem(position).getId());
            dialogBuilder.dismiss();
            this.photos = bienImmobilierComplete.getPhotos();
            this.adapter.updateData(photos, bienImmobilierComplete);
        });

        deleteButton.setOnClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        if(bienImmobilierComplete.getBienImmobilier().getIdPhotoCouverture() != adapter.getItem(position).getId()){
                            bienImmobilierViewModel.deletePhoto(adapter.getItem(position));
                            dialogBuilder.dismiss();
                            this.bienImmobilierComplete.getPhotos().remove(adapter.getItem(position));
                            this.photos = bienImmobilierComplete.getPhotos();
                            this.adapter.updateData(photos, bienImmobilierComplete);
                        }
                        else {
                            Toast.makeText(this, "Please set another picture as default and validate the changes before deleting this one", Toast.LENGTH_LONG).show();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
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

        bienImmobilierViewModel.getUtilisateurs().observe( this, this::updateUtilisateurSpinner);

        bienImmobilierViewModel.getTypes().observe( this, this::updateTypeSpinner);
    }

    private void addType(Type type){
        bienImmobilierViewModel.createType(type);
        bienImmobilierViewModel.getTypes().observe( this, this::updateTypeSpinner);
    }

    private void addPoi(PointInteret pointInteret){
        bienImmobilierViewModel.createPointInteret(pointInteret);
        bienImmobilierViewModel.getPointInterets().observe( this, this::updatePoiRecyclerView);
    }

    private void addUser(Utilisateur user){
        bienImmobilierViewModel.createUtilisateur(user);
        bienImmobilierViewModel.getUtilisateurs().observe( this, this::updateUtilisateurSpinner);
    }

    private void addMedia(Photo photo){
        bienImmobilierViewModel.createPhoto(photo);
        bienImmobilierViewModel.getPhotos(bienImmobilierComplete.getBienImmobilier().getId()).observe( this, this::updateMediaRecyclerView);
    }

    private void updateBienImmobilier(BienImmobilier bienImmobilier){
        boolean allFieldsFilled = true;
        if(!this.descriptionEt.getText().toString().isEmpty())
            bienImmobilier.setDescription(this.descriptionEt.getText().toString());
        else
            allFieldsFilled = false;
        if(!this.surfaceEt.getText().toString().isEmpty())
            bienImmobilier.setSurface(Integer.parseInt(this.surfaceEt.getText().toString()));
        else
            allFieldsFilled = false;
        if(!this.roomsEt.getText().toString().isEmpty())
            bienImmobilier.setPieces(Integer.parseInt(this.roomsEt.getText().toString()));
        else
            allFieldsFilled = false;
        if(!this.bedroomsEt.getText().toString().isEmpty())
            bienImmobilier.setChambres(Integer.parseInt(this.bedroomsEt.getText().toString()));
        else
            allFieldsFilled = false;
        if(!this.bathroomsEt.getText().toString().isEmpty())
            bienImmobilier.setSdb(Integer.parseInt(this.bathroomsEt.getText().toString()));
        else
            allFieldsFilled = false;
        if(!this.streetEt.getText().toString().isEmpty())
            bienImmobilier.setRue(this.streetEt.getText().toString());
        else
            allFieldsFilled = false;
        if (!this.street2Et.getText().toString().isEmpty()) {
            bienImmobilier.setComplementRue(this.street2Et.getText().toString());
        } else {
            bienImmobilier.setComplementRue(null);
        }
        if(!this.cityEt.getText().toString().isEmpty())
            bienImmobilier.setVille(this.cityEt.getText().toString());
        else
            allFieldsFilled = false;
        if(!this.cpEt.getText().toString().isEmpty())
            bienImmobilier.setCp(this.cpEt.getText().toString());
        else
            allFieldsFilled = false;
        if(!this.stateEt.getText().toString().isEmpty())
            bienImmobilier.setPays(this.stateEt.getText().toString());
        else
            allFieldsFilled = false;

        if (allFieldsFilled) {
            this.bienImmobilierViewModel.updateBienImmobilier(bienImmobilier);
            Intent intent;
            intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("A field or more is empty, please fill in all fields before proceeding.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }
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

    @Override
    public void onClickDeleteButton(int position) {

    }
}
