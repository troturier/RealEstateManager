package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.PrimaryKey;

public class Utilisateur {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private String nom;
    private String prenom;

    // GETTERS - SETTERS //

    public int get_id() { return _id; }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
