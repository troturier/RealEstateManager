package com.openclassrooms.realestatemanager.models;


import androidx.room.*;

@Entity(foreignKeys = @ForeignKey(
        entity = BienImmobilier.class,
        parentColumns = "id",
        childColumns = "idBien"),
        indices = {@Index(value = "idBien", name = "idBien_Photo")})
public class Photo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private String cheminAcces;
    private int idBien;

    public Photo(int id, String description, String cheminAcces, int idBien) {
        this.id = id;
        this.description = description;
        this.cheminAcces = cheminAcces;
        this.idBien = idBien;
    }

    // GETTERS - SETTERS //

    public void setIdBien(int idBien) {
        this.idBien = idBien;
    }

    public int getIdBien() {
        return idBien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheminAcces() {
        return cheminAcces;
    }

    public void setCheminAcces(String cheminAcces) {
        this.cheminAcces = cheminAcces;
    }
}
