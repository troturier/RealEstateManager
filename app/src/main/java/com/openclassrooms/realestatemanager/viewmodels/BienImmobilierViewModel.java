package com.openclassrooms.realestatemanager.viewmodels;

import com.openclassrooms.realestatemanager.models.BienImmobilier;
import com.openclassrooms.realestatemanager.models.BienImmobilierComplete;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.PointInteret;
import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.models.Utilisateur;
import com.openclassrooms.realestatemanager.repositories.BienImmobilierDataRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoDataRepository;
import com.openclassrooms.realestatemanager.repositories.PointInteretBienImmobilierDataRepository;
import com.openclassrooms.realestatemanager.repositories.PointInteretDataRepository;
import com.openclassrooms.realestatemanager.repositories.TypeDataRepository;
import com.openclassrooms.realestatemanager.repositories.UtilisateurDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class BienImmobilierViewModel extends ViewModel {

    // REPOSITORIES
    private final BienImmobilierDataRepository bienImmobilierDataSource;
    private final PhotoDataRepository photoDataSource;
    private final PointInteretBienImmobilierDataRepository pointInteretBienImmobilierDataSource;
    private final TypeDataRepository typeDataSource;
    private final UtilisateurDataRepository utilisateurDataSource;
    private final PointInteretDataRepository pointInteretDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<Utilisateur> currentUtilisateur;

    public BienImmobilierViewModel(BienImmobilierDataRepository bienImmobilierDataSource, PhotoDataRepository photoDataSource, PointInteretBienImmobilierDataRepository pointInteretBienImmobilierDataSource, TypeDataRepository typeDataSource, UtilisateurDataRepository utilisateurDataSource, PointInteretDataRepository pointInteretDataSource, Executor executor){
        this.bienImmobilierDataSource = bienImmobilierDataSource;
        this.photoDataSource = photoDataSource;
        this.pointInteretBienImmobilierDataSource = pointInteretBienImmobilierDataSource;
        this.pointInteretDataSource = pointInteretDataSource;
        this.typeDataSource = typeDataSource;
        this.utilisateurDataSource = utilisateurDataSource;
        this.executor = executor;
    }

    public void init(int utilisateurId){
        if(this.currentUtilisateur != null){
            return;
        }
        currentUtilisateur = utilisateurDataSource.getUtilisateur(utilisateurId);
    }

    // --------
    // FOR UTILISATEUR
    // --------

    public LiveData<Utilisateur> getUtilisateur(int utilisateurId) {
        return this.currentUtilisateur;
    }

    // --------
    // FOR BIEN IMMOBILIER
    // --------

    public LiveData<List<BienImmobilier>> getBienImmobiliers(){
        return bienImmobilierDataSource.getBienImmobiliers();
    }

    public LiveData<List<BienImmobilierComplete>> getBienImmobiliersComplete(){
        return bienImmobilierDataSource.getBienImmobiliersComplete();
    }

    public LiveData<BienImmobilier> getBienImmobilier(int bienImmobilierId){
        return bienImmobilierDataSource.getBienImmobilier(bienImmobilierId);
    }

    public void createBienImmobilier(BienImmobilier bienImmobilier){
        executor.execute(() -> bienImmobilierDataSource.createBienImmobilier(bienImmobilier));
    }

    public LiveData<BienImmobilier> getLastBienImmobilier(){
        return bienImmobilierDataSource.getLastBienImmobilier();
    }

    public void deleteBienImmobilier(BienImmobilier bienImmobilier){
        executor.execute(() -> {
            bienImmobilierDataSource.deleteBienImmobilier(bienImmobilier);
        });
    }

    public void updateBienImmobilier(BienImmobilier bienImmobilier){
        executor.execute(() -> {
            bienImmobilierDataSource.updateeBienImmobilier(bienImmobilier);
        });
    }

    // --------
    // FOR PHOTO
    // --------

    public LiveData<List<Photo>> getPhotos(int bienImmobilierId){
        return photoDataSource.getPhotos(bienImmobilierId);
    }

    public LiveData<Photo> getPhoto(int photoId){
        return photoDataSource.getPhoto(photoId);
    }

    public LiveData<Photo> getLastPhoto(){return photoDataSource.getLastPhoto();}

    public void createPhoto(Photo photo){
        executor.execute(() -> {
            photoDataSource.createPhoto(photo);
        });
    }

    public void deletePhoto(Photo photo){
        executor.execute(() -> {
            photoDataSource.deletePhoto(photo);
        });
    }

    public void updatePhoto(Photo photo){
        executor.execute(() -> {
            photoDataSource.updateePhoto(photo);
        });
    }

    // --------
    // FOR POINT INTERET
    // --------

    public LiveData<List<PointInteret>> getPointInterets(){
        return pointInteretDataSource.getPointInterets();
    }

    public LiveData<PointInteret> getPointInteret(int pointInteretId){
        return pointInteretDataSource.getPointInteret(pointInteretId);
    }

    public void createPointInteret(PointInteret pointInteret){
        executor.execute(() -> {
            pointInteretDataSource.createPointInteret(pointInteret);
        });
    }

    public void deletePointInteret(PointInteret pointInteret){
        executor.execute(() -> {
            pointInteretDataSource.deletePointInteret(pointInteret);
        });
    }

    public void updatePointInteret(PointInteret pointInteret){
        executor.execute(() -> {
            pointInteretDataSource.updateePointInteret(pointInteret);
        });
    }

    // --------
    // FOR POINT_INTERET_BIEN_IMMOBILIER
    // --------

    public LiveData<List<PointInteretBienImmobilier>> getPointInteretBienImmobilierByBI(int bienImmobilierId){
        return pointInteretBienImmobilierDataSource.getPointInteretBienImmobilierByBI(bienImmobilierId);
    }

    public LiveData<List<PointInteretBienImmobilier>> getPointInteretBienImmobilierByPOI(int poiId){
        return pointInteretBienImmobilierDataSource.getPointInteretBienImmobiliersByPOI(poiId);
    }

    public LiveData<List<PointInteretBienImmobilier>> getAllPointInteretsBienImmobilier(){
        return pointInteretBienImmobilierDataSource.getAllPointInteretsBienImmobilier();
    }

    public void createPointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier){
        executor.execute(() -> {
            pointInteretBienImmobilierDataSource.createPointInteretBienImmobilier(pointInteretBienImmobilier);
        });
    }

    public void deletePointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier){
        executor.execute(() -> {
            pointInteretBienImmobilierDataSource.deletePointInteretBienImmobilier(pointInteretBienImmobilier);
        });
    }

    // --------
    // FOR TYPE
    // --------

    public LiveData<List<Type>> getTypes(){
        return typeDataSource.getTypes();
    }

    public LiveData<Type> getType(int typeId){
        return typeDataSource.getType(typeId);
    }

    public void createType(Type type){
        executor.execute(() -> {
            typeDataSource.createType(type);
        });
    }

    public void deleteType(Type type){
        executor.execute(() -> {
            typeDataSource.deleteType(type);
        });
    }

    public void updateType(Type type){
        executor.execute(() -> {
            typeDataSource.updateeType(type);
        });
    }

    // --------
    // FOR UTILISATEUR
    // --------

    public LiveData<List<Utilisateur>> getUtilisateurs(){
        return utilisateurDataSource.getUtilisateurs();
    }

    public void createUtilisateur(Utilisateur utilisateur){
        executor.execute(() -> {
            utilisateurDataSource.createUtilisateur(utilisateur);
        });
    }

    public void deleteUtilisateur(Utilisateur utilisateur){
        executor.execute(() -> {
            utilisateurDataSource.deleteUtilisateur(utilisateur);
        });
    }

    public void updateUtilisateur(Utilisateur utilisateur){
        executor.execute(() -> {
            utilisateurDataSource.updateeUtilisateur(utilisateur);
        });
    }
}
