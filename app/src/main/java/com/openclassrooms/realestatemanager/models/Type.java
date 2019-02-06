package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Type {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private String libelle;

    public Type(int _id, String libelle) {
        this._id = _id;
        this.libelle = libelle;
    }

    // GETTERS - SETTERS //

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
