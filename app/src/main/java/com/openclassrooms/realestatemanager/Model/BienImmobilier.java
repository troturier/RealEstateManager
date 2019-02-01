package com.openclassrooms.realestatemanager.Model;

import java.util.List;

public class BienImmobilier {
    private int _id;
    private int surface;
    private int nombreChambres;
    private int nombreSdB;
    private String description;
    private String dateEntree;
    private String dateVente;
    private Float prix;
    private boolean statut;
    private Photo photoCouverture;
    private List<Photo> photos;
    private Utilisateur agent;
    private Adresse adresse;
    private Type type;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getNombreChambres() {
        return nombreChambres;
    }

    public void setNombreChambres(int nombreChambres) {
        this.nombreChambres = nombreChambres;
    }

    public int getNombreSdB() {
        return nombreSdB;
    }

    public void setNombreSdB(int nombreSdB) {
        this.nombreSdB = nombreSdB;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(String dateEntree) {
        this.dateEntree = dateEntree;
    }

    public String getDateVente() {
        return dateVente;
    }

    public void setDateVente(String dateVente) {
        this.dateVente = dateVente;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public Photo getPhotoCouverture() {
        return photoCouverture;
    }

    public void setPhotoCouverture(Photo photoCouverture) {
        this.photoCouverture = photoCouverture;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Utilisateur getAgent() {
        return agent;
    }

    public void setAgent(Utilisateur agent) {
        this.agent = agent;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
