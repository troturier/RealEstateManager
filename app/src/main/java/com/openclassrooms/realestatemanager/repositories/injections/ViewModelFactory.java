package com.openclassrooms.realestatemanager.repositories.injections;

import com.openclassrooms.realestatemanager.repositories.BienImmobilierDataRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoDataRepository;
import com.openclassrooms.realestatemanager.repositories.PointInteretBienImmobilierDataRepository;
import com.openclassrooms.realestatemanager.repositories.PointInteretDataRepository;
import com.openclassrooms.realestatemanager.repositories.TypeDataRepository;
import com.openclassrooms.realestatemanager.repositories.UtilisateurDataRepository;
import com.openclassrooms.realestatemanager.viewmodels.BienImmobilierViewModel;

import java.util.concurrent.Executor;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final BienImmobilierDataRepository bienImmobilierDataSource;
    private final PhotoDataRepository photoDataSource;
    private final PointInteretBienImmobilierDataRepository pointInteretBienImmobilierDataSource;
    private final TypeDataRepository typeDataSource;
    private final UtilisateurDataRepository utilisateurDataSource;
    private final PointInteretDataRepository pointInteretDataSource;
    private final Executor executor;

    public ViewModelFactory(BienImmobilierDataRepository bienImmobilierDataSource, PhotoDataRepository photoDataSource, PointInteretBienImmobilierDataRepository pointInteretBienImmobilierDataSource, TypeDataRepository typeDataSource, UtilisateurDataRepository utilisateurDataSource, PointInteretDataRepository pointInteretDataSource, Executor executor){
        this.bienImmobilierDataSource = bienImmobilierDataSource;
        this.photoDataSource = photoDataSource;
        this.pointInteretBienImmobilierDataSource = pointInteretBienImmobilierDataSource;
        this.typeDataSource = typeDataSource;
        this.utilisateurDataSource = utilisateurDataSource;
        this.pointInteretDataSource = pointInteretDataSource;
        this.executor = executor;
    }

    @Override

    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BienImmobilierViewModel.class)) {
            return (T) new BienImmobilierViewModel(bienImmobilierDataSource, photoDataSource, pointInteretBienImmobilierDataSource, typeDataSource, utilisateurDataSource, pointInteretDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
