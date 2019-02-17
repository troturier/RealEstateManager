package com.openclassrooms.realestatemanager.models;


import android.content.ContentValues;

import java.io.Serializable;

import androidx.room.*;

@Entity(indices = {@Index(value = "id")})
public class Utilisateur implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nom;
    private String prenom;

    public Utilisateur(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Utilisateur() {}

    // GETTERS - SETTERS //

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
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

    // --- UTILS ---
    public static Utilisateur fromContentValues(ContentValues values){
        final Utilisateur utilisateur = new Utilisateur();
        if (values.containsKey("id")) utilisateur.setId(values.getAsInteger("id"));
        if (values.containsKey("nom")) utilisateur.setNom(values.getAsString("nom"));
        if (values.containsKey("prenom")) utilisateur.setPrenom(values.getAsString("prenom"));
        return utilisateur;
    }
}
