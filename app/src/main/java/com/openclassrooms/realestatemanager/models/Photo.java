package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = BienImmobilier.class,
        parentColumns = "_id",
        childColumns = "idBien"))
public class Photo {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private String description;
    private String cheminAcces;
    private int idBien;

    // GETTERS - SETTERS //

    public void setIdBien(int idBien) {
        this.idBien = idBien;
    }

    public int getIdBien() {
        return idBien;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
