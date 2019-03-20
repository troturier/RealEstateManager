package com.openclassrooms.realestatemanager.controllers.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.RealEstateDetailFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.RealEstateListFragment;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.PointInteret;
import com.openclassrooms.realestatemanager.repositories.injections.Injection;
import com.openclassrooms.realestatemanager.repositories.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.utils.ExpandableHeightGridView;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.BienImmobilierViewModel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    // 1 - FOR DATA
    private BienImmobilierViewModel bienImmobilierViewModel;

    private List<PointInteret> pointInteretList;
    private List<BienImmobilierComplete> bienImmobilierCompleteList;

    private RealEstateListFragment realEstateListFragment;

    public boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        determinePaneLayout();
        configure();
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

    private void getPoi(){
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
        this.realEstateListFragment = (RealEstateListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRealEstateList);
        Objects.requireNonNull(realEstateListFragment).updateAdapter(this.bienImmobilierCompleteList, this.pointInteretList);
        if(isTwoPane && realEstateListFragment.adapter.row_index > -1){
            realEstateListFragment.updateDetailFragment();
        }
    }

    @AfterPermissionGranted(1)
    private void configure(){
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 8 - Configure RecyclerView & ViewModel
            this.configureViewModel();
            // 9 - Get BienImmobiliers from Database
            this.getBienImmobiliers();
        } else {
            EasyPermissions.requestPermissions(this, "This application requires to have access to the storage of your device to be able to display the photos of the various real estates.",
                    1, perms);
        }
    }

    private void determinePaneLayout() {
        FrameLayout fragmentDetail = findViewById(R.id.flDetailContainer);
        if(fragmentDetail != null){
            isTwoPane = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, EditActivity.class);
        switch (item.getItemId()) {
            case R.id.action_add:
                intent.putExtra("poi", (Serializable) pointInteretList);
                intent.putExtra("requestCode", "add");
                startActivityForResult(intent, 1);
                return true;

            case R.id.action_edit:
                RealEstateDetailFragment realEstateDetailFragment;
                if (getSupportFragmentManager().findFragmentById(R.id.flDetailContainer) != null) {
                    realEstateDetailFragment = (RealEstateDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flDetailContainer);
                    assert realEstateDetailFragment != null;
                    intent.putExtra("bienImmobilier", realEstateDetailFragment.bienImmobilierComplete);
                    intent.putExtra("poi", (Serializable) pointInteretList);
                    intent.putExtra("requestCode", "edit");
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(this, "Please select an item first", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.action_search:
                createSearchDialog();
                return true;

            case R.id.action_mortgage:
                createMortgageSimulatorDialog();
                return true;

            case R.id.action_map:
                Intent mapIntent = new Intent(this, MapsActivity.class);
                startActivity(mapIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void createMortgageSimulatorDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.mortgage_simulator_dialog, null);

        final EditText amountEt = dialogView.findViewById(R.id.amountEt);
        final EditText monthEt = dialogView.findViewById(R.id.monthEt);
        final EditText yearEt = dialogView.findViewById(R.id.yearEt);
        final EditText rateEt = dialogView.findViewById(R.id.rateEt);
        final EditText contributionEt = dialogView.findViewById(R.id.contributionEt);

        final TextView resultTv = dialogView.findViewById(R.id.resultTv);

        final Button cancelButton = dialogView.findViewById(R.id.mortgage_cancel);
        final Button calculateButton = dialogView.findViewById(R.id.mortgage_calculate);

        cancelButton.setOnClickListener(v -> dialogBuilder.dismiss());

        calculateButton.setOnClickListener(v -> {
            if (monthEt.getText().toString().isEmpty() && yearEt.getText().toString().isEmpty()) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("No mortgage term specified");
                alertDialog.setMessage("Please specify duration first");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog1, which1) -> dialog1.dismiss());
                alertDialog.show();
            }
            else if(amountEt.getText().toString().isEmpty()){
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("No amount specified");
                alertDialog.setMessage("Please specify an amount first");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog1, which1) -> dialog1.dismiss());
                alertDialog.show();
            }
            else {
                int month;
                if (!monthEt.getText().toString().isEmpty()) {
                    month = Integer.parseInt(monthEt.getText().toString());
                } else {
                    month = 0;
                }

                int year;
                if (!yearEt.getText().toString().isEmpty()) {
                    year = Integer.parseInt(yearEt.getText().toString());
                } else {
                    year = 0;
                }

                int amount = Integer.parseInt(amountEt.getText().toString());

                float rate;
                if (!rateEt.getText().toString().isEmpty()) {
                    rate = Float.parseFloat(rateEt.getText().toString());
                } else {
                    rate = 1;
                }

                int contribution;
                if (!contributionEt.getText().toString().isEmpty()) {
                    contribution = Integer.parseInt(contributionEt.getText().toString());
                } else {
                    contribution = 0;
                }

                float result = (amount-contribution)/(month+(year*12))*rate;

                resultTv.setText("$"+String.format("%.2f", result) + " / month ");
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void createSearchDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.search_dialog, null);

        int prices[] = new int[bienImmobilierCompleteList.size()];
        int surfaces[] = new int[bienImmobilierCompleteList.size()];
        int rooms[] = new int[bienImmobilierCompleteList.size()];
        int bathrooms[] = new int[bienImmobilierCompleteList.size()];
        int bedrooms[] = new int[bienImmobilierCompleteList.size()];
        int numberPicture[] = new int[bienImmobilierCompleteList.size()];
        ArrayList<String> cityList = new ArrayList<>();
        cityList.add("");

        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("");

        for(int i=0; i<bienImmobilierCompleteList.size();i++){
            prices[i] = bienImmobilierCompleteList.get(i).getBienImmobilier().getPrix();
            surfaces[i] = bienImmobilierCompleteList.get(i).getBienImmobilier().getSurface();
            rooms[i] = bienImmobilierCompleteList.get(i).getBienImmobilier().getPieces();
            bathrooms[i] = bienImmobilierCompleteList.get(i).getBienImmobilier().getSdb();
            bedrooms[i] = bienImmobilierCompleteList.get(i).getBienImmobilier().getChambres();
            numberPicture[i] = bienImmobilierCompleteList.get(i).getPhotos().size();
            cityList.add(bienImmobilierCompleteList.get(i).getBienImmobilier().getVille());
            typeList.add(bienImmobilierCompleteList.get(i).getType().get(0).getLibelle());
        }

        String[] pois = new String[pointInteretList.size()];

        for(int i=0; i<pointInteretList.size();i++){
            pois[i] = pointInteretList.get(i).getLibelle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, pois);
        final ExpandableHeightGridView gridView = dialogView.findViewById(R.id.poiGv);
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);

        final CrystalRangeSeekbar priceSeekBar = dialogView.findViewById(R.id.rangeSeekbarPrice);
        final TextView priceMin = dialogView.findViewById(R.id.priceMin);
        final TextView priceMax = dialogView.findViewById(R.id.priceMax);

        final CrystalRangeSeekbar surfaceSeekBar = dialogView.findViewById(R.id.rangeSeekbarSurface);
        final TextView surfaceMin = dialogView.findViewById(R.id.surfaceMin);
        final TextView surfaceMax = dialogView.findViewById(R.id.surfaceMax);
        
        final CrystalRangeSeekbar roomsSeekBar = dialogView.findViewById(R.id.rangeSeekbarRooms);
        final TextView roomsMin = dialogView.findViewById(R.id.roomsMin);
        final TextView roomsMax = dialogView.findViewById(R.id.roomsMax);

        final CrystalRangeSeekbar bathroomsSeekBar = dialogView.findViewById(R.id.rangeSeekbarbathrooms);
        final TextView bathroomsMin = dialogView.findViewById(R.id.bathroomsMin);
        final TextView bathroomsMax = dialogView.findViewById(R.id.bathroomsMax);

        final CrystalRangeSeekbar bedroomsSeekBar = dialogView.findViewById(R.id.rangeSeekbarBedrooms);
        final TextView bedroomsMin = dialogView.findViewById(R.id.bedroomsMin);
        final TextView bedroomsMax = dialogView.findViewById(R.id.bedroomsMax);
        
        final CrystalRangeSeekbar numberPictureSeekBar = dialogView.findViewById(R.id.rangeSeekbarNumberPicture);
        final TextView numberPictureMin = dialogView.findViewById(R.id.numberPictureMin);
        final TextView numberPictureMax = dialogView.findViewById(R.id.numberPictureMax);

        Button submitButton = dialogView.findViewById(R.id.search_buttonSubmit);
        Button cancelButton = dialogView.findViewById(R.id.search_buttonCancel);

        DatePickerDialog.OnDateSetListener dateSetListener1;
        DatePickerDialog.OnDateSetListener dateSetListener2;
        DatePickerDialog.OnDateSetListener dateSetListener3;
        DatePickerDialog.OnDateSetListener dateSetListener4;
        final TextView dateAdded1 = dialogView.findViewById(R.id.dateAddedPicker1);
        final TextView dateAdded2 = dialogView.findViewById(R.id.dateAddedPicker2);
        final TextView dateSold1 = dialogView.findViewById(R.id.dateSoldPicker1);
        final TextView dateSold2 = dialogView.findViewById(R.id.dateSoldPicker2);

        final Spinner citySpinner = dialogView.findViewById(R.id.spinner_city);
        final Spinner typeSpinner = dialogView.findViewById(R.id.spinner_type);

        // -----------------
        // DATE ADDED
        // -----------------

        dateSetListener1 = (view, year, month, dayOfMonth) -> dateAdded1.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));

        dateAdded1.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    dateSetListener1,
                    year,month,day
                    );
            Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        dateSetListener2 = (view, year, month, dayOfMonth) -> dateAdded2.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));

        dateAdded2.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    dateSetListener2,
                    year,month,day
                    );
            Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        // -----------------
        // DATE SOLD
        // -----------------

        dateSetListener3 = (view, year, month, dayOfMonth) -> dateSold1.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));

        dateSold1.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    dateSetListener3,
                    year,month,day
                    );
            Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        dateSetListener4 = (view, year, month, dayOfMonth) -> dateSold2.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));

        dateSold2.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    dateSetListener4,
                    year,month,day
                    );
            Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        // -----------------

        // -----------------
        // PRICE
        // -----------------
        priceSeekBar.setMaxValue(Utils.getMaxValue(prices));
        priceSeekBar.setMaxStartValue(Utils.getMaxValue(prices));
        priceSeekBar.setMinValue(Utils.getMinValue(prices));
        priceSeekBar.setMinStartValue(Utils.getMinValue(prices));

        priceSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            priceMin.setText("$"+String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Utils.convertEuroToDollar(minValue.intValue()))));
            priceMax.setText("$"+String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Utils.convertEuroToDollar(maxValue.intValue()))));
        });
        // -----------------

        // -----------------
        // CITY
        // -----------------
        cityList = Utils.removeDuplicates(cityList);
        cityList.sort(String::compareToIgnoreCase);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cityList);
        citySpinner.setAdapter(arrayAdapter);
        // -----------------

        // -----------------
        // TYPE
        // -----------------
        typeList = Utils.removeDuplicates(typeList);
        typeList.sort(String::compareToIgnoreCase);
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeList);
        typeSpinner.setAdapter(arrayAdapterType);
        // -----------------

        // -----------------
        // SURFACE
        // -----------------
        surfaceSeekBar.setMaxValue(Utils.getMaxValue(surfaces));
        surfaceSeekBar.setMaxStartValue(Utils.getMaxValue(surfaces));
        surfaceSeekBar.setMinValue(Utils.getMinValue(surfaces));
        surfaceSeekBar.setMinStartValue(Utils.getMinValue(surfaces));

        surfaceSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            surfaceMin.setText(minValue.toString()+" m²");
            surfaceMax.setText(maxValue.toString()+" m²");
        });
        // -----------------
        
        // -----------------
        // ROOMS
        // -----------------
        roomsSeekBar.setMaxValue(Utils.getMaxValue(rooms));
        roomsSeekBar.setMaxStartValue(Utils.getMaxValue(rooms));
        roomsSeekBar.setMinValue(Utils.getMinValue(rooms));
        roomsSeekBar.setMinStartValue(Utils.getMinValue(rooms));

        roomsSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            roomsMin.setText(minValue.toString());
            roomsMax.setText(maxValue.toString());
        });
        // -----------------
        
        // -----------------
        // BEDROOMS
        // -----------------
        bedroomsSeekBar.setMaxValue(Utils.getMaxValue(bedrooms));
        bedroomsSeekBar.setMaxStartValue(Utils.getMaxValue(bedrooms));
        bedroomsSeekBar.setMinValue(Utils.getMinValue(bedrooms));
        bedroomsSeekBar.setMinStartValue(Utils.getMinValue(bedrooms));

        bedroomsSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            bedroomsMin.setText(minValue.toString());
            bedroomsMax.setText(maxValue.toString());
        });
        // -----------------
        
        // -----------------
        // BATHROOMS
        // -----------------
        bathroomsSeekBar.setMaxValue(Utils.getMaxValue(bathrooms));
        bathroomsSeekBar.setMaxStartValue(Utils.getMaxValue(bathrooms));
        bathroomsSeekBar.setMinValue(Utils.getMinValue(bathrooms));
        bathroomsSeekBar.setMinStartValue(Utils.getMinValue(bathrooms));

        bathroomsSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            bathroomsMin.setText(minValue.toString());
            bathroomsMax.setText(maxValue.toString());
        });
        // -----------------

        // -----------------
        // NUMBER OF PICTURES
        // -----------------
        numberPictureSeekBar.setMaxValue(Utils.getMaxValue(numberPicture));
        numberPictureSeekBar.setMaxStartValue(Utils.getMaxValue(numberPicture));
        numberPictureSeekBar.setMinValue(Utils.getMinValue(numberPicture));
        numberPictureSeekBar.setMinStartValue(Utils.getMinValue(numberPicture));

        numberPictureSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            numberPictureMin.setText(minValue.toString());
            numberPictureMax.setText(maxValue.toString());
        });
        // -----------------

        cancelButton.setOnClickListener(view -> dialogBuilder.dismiss());
        submitButton.setOnClickListener(view -> {
            
            //  ---- DATE ADDED ------
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            
            Date startDateAdded = null;
            Date endDateAdded = null;
            boolean fieldsDateAdded = false;
            if (!dateAdded1.getText().toString().contains("Start") && !dateAdded2.getText().toString().contains("End")) {
                try {
                    startDateAdded = dateFormat.parse(dateAdded1.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    endDateAdded = dateFormat.parse(dateAdded2.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                fieldsDateAdded = true;
            }
            
            // ---- DATE SOLD -----
            Date startDateSold = null;
            Date endDateSold = null;
            boolean fieldsDateSold = false;
            if (!dateSold1.getText().toString().contains("Start") && !dateSold2.getText().toString().contains("End")) {
                try {
                    startDateSold = dateFormat.parse(dateSold1.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    endDateSold = dateFormat.parse(dateSold2.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                fieldsDateSold = true;
            }

            // ----- POI -----
            SparseBooleanArray checkedBoolean = gridView.getCheckedItemPositions();
            List<Integer> pointInteretList2 = new ArrayList<>();
            for(int i=0; i<checkedBoolean.size();i++) {
                int key = checkedBoolean.keyAt(i);
                pointInteretList2.add(pointInteretList.get(key).getId());
            }


            List<BienImmobilierComplete> searchList = new ArrayList<>();
            for(int i=0; i<bienImmobilierCompleteList.size();i++){

                Date dateAddedToCompare = null;
                boolean inBetweenDateAdded;
                
                Date dateSoldToCompare = null;
                boolean inBetweenDateSold;

                if (fieldsDateAdded) {
                    try {
                        dateAddedToCompare = dateFormat.parse(bienImmobilierCompleteList.get(i).getBienImmobilier().getDateEntree());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    inBetweenDateAdded = dateAddedToCompare.compareTo(startDateAdded) >= 0 && dateAddedToCompare.compareTo(endDateAdded)  <= 0;
                }
                else
                    inBetweenDateAdded = true;

                if (fieldsDateSold && bienImmobilierCompleteList.get(i).getBienImmobilier().getDateVente() != null) {
                    try {
                        dateSoldToCompare = dateFormat.parse(bienImmobilierCompleteList.get(i).getBienImmobilier().getDateVente());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    inBetweenDateSold = dateSoldToCompare.compareTo(startDateSold) >= 0 && dateSoldToCompare.compareTo(endDateSold)  <= 0;
                }
                else
                    inBetweenDateSold = !fieldsDateSold || bienImmobilierCompleteList.get(i).getBienImmobilier().getDateVente() != null;


                if(
                        (bienImmobilierCompleteList.get(i).getBienImmobilier().getPrix() <= priceSeekBar.getSelectedMaxValue().intValue() && bienImmobilierCompleteList.get(i).getBienImmobilier().getPrix() >= priceSeekBar.getSelectedMinValue().intValue())
                        && bienImmobilierCompleteList.get(i).getBienImmobilier().getVille().contains(citySpinner.getSelectedItem().toString())
                        && bienImmobilierCompleteList.get(i).getType().get(0).getLibelle().contains(typeSpinner.getSelectedItem().toString())
                        && (bienImmobilierCompleteList.get(i).getBienImmobilier().getSurface() <= surfaceSeekBar.getSelectedMaxValue().intValue() && bienImmobilierCompleteList.get(i).getBienImmobilier().getSurface() >= surfaceSeekBar.getSelectedMinValue().intValue())
                        && (bienImmobilierCompleteList.get(i).getBienImmobilier().getPieces() <= roomsSeekBar.getSelectedMaxValue().intValue() && bienImmobilierCompleteList.get(i).getBienImmobilier().getPieces() >= roomsSeekBar.getSelectedMinValue().intValue())
                        && (bienImmobilierCompleteList.get(i).getBienImmobilier().getSdb() <= bathroomsSeekBar.getSelectedMaxValue().intValue() && bienImmobilierCompleteList.get(i).getBienImmobilier().getSdb() >= bathroomsSeekBar.getSelectedMinValue().intValue())
                        && (bienImmobilierCompleteList.get(i).getBienImmobilier().getChambres() <= bedroomsSeekBar.getSelectedMaxValue().intValue() && bienImmobilierCompleteList.get(i).getBienImmobilier().getChambres() >= bedroomsSeekBar.getSelectedMinValue().intValue())
                        && (bienImmobilierCompleteList.get(i).getPhotos().size() <= numberPictureSeekBar.getSelectedMaxValue().intValue() && bienImmobilierCompleteList.get(i).getPhotos().size() >= numberPictureSeekBar.getSelectedMinValue().intValue())
                        && inBetweenDateAdded
                        && inBetweenDateSold
                ){
                    if(pointInteretList2.isEmpty())
                    searchList.add(bienImmobilierCompleteList.get(i));
                    else {
                        List<Integer> poiIdList = new ArrayList<>();
                        for (int i2=0; i2<bienImmobilierCompleteList.get(i).getPointInteretBienImmobiliers().size(); i2++){
                            poiIdList.add(bienImmobilierCompleteList.get(i).getPointInteretBienImmobiliers().get(i2).getIdPoi());
                        }
                        if(poiIdList.containsAll(pointInteretList2)){
                            searchList.add(bienImmobilierCompleteList.get(i));
                        }
                    }
                }
            }
            if (!searchList.isEmpty()) {
                Toast.makeText(this, "Number of property found : "+String.valueOf(searchList.size()), Toast.LENGTH_LONG).show();
                this.realEstateListFragment.updateAdapter(searchList, pointInteretList);
                dialogBuilder.dismiss();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("No result");
                alertDialog.setMessage("No properties were found based on your search criteria");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog1, which1) -> dialog1.dismiss());
                alertDialog.show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
