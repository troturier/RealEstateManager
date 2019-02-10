package com.openclassrooms.realestatemanager.models;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class BienImmobilierComplete {

    @Embedded
    private BienImmobilier bienImmobilier;

    @Relation(parentColumn = "idType", entityColumn = "id", entity = Type.class)
    private List<Type> type;

    @Relation(parentColumn = "idAgent", entityColumn = "id", entity = Utilisateur.class)
    private List<Utilisateur> utilisateurs;

    @Relation(parentColumn = "id", entityColumn = "idBien", entity = PointInteret.class)
    private List<PointInteret> pointInterets;

    @Relation(parentColumn = "id", entityColumn = "idBien", entity = Photo.class)
    private List<Photo> photos;

    @Relation(parentColumn = "idPhotoCouverture", entityColumn = "id", entity = Photo.class)
    private List<Photo> photoCouverture;

    public BienImmobilierComplete(BienImmobilier bienImmobilier) {
        this.bienImmobilier = bienImmobilier;
    }

    public List<Type> getType() {
        return type;
    }

    public void setType(List<Type> type) {
        this.type = type;
    }

    public BienImmobilier getBienImmobilier() {
        return bienImmobilier;
    }

    public void setBienImmobilier(BienImmobilier bienImmobilier) {
        this.bienImmobilier = bienImmobilier;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public List<PointInteret> getPointInterets() {
        return pointInterets;
    }

    public void setPointInterets(List<PointInteret> pointInterets) {
        this.pointInterets = pointInterets;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getPhotoCouverture() {
        return photoCouverture;
    }

    public void setPhotoCouverture(List<Photo> photoCouverture) {
        this.photoCouverture = photoCouverture;
    }
}
