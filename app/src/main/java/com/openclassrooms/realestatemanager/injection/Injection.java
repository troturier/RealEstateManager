package com.openclassrooms.realestatemanager.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.repositories.BienImmobilierDataRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoDataRepository;
import com.openclassrooms.realestatemanager.repositories.PointInteretDataRepository;
import com.openclassrooms.realestatemanager.repositories.TypeDataRepository;
import com.openclassrooms.realestatemanager.repositories.UtilisateurDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static BienImmobilierDataRepository provideBienImmobilierDataSource(Context context){
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new BienImmobilierDataRepository(database.bienImmobilierDao());
    }

    public static PhotoDataRepository providePhotoDataSource(Context context){
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new PhotoDataRepository(database.photoDao());
    }

    public static PointInteretDataRepository providePointInteretDataSource(Context context){
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new PointInteretDataRepository(database.pointInteretDao());
    }

    public static TypeDataRepository provideTypeDataSource(Context context){
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new TypeDataRepository(database.typeDao());
    }

    public static UtilisateurDataRepository provideUtilisateurDataSource(Context context){
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new UtilisateurDataRepository(database.utilisateurDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        BienImmobilierDataRepository dataSourceBienImmobilier = provideBienImmobilierDataSource(context);
        PhotoDataRepository dataSourcePhoto = providePhotoDataSource(context);
        PointInteretDataRepository dataSourcePointInteret = providePointInteretDataSource(context);
        TypeDataRepository dataSourceType = provideTypeDataSource(context);
        UtilisateurDataRepository dataSourceUtilisateur = provideUtilisateurDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceBienImmobilier, dataSourcePhoto, dataSourcePointInteret, dataSourceType, dataSourceUtilisateur, executor);
    }
}
