package com.openclassrooms.realestatemanager.models;


import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(foreignKeys = {@ForeignKey(
        entity = BienImmobilier.class,
        parentColumns = "id",
        childColumns = "idBien"),
        @ForeignKey(
                entity = PointInteret.class,
                parentColumns = "id",
                childColumns = "idPoi")},
        primaryKeys = {"idPoi","idBien"},
        indices = {@Index(value = "idBien", name = "idBien_POI"),
                @Index(value = "idPoi", name = "idPoi_POI")})
public class PointInteretBienImmobilier implements Serializable {
    private int idPoi;
    private int idBien;

    private static final long serialVersionUID = -1213949467658913456L;


    public PointInteretBienImmobilier(int idPoi, int idBien) {
        this.idPoi = idPoi;
        this.idBien = idBien;
    }

    public PointInteretBienImmobilier(){}

    // GETTERS - SETTERS //

    public int getIdPoi() {
        return idPoi;
    }

    public void setIdPoi(int idPoi) {
        this.idPoi = idPoi;
    }

    public int getIdBien() {
        return idBien;
    }

    public void setIdBien(int idBien) {
        this.idBien = idBien;
    }

}
