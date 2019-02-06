package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(
        entity = Photo.class,
        parentColumns = "_id",
        childColumns = "idPhotoCouverture"),
        @ForeignKey(
                entity = Utilisateur.class,
                parentColumns = "_id",
                childColumns = "idAgent"),
        @ForeignKey(
                entity = Type.class,
                parentColumns = "_id",
                childColumns = "idType")})
public class BienImmobilier {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private int surface;
    private int chambres;
    private int sdb;
    private int pieces;
    private String rue;
    private String complementRue;
    private String ville;
    private String pays;
    private String cp;
    private String description;
    private String dateEntree;
    private String dateVente;
    private int prix;
    private boolean statut;
    private int idPhotoCouverture;
    private int idAgent;
    private int idType;

    // GETTERS - SETTERS //

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

    public int getChambres() {
        return chambres;
    }

    public void setChambres(int chambres) {
        this.chambres = chambres;
    }

    public int getSdb() {
        return sdb;
    }

    public void setSdb(int sdb) {
        this.sdb = sdb;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getComplementRue() {
        return complementRue;
    }

    public void setComplementRue(String complementRue) {
        this.complementRue = complementRue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
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

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public int getIdPhotoCouverture() {
        return idPhotoCouverture;
    }

    public void setIdPhotoCouverture(int idPhotoCouverture) {
        this.idPhotoCouverture = idPhotoCouverture;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }
}
