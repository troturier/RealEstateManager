package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.PointInteretBienImmobilier;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PointInteretBienImmobilierDao {
    @Query("SELECT * FROM PointInteretBienImmobilier WHERE idBien = :idBien")
    LiveData<List<PointInteretBienImmobilier>> getPointInteretBienImmobilierByBI(int idBien);

    @Query("SELECT * FROM PointInteretBienImmobilier WHERE idPoi = :idPoi")
    LiveData<List<PointInteretBienImmobilier>> getPointInteretBienImmobiliersByPOI(int idPoi);

    @Query("SELECT * FROM PointInteretBienImmobilier")
    LiveData<List<PointInteretBienImmobilier>> getAllPointInteretsBienImmobilier();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long insertPointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier);

    @Delete
    int deletePointInteretBienImmobilier(PointInteretBienImmobilier pointInteretBienImmobilier);
}
