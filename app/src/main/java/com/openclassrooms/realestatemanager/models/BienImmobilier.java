package com.openclassrooms.realestatemanager.models;


import com.openclassrooms.realestatemanager.utils.Utils;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Photo.class,
                parentColumns = "id",
                childColumns = "idPhotoCouverture"),
        @ForeignKey(
                entity = Utilisateur.class,
                parentColumns = "id",
                childColumns = "idAgent"),
        @ForeignKey(
                entity = Type.class,
                parentColumns = "id",
                childColumns = "idType")},
        indices = {@Index(value = "idAgent", name = "idAgent"),
                @Index(value = "idType", name = "idType")})
public class BienImmobilier implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
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
    private Integer idPhotoCouverture;
    private boolean statut;
    private int idAgent;
    private int idType;

    public BienImmobilier(int id, int surface, int chambres, int sdb, int pieces, String rue, String complementRue, String ville, String pays, String cp, String description, String dateVente, int prix, Integer idPhotoCouverture, boolean statut, int idAgent, int idType) {
        this.id = id;
        this.surface = surface;
        this.chambres = chambres;
        this.sdb = sdb;
        this.pieces = pieces;
        this.rue = rue;
        this.complementRue = complementRue;
        this.ville = ville;
        this.pays = pays;
        this.cp = cp;
        this.description = description;
        this.idPhotoCouverture = idPhotoCouverture;
        this.dateEntree = Utils.getTodayDate();
        this.dateVente = dateVente;
        this.prix = prix;
        this.statut = statut;
        this.idAgent = idAgent;
        this.idType = idType;
    }

    // GETTERS - SETTERS //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getIdPhotoCouverture() {
        return idPhotoCouverture;
    }

    public void setIdPhotoCouverture(Integer idPhotoCouverture) {
        this.idPhotoCouverture = idPhotoCouverture;
    }
}
