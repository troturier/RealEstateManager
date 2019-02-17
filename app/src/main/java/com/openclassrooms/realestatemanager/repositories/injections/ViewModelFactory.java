package com.openclassrooms.realestatemanager.repositories.injections;

import com.openclassrooms.realestatemanager.repositories.BienImmobilierDataRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoDataRepository;
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
    private final PointInteretDataRepository pointInteretDataSource;
    private final TypeDataRepository typeDataSource;
    private final UtilisateurDataRepository utilisateurDataSource;
    private final Executor executor;

    public ViewModelFactory(BienImmobilierDataRepository bienImmobilierDataSource, PhotoDataRepository photoDataSource, PointInteretDataRepository pointInteretDataSource, TypeDataRepository typeDataSource, UtilisateurDataRepository utilisateurDataSource, Executor executor){
        this.bienImmobilierDataSource = bienImmobilierDataSource;
        this.photoDataSource = photoDataSource;
        this.pointInteretDataSource = pointInteretDataSource;
        this.typeDataSource = typeDataSource;
        this.utilisateurDataSource = utilisateurDataSource;
        this.executor = executor;
    }

    @Override

    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BienImmobilierViewModel.class)) {
            return (T) new BienImmobilierViewModel(bienImmobilierDataSource, photoDataSource, pointInteretDataSource, typeDataSource, utilisateurDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
