package com.openclassrooms.realestatemanager.models;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "id")})
public class PointInteret implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String libelle;

    public PointInteret(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public PointInteret(){}

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
