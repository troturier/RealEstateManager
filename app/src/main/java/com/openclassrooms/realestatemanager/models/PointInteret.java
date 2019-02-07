package com.openclassrooms.realestatemanager.models;


import androidx.room.*;

@Entity(foreignKeys = @ForeignKey(
        entity = BienImmobilier.class,
        parentColumns = "id",
        childColumns = "idBien"),
        indices = {@Index(value = "idBien", name = "idBien_POI")})
public class PointInteret {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idBien;
    private String libelle;

    public PointInteret(int id, int idBien, String libelle) {
        this.id = id;
        this.idBien = idBien;
        this.libelle = libelle;
    }

    // GETTERS - SETTERS //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getIdBien() {
        return idBien;
    }

    public void setIdBien(int idBien) {
        this.idBien = idBien;
    }
}
