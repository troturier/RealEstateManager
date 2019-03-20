package com.openclassrooms.realestatemanager.controllers.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class RealEstateEditFragment extends Fragment implements PhotoAdapter.Listener, LifecycleOwner, PoiAdapter.Listener {

    private BienImmobilierComplete bienImmobilierComplete;
    private BienImmobilier createdBienImmoobilier;
    private BienImmobilierViewModel bienImmobilierViewModel;
    private boolean created;
    private View view;
    private static String requestCode;

    // FOR DESIGN
    @BindView(R.id.detail_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.poi_recyclerview)
    RecyclerView recyclerViewPoi;

    private ImageView add_media_iv;
    private EditText add_media_path;

    private PhotoAdapter adapter;
    private PoiAdapter poiAdapter;

    private List<PointInteret> pointInteretList;

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
    private EditText priceEt;
    private Spinner typeSpinner;
    private TextView soldTv;
    private Button soldButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.created = false;
        this.createdBienImmoobilier = null;
        requestCode = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("requestCode");
        if(requestCode.equals("edit"))
        // Fetch the bienImmobilier to display from bundle
        bienImmobilierComplete = (BienImmobilierComplete) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra("bienImmobilier");
        else{
            BienImmobilier bienImmobilier = new BienImmobilier();
            List<Photo> photoList = new ArrayList<>();
            List<Photo> photoCouverture = new ArrayList<>();
            List<Type> typeList = new ArrayList<>();
            List<Utilisateur> utilisateurList = new ArrayList<>();
            List<PointInteretBienImmobilier> pointInteretBienImmobilierList = new ArrayList<>();
            this.bienImmobilierComplete = new BienImmobilierComplete(bienImmobilier);
            this.bienImmobilierComplete.setUtilisateurs(utilisateurList);
            this.bienImmobilierComplete.setType(typeList);
            this.bienImmobilierComplete.setPhotos(photoList);
            this.bienImmobilierComplete.setPhotoCouverture(photoCouverture);
            this.bienImmobilierComplete.setPointInteretBienImmobiliers(pointInteretBienImmobilierList);
        }
        //noinspection unchecked
        pointInteretList = (List<PointInteret>) getActivity().getIntent().getSerializableExtra("poi");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_real_estate_edit, container, false);
        ButterKnife.bind(this, view);
        if (savedInstanceState != null) {
            if (uriFilePath == null && savedInstanceState.getString("uri_file_path") != null) {
                uriFilePath = Uri.parse(savedInstanceState.getString("uri_file_path"));
            }
        }

        // Configure ViewModel for database operations
        configureViewModel();

        // Configure RecyclerView for media
        configureMediaRecyclerView();

        configurePoiRecyclerView();

        // Update UI from selected item
        updatePriceUI();
        updateDescriptionUI();
        updateSurfaceUI();
        updateRoomsUI();
        updateLocationUI();
        updateSoldUI();
        return view;
    }

    // -----------------------
    // RECYCLERVIEW UPDATE
    // -----------------------

    private void configureMediaRecyclerView() {
        ButterKnife.bind(Objects.requireNonNull(getActivity()));
        this.adapter = new PhotoAdapter(this, true, getActivity(), requestCode);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        this.adapter.updateData(bienImmobilierComplete);
        this.configureOnClickRecyclerView();
        //addMediaIB.setOnClickListener(v -> createAddMediaDialog());
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.detail_recycler_view_item).setOnItemClickListener((recyclerView, position, v) -> {
            if (position == adapter.getItemCount()-1)
                createAddMediaDialog();
            else
                createEditMediaDialog(position);
        });
    }

    private void updateMediaRecyclerView(){
        this.adapter.updateData(bienImmobilierComplete);
    }

    private void configurePoiRecyclerView(){
        this.poiAdapter = new PoiAdapter(this, bienImmobilierViewModel, requestCode);
        this.recyclerViewPoi.setAdapter(poiAdapter);
        this.recyclerViewPoi.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.poiAdapter.updateData(pointInteretList, bienImmobilierComplete);
        ImageButton addPoiIB = this.view.findViewById(R.id.action_add_poi);
        addPoiIB.setOnClickListener(v -> createAddPoiDialog());
    }

    private void updatePoiRecyclerView(List<PointInteret> pointInteretList){
        this.poiAdapter.updateData(pointInteretList, bienImmobilierComplete);
    }

    // -----------------------
    // EDITTEXT UPDATE
    // -----------------------

    private void updateSoldUI(){
        soldTv = this.view.findViewById(R.id.tvDateSold);
        soldButton = this.view.findViewById(R.id.action_sold);
        if(bienImmobilierComplete.getBienImmobilier().getDateVente() != null){
            if(!bienImmobilierComplete.getBienImmobilier().getDateVente().isEmpty()) {
                soldTv.setVisibility(View.VISIBLE);
                soldTv.setText(String.format("%s%s", getString(R.string.sold_on), bienImmobilierComplete.getBienImmobilier().getDateVente()));
                soldButton.setText(getString(R.string.not_sold));
            }
        }
        else
            soldTv.setText(getString(R.string.is_not_sold_yet));
        soldButton.setOnClickListener(v -> sold());
    }

    private void sold(){
        if(bienImmobilierComplete.getBienImmobilier().getDateVente() == null) {
            bienImmobilierComplete.getBienImmobilier().setDateVente(Utils.getTodayDate());
            soldTv.setVisibility(View.VISIBLE);
            soldTv.setText(String.format("%s%s", getString(R.string.sold_on), Utils.getTodayDate()));
            soldButton.setText(getString(R.string.not_sold));
        }
        else {
            bienImmobilierComplete.getBienImmobilier().setDateVente(null);
            soldTv.setText(getString(R.string.is_not_sold_yet));
            soldButton.setText(getString(R.string.sold));
        }
    }

    private void updateLocationUI(){
        streetEt = this.view.findViewById(R.id.etStreet);
        streetEt.setText(bienImmobilierComplete.getBienImmobilier().getRue());

        street2Et = this.view.findViewById(R.id.etStreet2);

        if (bienImmobilierComplete.getBienImmobilier().getComplementRue() != null) {
            street2Et.setText(bienImmobilierComplete.getBienImmobilier().getComplementRue());
        }

        cpEt = this.view.findViewById(R.id.etCp);
        cpEt.setText(bienImmobilierComplete.getBienImmobilier().getCp());

        cityEt = this.view.findViewById(R.id.etCity);
        cityEt.setText(bienImmobilierComplete.getBienImmobilier().getVille());

        stateEt = this.view.findViewById(R.id.etState);
        stateEt.setText(bienImmobilierComplete.getBienImmobilier().getPays());
    }

    private void updatePriceUI(){
        priceEt = this.view.findViewById(R.id.etPrice);
        priceEt.setText(String.valueOf(Utils.convertEuroToDollar(bienImmobilierComplete.getBienImmobilier().getPrix())));
    }

    private void updateRoomsUI() {
        roomsEt = this.view.findViewById(R.id.etRooms);
        roomsEt.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getPieces()));
        roomsEt.setInputType(InputType.TYPE_CLASS_NUMBER);

        bedroomsEt = this.view.findViewById(R.id.etBedrooms);
        bedroomsEt.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getChambres()));
        bedroomsEt.setInputType(InputType.TYPE_CLASS_NUMBER);

        bathroomsEt = this.view.findViewById(R.id.etBathrooms);
        bathroomsEt.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getSdb()));
        bathroomsEt.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void updateSurfaceUI() {
        surfaceEt = this.view.findViewById(R.id.etSurface);
        surfaceEt.setText(String.valueOf(bienImmobilierComplete.getBienImmobilier().getSurface()));
        surfaceEt.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void updateDescriptionUI() {
        descriptionEt = this.view.findViewById(R.id.etDescription);
        descriptionEt.setText(bienImmobilierComplete.getBienImmobilier().getDescription());
    }

    // -----------------------
    // SPINNERS UPDATE
    // -----------------------

    private void updateUtilisateurSpinner(List<Utilisateur> utilisateurList){
        Spinner utilisateurSpinner = this.view.findViewById(R.id.spinner_utilisateur);
        ArrayAdapter<Utilisateur> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, utilisateurList);
        utilisateurSpinner.setAdapter(arrayAdapter);
        if (requestCode.equals("edit"))
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

        ImageButton addUserIB = this.view.findViewById(R.id.action_add_user);
        addUserIB.setOnClickListener(v -> createAddUserDialog());
    }

    private void updateTypeSpinner(List<Type> types){
        typeSpinner = this.view.findViewById(R.id.spinner_type);
        ArrayAdapter<Type> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, types);
        typeSpinner.setAdapter(arrayAdapter);
        if (requestCode.equals("edit"))
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

        ImageButton addTypeIB = this.view.findViewById(R.id.action_add_type);
        addTypeIB.setOnClickListener(v -> createAddTypeDialog());
    }

    // -----------------------
    // DIALOG BOX CREATION
    // -----------------------

    private void createAddTypeDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity())).create();
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
                Toast.makeText(getActivity(), "Please enter a name for the new type", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void createAddUserDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity())).create();
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
                Toast.makeText(getActivity(), "Please fill all the fields before adding a new user", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    // ---------- MEDIA --------------

    private void createAddMediaDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity())).create();
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.add_media_dialog, null);

        final EditText description = dialogView.findViewById(R.id.etMediaDescription);
        Button submitButton = dialogView.findViewById(R.id.add_media_submit);
        Button cancelButton = dialogView.findViewById(R.id.add_media_cancel);
        ImageButton cameraButton = dialogView.findViewById(R.id.add_media_camera);
        ImageButton galleryButton = dialogView.findViewById(R.id.add_media_gallery);

        this.add_media_iv = dialogView.findViewById(R.id.add_media_iv);
        this.add_media_path = dialogView.findViewById(R.id.etMediaPath);

        galleryButton.setOnClickListener(view -> getImageFromAlbum());

        cameraButton.setOnClickListener(v -> getImageFromCamera());

        cancelButton.setOnClickListener(view -> dialogBuilder.dismiss());
        submitButton.setOnClickListener(view -> {
            if (!description.getText().toString().isEmpty() || this.add_media_path.getText().toString().isEmpty()) {
                Photo photo = new Photo();
                photo.setDescription(description.getText().toString());
                photo.setCheminAcces(this.add_media_path.getText().toString());
                if (requestCode.equals("edit")) {
                    photo.setIdBien(bienImmobilierComplete.getBienImmobilier().getId());
                    addMedia(photo);
                }
                else {
                    bienImmobilierComplete.getPhotos().add(photo);
                    bienImmobilierComplete.getPhotoCouverture().add(photo);
                    adapter.updateData(bienImmobilierComplete);
                }
                dialogBuilder.dismiss();
            } else {
                Toast.makeText(getActivity(), "Please fill all the fields before adding a new media", Toast.LENGTH_LONG).show();
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

    @AfterPermissionGranted(2)
    public void getImageFromCamera(){
        String[] perms = {Manifest.permission.CAMERA};
        if(EasyPermissions.hasPermissions(Objects.requireNonNull(getActivity()), perms)) {
            PackageManager packageManager = Objects.requireNonNull(getActivity()).getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                File mainDirectory = new File(Environment.getExternalStorageDirectory(), "DCIM");
                if (!mainDirectory.exists())
                    //noinspection ResultOfMethodCallIgnored
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
        else {
            EasyPermissions.requestPermissions(this, "A camera access permission is needed in order to take a picture with the camera of your device.",
                    2, perms);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (uriFilePath != null)
            outState.putString("uri_file_path", uriFilePath.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(Objects.requireNonNull(selectedImage),
                    filePathColumn, null, null, null);
            Objects.requireNonNull(cursor).moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Glide
                    .with(getActivity())
                    .load(picturePath)
                    .error(R.mipmap.error)
                    .placeholder(R.mipmap.loading)
                    .centerCrop()
                    .into(add_media_iv);
            this.add_media_path.setText(picturePath);
            cursor.close();
        }
        else if(resultCode == RESULT_OK && requestCode == 2){
            String filePath = uriFilePath.getPath();
            Glide
                    .with(Objects.requireNonNull(getActivity()))
                    .load(filePath)
                    .error(R.mipmap.error)
                    .placeholder(R.mipmap.loading)
                    .centerCrop()
                    .into(add_media_iv);
            this.add_media_path.setText(filePath);
        }

    }

    private void createEditMediaDialog(int position){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity())).create();
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.edit_media_dialog, null);

        final EditText mediaDescription = dialogView.findViewById(R.id.etMediaDescription);
        Button submitButton = dialogView.findViewById(R.id.editMedia_buttonSubmit);
        Button cancelButton = dialogView.findViewById(R.id.editMedia_buttonCancel);
        Button deleteButton = dialogView.findViewById(R.id.editMedia_buttonDelete);
        Button defaultButton = dialogView.findViewById(R.id.editMedia_buttonDefault);
        ImageView editMediaIv = dialogView.findViewById(R.id.edit_media_iv);

        Glide
                .with(getActivity())
                .load(adapter.getItem(position).getCheminAcces())
                .error(R.mipmap.error)
                .placeholder(R.mipmap.loading)
                .centerCrop()
                .into(editMediaIv);
        mediaDescription.setText(adapter.getItem(position).getDescription());

        cancelButton.setOnClickListener(view -> dialogBuilder.dismiss());

        submitButton.setOnClickListener(view -> {
            if (!mediaDescription.getText().toString().isEmpty()) {
                adapter.getItem(position).setDescription(mediaDescription.getText().toString());
                dialogBuilder.dismiss();
                this.adapter.updateData(bienImmobilierComplete);
                if (requestCode.equals("edit")) {
                    this.bienImmobilierViewModel.updatePhoto(adapter.getItem(position));
                }
            } else {
                Toast.makeText(getActivity(), "Please fill all the fields before editing a media", Toast.LENGTH_LONG).show();
            }
        });

        defaultButton.setOnClickListener(v -> {
            if (requestCode.equals("edit")) {
                bienImmobilierComplete.getBienImmobilier().setIdPhotoCouverture(adapter.getItem(position).getId());
            }
            bienImmobilierComplete.getPhotoCouverture().clear();
            bienImmobilierComplete.getPhotoCouverture().add(adapter.getItem(position));
            dialogBuilder.dismiss();
            this.adapter.updateData(bienImmobilierComplete);
        });

        deleteButton.setOnClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        if (requestCode.equals("edit")) {
                            if(bienImmobilierComplete.getBienImmobilier().getIdPhotoCouverture() == null || bienImmobilierComplete.getBienImmobilier().getIdPhotoCouverture() != adapter.getItem(position).getId()){
                                bienImmobilierViewModel.deletePhoto(adapter.getItem(position));
                                dialogBuilder.dismiss();
                                this.bienImmobilierComplete.getPhotos().remove(adapter.getItem(position));
                                this.adapter.updateData(bienImmobilierComplete);
                            }
                            else {
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Please set another picture as default and validate the changes before deleting this one");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        (dialog1, which1) -> dialog1.dismiss());
                                alertDialog.show();
                            }
                        } else {
                            if(bienImmobilierComplete.getPhotoCouverture().get(0) == adapter.getItem(position)){
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Please set another picture as default before deleting this one");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        (dialog1, which1) -> dialog1.dismiss());
                                alertDialog.show();
                            }
                            else {
                                bienImmobilierComplete.getPhotos().remove(adapter.getItem(position));
                                adapter.updateData(bienImmobilierComplete);
                                dialogBuilder.dismiss();
                            }
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    // ----------------------------------------------------------------------

    private void createAddPoiDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity())).create();
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
                Toast.makeText(getActivity(), "Please enter a name for the new point of interest", Toast.LENGTH_LONG).show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    // -----------------------
    // DATABASE
    // -----------------------

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
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
        if (requestCode.equals("edit")) {
            bienImmobilierViewModel.createPhoto(photo);
            bienImmobilierViewModel.getPhotos(bienImmobilierComplete.getBienImmobilier().getId()).observe( this, photos -> updateMediaRecyclerView());
        }
    }

    public void updateBienImmobilier(){
        BienImmobilier bienImmobilier = bienImmobilierComplete.getBienImmobilier();
        boolean allFieldsFilled = true;
        boolean coverPicture = true;
        List<EditText> editTextList = new ArrayList<>();
        editTextList.add(this.descriptionEt);
        editTextList.add(this.surfaceEt);
        editTextList.add(this.roomsEt);
        editTextList.add(this.streetEt);
        editTextList.add(this.priceEt);
        editTextList.add(this.cityEt);
        editTextList.add(this.cpEt);
        editTextList.add(this.stateEt);
        editTextList.add(this.cityEt);

        for(int i=0; i<editTextList.size();i++){
            if(editTextList.get(i).getText().toString().isEmpty() || editTextList.get(i).getText().toString().equals("0")){
                allFieldsFilled = false;
            }
        }
        if(this.bienImmobilierComplete.getPhotoCouverture().size() == 0){
            coverPicture = false;
        }

        if (allFieldsFilled && coverPicture) {
            Intent intent;
            intent = new Intent();

            bienImmobilier.setDescription(this.descriptionEt.getText().toString());
            bienImmobilier.setSurface(Integer.parseInt(this.surfaceEt.getText().toString()));
            bienImmobilier.setPieces(Integer.parseInt(this.roomsEt.getText().toString()));
            bienImmobilier.setChambres(Integer.parseInt(this.bedroomsEt.getText().toString()));
            bienImmobilier.setSdb(Integer.parseInt(this.bathroomsEt.getText().toString()));
            bienImmobilier.setRue(this.streetEt.getText().toString());
            if (!this.street2Et.getText().toString().isEmpty()) {
                bienImmobilier.setComplementRue(this.street2Et.getText().toString());
            } else {
                bienImmobilier.setComplementRue(null);
            }
            bienImmobilier.setPrix(Utils.convertDollarToEuro(Integer.parseInt(this.priceEt.getText().toString())));
            bienImmobilier.setVille(this.cityEt.getText().toString());
            bienImmobilier.setCp(this.cpEt.getText().toString());
            bienImmobilier.setPays(this.stateEt.getText().toString());

            if(requestCode.equals("edit")) {
                this.bienImmobilierViewModel.updateBienImmobilier(bienImmobilier);
                intent.putExtra("bienImmobilier", this.bienImmobilierComplete);
                intent.putExtra("poi", (Serializable) this.pointInteretList);
            }
            else {
                bienImmobilier.setDateEntree(Utils.getTodayDate());
                bienImmobilierViewModel.createBienImmobilier(bienImmobilier);
                bienImmobilierViewModel.getLastBienImmobilier().observe(this, this::createItems);
            }
            Objects.requireNonNull(getActivity()).setResult(RESULT_OK, intent);
            getActivity().finish();
        } else {
            if(!coverPicture){
                AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity())).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Please choose a cover picture.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }else {
                AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity())).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("A field or more is empty, please fill in all fields before proceeding.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }
        }
    }

    private void createItems(BienImmobilier bienImmobilier){
        if (!created) {
            this.createdBienImmoobilier = bienImmobilier;
            for(int i=0;i<this.bienImmobilierComplete.getPointInteretBienImmobiliers().size();i++){
                this.bienImmobilierComplete.getPointInteretBienImmobiliers().get(i).setIdBien(bienImmobilier.getId());
                bienImmobilierViewModel.createPointInteretBienImmobilier(this.bienImmobilierComplete.getPointInteretBienImmobiliers().get(i));
            }
            this.bienImmobilierComplete.getPhotos().remove(bienImmobilierComplete.getPhotoCouverture().get(0));
            for(int i=0;i<this.bienImmobilierComplete.getPhotos().size();i++){
                this.bienImmobilierComplete.getPhotos().get(i).setIdBien(bienImmobilier.getId());
                bienImmobilierViewModel.createPhoto(this.bienImmobilierComplete.getPhotos().get(i));
            }
            this.bienImmobilierComplete.getPhotoCouverture().get(0).setIdBien(bienImmobilier.getId());
            this.bienImmobilierViewModel.createPhoto(this.bienImmobilierComplete.getPhotoCouverture().get(0));
            this.bienImmobilierViewModel.getLastPhoto().observe(this, this::updateCover);
            created = true;
        }
    }

    private void updateCover(Photo photo){
        this.createdBienImmoobilier.setIdPhotoCouverture(photo.getId());
        this.bienImmobilierViewModel.updateBienImmobilier(this.createdBienImmoobilier);
        Utils.createNotification(getActivity());
    }

    @Override
    public void onClickDeleteButton(int position) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
