package com.openclassrooms.realestatemanager.models;

import java.io.Serializable;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class BienImmobilierComplete implements Serializable {

    private static final long serialVersionUID = -1213949467658913456L;

    @Embedded
    private BienImmobilier bienImmobilier;

    @Relation(parentColumn = "idType", entityColumn = "id", entity = Type.class)
    private List<Type> type;

    @Relation(parentColumn = "idAgent", entityColumn = "id", entity = Utilisateur.class)
    private List<Utilisateur> utilisateurs;

    @Relation(parentColumn = "id", entityColumn = "idBien", entity = PointInteretBienImmobilier.class)
    private List<PointInteretBienImmobilier> pointInteretBienImmobiliers;

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

    public List<PointInteretBienImmobilier> getPointInteretBienImmobiliers() {
        return pointInteretBienImmobiliers;
    }

    public void setPointInteretBienImmobiliers(List<PointInteretBienImmobilier> pointInteretBienImmobiliers) {
        this.pointInteretBienImmobiliers = pointInteretBienImmobiliers;
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
