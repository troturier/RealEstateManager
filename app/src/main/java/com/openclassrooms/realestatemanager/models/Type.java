package com.openclassrooms.realestatemanager.models;


import androidx.room.*;

@Entity(indices = {@Index(value = "id")})
public class Type {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String libelle;

    public Type(int id, String libelle) {
        this.id = id;
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
}